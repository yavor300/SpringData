package homeword_jdbc.exercise_8;

import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String DB_URL = "jdbc:mysql://localhost:3306/minions_db";

    public static String SQL_QUERY_FOR_UPDATING_MINIONS = "update minions\n" +
            "set name = lower(name), age = age + 1\n" +
            "where id = ?;";

    public static String SQL_QUERY_FOR_ALL_MINIONS = "select name, age from minions;";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Connect to DB
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");

        try (Connection con = DriverManager.getConnection(DB_URL, props);
             PreparedStatement psMinionUpadte = con.prepareStatement(SQL_QUERY_FOR_UPDATING_MINIONS);
             PreparedStatement psAllMinions = con.prepareStatement(SQL_QUERY_FOR_ALL_MINIONS);) {

            // 2. Read input
            int[] IDs = Arrays.stream(sc.nextLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();


            // 3. Iterate over each ID
            for (int id : IDs) {
                // 4. Update
                psMinionUpadte.setInt(1, id);
                psMinionUpadte.executeUpdate();
            }

            // 5. Print messages
            ResultSet rsAllMinions = psAllMinions.executeQuery();
            while (rsAllMinions.next()) {
                System.out.printf("%s %d%n",
                        rsAllMinions.getString("name"),
                        rsAllMinions.getInt("age"));
            }

            // 6. Close connection
        } catch (SQLException e) {
            System.err.printf("Error closing DB connection %s", e.getMessage());
        }

    }
}