package homeword_jdbc;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String DB_URL = "jdbc:mysql://localhost:3306/minions_db";

    public static String SQL_QUERY_FOR_GETTING_TOWN_ID = "select id from towns where name = ?;";
    public static String SQL_QUERY_FOR_INSERTING_TOWN = "insert into towns(name, country)\n" +
            "values (?, null);";

    public static String SQL_QUERY_FOR_GETTING_VILLAIN_ID = "select id from villains where name = ?;";
    public static String SQL_QUERY_FOR_INSERTING_VILLAIN = "insert into villains(name, evilness_factor) VALUES (?, 'evil');";

    public static String SQL_QUERY_FOR_GETTING_MINION_ID = "select id from minions where name = ? and age = ?;";

    public static String SQL_QUERY_FOR_INSERTING_MINION = "insert into minions(name, age, town_id) VALUES (?, ?, ?);";

    public static String SQL_QUERY_FOR_MANY_TO_MANY_REL = "insert into minions_villains(minion_id, villain_id) VALUES (?, ?);";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Connect to DB
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");

        try (Connection con = DriverManager.getConnection(DB_URL, props);
             PreparedStatement psTownId = con.prepareStatement(SQL_QUERY_FOR_GETTING_TOWN_ID);
            PreparedStatement psTownInsert = con.prepareStatement(SQL_QUERY_FOR_INSERTING_TOWN);
            PreparedStatement psVillainId = con.prepareStatement(SQL_QUERY_FOR_GETTING_VILLAIN_ID);
             PreparedStatement psVillainInsert = con.prepareStatement(SQL_QUERY_FOR_INSERTING_VILLAIN);
             PreparedStatement psMinionInsert =con.prepareStatement(SQL_QUERY_FOR_INSERTING_MINION);
             PreparedStatement psMinionId = con.prepareStatement(SQL_QUERY_FOR_GETTING_MINION_ID);
             PreparedStatement psManyToManyRel = con.prepareStatement(SQL_QUERY_FOR_MANY_TO_MANY_REL)) {

            // 2. Read query params
            String[] minionData = sc.nextLine().split("\\s+");
            String minionName = minionData[1];
            int minionAge = Integer.parseInt(minionData[2]);
            String minionTown = minionData[3];

            String[] villain = sc.nextLine().split("\\s+");
            String villainName = villain[1];

            // 3. Check whether the town exists and if not add it to the database
            psTownId.setString(1, minionTown);
            ResultSet rsTownExists = psTownId.executeQuery();
            if (!rsTownExists.next()) {
                psTownInsert.setString(1, minionTown);
                psTownInsert.executeUpdate();
                System.out.printf("Town %s was added to the database.%n", minionTown);
            }

            // 4. Check whether the villain exists and if not add him to the database
            psVillainId.setString(1, villainName);
            ResultSet rsVillainExists = psVillainId.executeQuery();
            if (!rsVillainExists.next()) {
                psVillainInsert.setString(1, villainName);
                psVillainInsert.executeUpdate();
                System.out.printf("Villain %s was added to the database.%n", villainName);
            }

            // 5. Insert minion to the database
            psMinionInsert.setString(1, minionName);
            psMinionInsert.setInt(2, minionAge);
            ResultSet rsTownId = psTownId.executeQuery();
            rsTownId.next();
            int townId = rsTownId.getInt("id");
            psMinionInsert.setInt(3, townId);
            psMinionInsert.executeUpdate();

            // 6. Make the many-to-many connection
            psMinionId.setString(1, minionName);
            psMinionId.setInt(2, minionAge);
            ResultSet rsMinionId = psMinionId.executeQuery();
            rsMinionId.next();
            int minonId = rsMinionId.getInt("id");
            psVillainId.setString(1, villainName);
            ResultSet rsVillainId = psVillainId.executeQuery();
            rsVillainId.next();
            int villainId = rsVillainId.getInt("id");
            psManyToManyRel.setInt(1, minonId);
            psManyToManyRel.setInt(2, villainId);
            psManyToManyRel.executeUpdate();
            System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);

        // 7. Close connection
        } catch (SQLException e) {
            System.err.printf("Error closing DB connection %s", e.getMessage());
        }

    }
}