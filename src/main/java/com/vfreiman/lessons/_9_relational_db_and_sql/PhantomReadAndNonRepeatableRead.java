package com.vfreiman.lessons._9_relational_db_and_sql;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 * The second session reads an inserted record in the table which was made the first session
 *
 *      A little bit about Non-Repeatable read:
 *        The second session reads an updated record in the table which was made the first session
 *        (see a commented sql request in thread1())
 */
public class PhantomReadAndNonRepeatableRead {

    /**
     * change isolation levels here
     */
//    private static int TRANSACTION_LEVEL = Connection.TRANSACTION_READ_UNCOMMITTED; //phantom read
//    private static int TRANSACTION_LEVEL = Connection.TRANSACTION_READ_COMMITTED; //phantom read #default
    private static int TRANSACTION_LEVEL = Connection.TRANSACTION_REPEATABLE_READ; //phantom read (~ == Serializable in MySQL)
//    private static int TRANSACTION_LEVEL = Connection.TRANSACTION_SERIALIZABLE; //phantom read fixed

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        createTable();
        fillDB();
        thread1();
        thread2();
    }

    /**
     * Thread 1 sleeps two seconds then executes update and commits.
     */
    private static void thread1() {
        new Thread(() -> {
            try (Connection conn = connectMySQL()) {

                /**
                 * For an exmample of the Non-Repeatable read
                 */
//                String sql = "UPDATE newtable SET f = 'newValue' WHERE id = 1";

                String sql = "INSERT INTO newtable (f) VALUES('a')";
                PreparedStatement insert = conn.prepareStatement(sql);

                TimeUnit.SECONDS.sleep(2);

                insert.executeUpdate();
                conn.commit();
                System.out.println("First transaction committed after 2 seconds sleeping");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    /**
     * Thread 2 reads a table, sleeps 5 seconds and reads the table again.
     */
    private static void thread2() {
        new Thread(() -> {
            try (Connection conn = connectMySQL()) {

                String sql = "SELECT * FROM newtable";
                PreparedStatement read = conn.prepareStatement(sql);

                System.out.println("Second transaction read");
                thread2Read(read);

                TimeUnit.SECONDS.sleep(5);

                System.out.println("Second transaction read in 5 sec");
                thread2Read(read);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    private static void thread2Read(PreparedStatement read) throws SQLException{
        ResultSet resultSet = read.executeQuery();
        while (resultSet.next()) {
            System.out.print(" id = " + resultSet.getInt("id"));
            System.out.println(" f = " + resultSet.getString("f"));
        }
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
        Connection conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);
        conn.setTransactionIsolation(TRANSACTION_LEVEL);
        return conn;
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
        final String sql = "INSERT INTO newtable (f) VALUES ('a')";
        try (Connection conn = connectMySQL();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
