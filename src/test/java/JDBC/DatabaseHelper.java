package JDBC;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    protected static Connection con;
    private static final String dbSchema = "jdbc:mysql://demo.mersys.io:33906/employees";
    private static final String username = "student";
    private static final String password = "DEkzTd3#pzPm";

    @BeforeClass
    public static void setUp() {
        try {
            System.out.println(">>> Connecting to Database...");
            con = DriverManager.getConnection(dbSchema, username, password);
            if (con != null) {
                System.out.println(">|< Connected to the Database...");
            }
        } catch (SQLException e) {
            System.err.println("Connection Failed: " + e.getMessage());
        }
    }

    @AfterClass
    public static void tearDown() {
        try {
            if (con != null && !con.isClosed()) {
                System.out.println("Closing Database Connection");
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected static void executeEmployeeQuery(String query) throws SQLException {
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();


            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-20s", metaData.getColumnName(i));
            }
            System.out.println();
            System.out.println("=".repeat(20 * columnCount)); // Divider


            int rowCount = 0;
            while (rs.next() && rowCount < 100) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.printf("%-20s", rs.getString(i));
                }
                System.out.println();
                rowCount++;
            }
        }
    }
}
