package com.vfreiman.lessons._9_relational_db_and_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * It is an example of how read and write locks work in MySQL using JDBC.
 * It helps in a situation if the table can be replaced with other one
 */
public class Locks {
    private static final CountDownLatch LATCH = new CountDownLatch(1);

    public static void main(String[] args) {
        createTable();
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
     * Session 1 takes a write lock, sleeps 3 seconds and then unlocks the fields of the table.
     */
    private static Future<?> task1(ExecutorService es) {
        return es.submit(() -> {

            String sql = writeLock("newtable");
            try (Connection conn = connectMySQL();
                 Statement stmt = conn.createStatement(
                         ResultSet.TYPE_SCROLL_SENSITIVE,
                         ResultSet.CONCUR_UPDATABLE
                 );
                 ResultSet rs = stmt.executeQuery(sql)) {

                LATCH.countDown();
                System.out.println("Session 1 locked the fields of the table");

                TimeUnit.SECONDS.sleep(3);

                conn.commit();
                System.out.println("Session 1 unlock the fields of the table");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Session 2 with a read lock tries to get access to a table .
     */
    private static Future<?> task2(ExecutorService es) {
        return es.submit(() -> {

            try {
                LATCH.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Session 2 is trying to read the fields of the table...");

            String sql = readLock("newtable");
            try (Connection conn = connectMySQL();
                 Statement stmt = conn.createStatement(
                         ResultSet.TYPE_SCROLL_SENSITIVE,
                         ResultSet.CONCUR_UPDATABLE
                 );
                 ResultSet rs = stmt.executeQuery(sql)) {

                System.out.println("Session 2 has read the fields of the table!");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private static String writeLock(final String table) {
//        return "SELECT * FROM " + table + " FOR UPDATE"; //doesn't work in jdbc by some reason
        return "lock table "+ table +" WRITE";
    }

    private static String readLock(final String table) {
//        return "SELECT * FROM " + table + " LOCK IN SHARE MODE"; //doesn't work in jdbc by some reason
        return "lock table "+ "newtable" +" READ";
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
}
