package homeword_jdbc.exercise_7;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String DB_URL = "jdbc:mysql://localhost:3306/minions_db";

    public static String SQL_QUERY_FOR_GETTING_MINIONS_NAMES = "select name from minions;";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        //TODO: IN ORDER TO GET THE RIGHT DATA YOU NEED TO REMOVE THE LAST 4 ROWS
        // FROM THE MINIONS TABLE, BECAUSE WE INSERTED DATA IN EXERCISE 4.
        // HERE ARE THE NECESSARY QUERIES:
        // 'delete from minions_villains where minion_id = 51 or minion_id = 52 or minion_id = 53 or minion_id = 54;'
        // and
        // 'delete from minions where id = 51 or id = 52 or id = 53 or id = 54;'.

        // 1. Connect to DB
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");

        try (Connection con = DriverManager.getConnection(DB_URL, props);
             PreparedStatement psMinionsNames = con.prepareStatement(SQL_QUERY_FOR_GETTING_MINIONS_NAMES);) {

            List<String> minionsInitial = new ArrayList<>();
            List<String> minionsArranged = new ArrayList<>();

            // 2. Fill in the initial ArrayList
            ResultSet rsMinionsNames = psMinionsNames.executeQuery();
            while (rsMinionsNames.next()) {
                minionsInitial.add(rsMinionsNames.getString("name"));
            }

            // 3.Fill in the arranged matrix
            while (minionsInitial.size() > 0) {
                minionsArranged.add(minionsInitial.get(0));
                minionsInitial.remove(0);
                if (minionsInitial.size() > 0) {
                    minionsArranged.add(minionsInitial.get(minionsInitial.size() - 1));
                    minionsInitial.remove(minionsInitial.size() - 1);
                }
            }

            // 4. Print
            minionsArranged
                    .forEach(System.out::println);

        // 6. Close connection
        } catch (SQLException e) {
            System.err.printf("Error closing DB connection %s", e.getMessage());
        }

    }
}