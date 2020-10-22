package homeword_jdbc.exercise_5;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String DB_URL = "jdbc:mysql://localhost:3306/minions_db";

    public static String SQL_QUERY_FOR_GETTING_THE_COUNT_OF_TOWNS = "select count(*) as count from towns where country = ?;";

    public static String SQL_QUERY_FOR_UPDATING_TOWNS_NAMES = "update towns\n" +
            "set name = upper(name)\n" +
            "where country = ?;";

    public static String SQL_QUERY_FOR_GETTING_TOWNS_NAMES = "select name from towns where country = ?;";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Connect to DB
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");

        try (Connection con = DriverManager.getConnection(DB_URL, props);
             PreparedStatement psTownsCount = con.prepareStatement(SQL_QUERY_FOR_GETTING_THE_COUNT_OF_TOWNS);
             PreparedStatement psUpdateTownsNames = con.prepareStatement(SQL_QUERY_FOR_UPDATING_TOWNS_NAMES);
             PreparedStatement psGetAllTownNames = con.prepareStatement(SQL_QUERY_FOR_GETTING_TOWNS_NAMES);) {

            // 2. Read query params
            String country = sc.nextLine();

            // 3. Check whether towns exist
            psTownsCount.setString(1, country);
            ResultSet rsTownsCount = psTownsCount.executeQuery();
            rsTownsCount.next();
            int townsCount = rsTownsCount.getInt("count");
            if (townsCount <= 0) {
                System.out.println("No town names were affected.");
                return;
            }

            // 4. Update cities
            psUpdateTownsNames.setString(1, country);
            psUpdateTownsNames.executeUpdate();

            // 5. Print messages
            psGetAllTownNames.setString(1, country);
            ResultSet rsAllTowns = psGetAllTownNames.executeQuery();
            System.out.printf("%d town names were affected.%n[", townsCount);
            int iterator = 0;
            while (rsAllTowns.next()) {
                if (iterator == townsCount - 1) {
                    System.out.printf("%s]", rsAllTowns.getString("name"));
                } else {
                    System.out.printf("%s, ", rsAllTowns.getString("name"));
                }
                iterator++;
            }

        // 6. Close connection
        } catch (SQLException e) {
            System.err.printf("Error closing DB connection %s", e.getMessage());
        }

    }
}