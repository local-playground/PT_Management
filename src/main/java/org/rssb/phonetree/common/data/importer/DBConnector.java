package org.rssb.phonetree.common.data.importer;

import java.sql.*;

public class DBConnector {
    public static void createNewDatabase(String fileName) {
        String url = "jdbc:sqlite:" + fileName;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void executeQuery(String query, String dbFileName) {
        String url = "jdbc:sqlite:" + dbFileName;
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(query);
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        DBConnector.createNewDatabase("C:\\Rajesh\\Personal\\Satsang\\Test.db");
    }
}
