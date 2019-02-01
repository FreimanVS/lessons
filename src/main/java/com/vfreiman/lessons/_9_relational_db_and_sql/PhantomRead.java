package com.vfreiman.lessons._9_relational_db_and_sql;

import java.sql.*;
import java.util.concurrent.TimeUnit;

public class PhantomRead {

    /**
     * change isolation levels here
     */
//    private static int TRANSACTION_LEVEL = Connection.TRANSACTION_READ_COMMITTED; //phantom read
    private static int TRANSACTION_LEVEL = Connection.TRANSACTION_SERIALIZABLE; //phantom read fixed

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        thread1();
        thread2();
    }

    private static Connection connectMySQL() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/newschema?useSSL=false&user=root&password=pass");
    }

    private static void thread1() {
        new Thread(() -> {
            try (Connection conn = connectMySQL()) {
                conn.setAutoCommit(false);

                conn.setTransactionIsolation(TRANSACTION_LEVEL);
                PreparedStatement insert = conn.prepareStatement("INSERT INTO newtable (field2, field3, field4) VALUES('a', 1, 1.08)");

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

    private static void thread2() {
        new Thread(() -> {
            try (Connection conn = connectMySQL()) {
                conn.setAutoCommit(false);

                conn.setTransactionIsolation(TRANSACTION_LEVEL);
                PreparedStatement read = conn.prepareStatement("SELECT * FROM newtable");

                System.out.println("Second transaction read");
                ResultSet resultSet = read.executeQuery();
                while (resultSet.next()) {
                    System.out.print(" field = " + resultSet.getInt("field"));
                    System.out.print(" field2 = " + resultSet.getString("field2"));
                    System.out.print(" field3 = " + resultSet.getLong("field3"));
                    System.out.println(" field4 = " + resultSet.getDouble("field4"));
                }

                TimeUnit.SECONDS.sleep(5);


                System.out.println("Second transaction read in 5 sec");
                resultSet = read.executeQuery();
                while (resultSet.next()) {
                    System.out.print(" field = " + resultSet.getInt("field"));
                    System.out.print(" field2 = " + resultSet.getString("field2"));
                    System.out.print(" field3 = " + resultSet.getLong("field3"));
                    System.out.println(" field4 = " + resultSet.getDouble("field4"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
