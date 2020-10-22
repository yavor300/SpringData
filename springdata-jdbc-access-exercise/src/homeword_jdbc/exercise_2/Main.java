package homeword_jdbc.exercise_2;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static String DB_URL = "jdbc:mysql://localhost:3306/minions_db";
    public static String SQL_QUERY = "select v.name, count(m.id) as `count` from villains as v\n" +
            "join minions_villains mv on v.id = mv.villain_id\n" +
            "join minions m on mv.minion_id = m.id\n" +
            "group by v.name\n" +
            "having count > 15\n" +
            "order by count desc;";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Connect to DB
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");


        try (Connection con = DriverManager.getConnection(DB_URL, props);
             PreparedStatement ps = con.prepareStatement(SQL_QUERY)) {


            // 2. Execute prepared statement with parameter
            ResultSet rs = ps.executeQuery();

            // 3. Print results
            while(rs.next()) {
                System.out.printf("%s %s%n",
                        rs.getString("name"),
                        rs.getString("count")
                );
            }

        // 4. Close connection
        } catch (SQLException throwables) {
            System.err.printf("Error closing DB connection %s", throwables.getMessage());
        }

    }
}