package com.vfreiman.lessons._9_relational_db_and_sql;

import java.sql.*;
import java.util.concurrent.*;

/**
 * One session can read a record which can be rollbacked by other session
 */
public class DirtyRead {

    /**
     * change isolation levels here
     */
    private static final int TRANSACTION_LEVEL = Connection.TRANSACTION_READ_UNCOMMITTED; //dirty read
//    private static final int TRANSACTION_LEVEL = Connection.TRANSACTION_READ_COMMITTED; //dirty read fixed #default
//    private static final int TRANSACTION_LEVEL = Connection.TRANSACTION_REPEATABLE_READ; //dirty read fixed
//    private static final int TRANSACTION_LEVEL = Connection.TRANSACTION_SERIALIZABLE; //dirty read fixed

    public static void main(String[] args) {
        createTable();
        fillDB();
        tasks();
    }

    private static void tasks() {
        final ExecutorService es = Executors.newCachedThreadPool();
        Future<?> task1 = task1(es);
        Future<?> task2 = task2(es);

        try {
            task1.get();
            task2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        es.shutdown();
    }

    /**
     * Session 1 updates a record, sleeps 3 seconds, then rollbacks
     */
    private static Future<?> task1(ExecutorService es) {
        return es.submit(() -> {
            final String sql = "UPDATE newtable SET f = 'abcde' WHERE id = 1";
            try (Connection conn = connectMySQL();
                 Statement stmt = conn.createStatement()) {

                stmt.executeUpdate(sql);

                TimeUnit.SECONDS.sleep(3);

                conn.rollback();

                String sql2 = "SELECT * FROM newtable";
                ResultSet rs = stmt.executeQuery(sql2);
                while (rs.next()) {
                    System.out.println("ACTUAL " + rs.getInt(1) + " : " + rs.getString(2));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Session 2 reads an updated uncommited record
     */
    private static Future<?> task2(ExecutorService es) {
        return es.submit(() -> {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final String sql = "SELECT * FROM newtable";
            try (Connection conn = connectMySQL();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

               while (rs.next()) {
                   System.out.println("SESSION 2 read " + rs.getInt(1) + " : " + rs.getString(2));
               }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private static Connection connectMySQL() throws SQLException {
        String ip = "localhost";
        String port = "3306";
        String schema = "newschema";
        String useSSL = "false";
        String user = "root";
        String password = "pass";
        String url = String.format(
                "jdbc:mysql://%s:%s/%s?useSSL=%s&user=%s&password=%s",
                ip, port, schema, useSSL, user, password
        );
        Connection connection = DriverManager.getConnection(url);
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(TRANSACTION_LEVEL);
        return connection;
    }

    private static void createTable() {
        try (Connection conn = connectMySQL()) {
            StringBuilder sql = new StringBuilder("DROP TABLE IF EXISTS newtable;");
            conn.prepareStatement(sql.toString()).executeUpdate();

            sql = new StringBuilder();
            sql.append("CREATE TABLE IF NOT EXISTS newtable(");
            sql.append("id INT AUTO_INCREMENT NOT NULL,");
            sql.append("f VARCHAR(100) NULL,");
            sql.append("PRIMARY KEY (id)");
            sql.append(");");
            conn.prepareStatement(sql.toString()).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void fillDB() {
        final String sql = "INSERT INTO newtable (f) VALUES ('a'), ('b'), ('c')";
        try (Connection conn = connectMySQL();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
