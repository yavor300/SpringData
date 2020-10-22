package homeword_jdbc.exercise_3;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static String DB_URL = "jdbc:mysql://localhost:3306/minions_db";
    public static String SQL_QUERY_FOR_VILLAIN = "select name from villains where id = ?;";
    public static String SQL_QUERY_FOR_MINIONS_DATA = "select m.name, m.age from villains as v\n" +
            "            join minions_villains mv on v.id = mv.villain_id\n" +
            "            join minions m on mv.minion_id = m.id\n" +
            "            where v.id = ?;";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Connect to DB
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");

        try (Connection con = DriverManager.getConnection(DB_URL, props);
             PreparedStatement ps_minions = con.prepareStatement(SQL_QUERY_FOR_MINIONS_DATA);
            PreparedStatement ps_villain = con.prepareStatement(SQL_QUERY_FOR_VILLAIN)) {

            // 2. Read query params
            int id = Integer.parseInt(sc.nextLine());

            // 3. Set Query Parameter
            ps_villain.setInt(1, id);
            ps_minions.setInt(1, id);

            // 4. Execute prepared statement with parameter
            ResultSet rs_villain = ps_villain.executeQuery();
            ResultSet rs_minions = ps_minions.executeQuery();

            // 5.Check whether the villain exists in the resultset
            if (!rs_villain.next()) {
                System.out.printf("No villain with ID %d exists in the database.", id);
                return;
            }

            // 5. Print results
            System.out.printf("Villain: %s%n", rs_villain.getString("name"));
            int counter = 1;
            while (rs_minions.next()) {
                System.out.printf("%d. %s %d%n", counter, rs_minions.getString("name"), rs_minions.getInt("age"));
                counter++;
            }


        // 8. Close connection
        } catch (SQLException e) {
            System.err.printf("Error closing DB connection %s", e.getMessage());
        }

    }
}