package homeword_jdbc.exercise_6;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String DB_URL = "jdbc:mysql://localhost:3306/minions_db";

    public static String SQL_QUERY_FOR_GETTING_THE_VILLAIN_ID = "select id from villains where id = ?;";

    public static String SQL_QUERY_FOR_GETTING_THE_MINIONS_COUNT = "select count(*) as count from minions_villains where villain_id = ?;";

    public static String SQL_QUERY_FOR_REMOVING_RELATION = "delete from minions_villains where villain_id = ?;";

    public static String SQL_QUERY_FOR_DELETING_VILLAIN = "delete from villains where id = ?;";

    public static String SQL_QUERY_FOR_GETTING_VILLAIN_NAME = "select name from villains where id = ?;";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Connect to DB
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");

        try (Connection con = DriverManager.getConnection(DB_URL, props);
             PreparedStatement psVillainID = con.prepareStatement(SQL_QUERY_FOR_GETTING_THE_VILLAIN_ID);
             PreparedStatement psMinionsCount = con.prepareStatement(SQL_QUERY_FOR_GETTING_THE_MINIONS_COUNT);
             PreparedStatement psRemoveRelation = con.prepareStatement(SQL_QUERY_FOR_REMOVING_RELATION);
             PreparedStatement psDeleteVillain = con.prepareStatement(SQL_QUERY_FOR_DELETING_VILLAIN);
             PreparedStatement psGetVillainName = con.prepareStatement(SQL_QUERY_FOR_GETTING_VILLAIN_NAME)) {

            // 2. Read input
            int villainId = Integer.parseInt(sc.nextLine());

            // 3. Check whether villain exist
            psVillainID.setInt(1, villainId);
            ResultSet rsVillainID = psVillainID.executeQuery();
            if (!rsVillainID.next()) {
                System.out.println("No such villain was found");
                return;
            }

            // 4.Get the number of minions serving te villain
            psMinionsCount.setInt(1, villainId);
            ResultSet rsMinionsCount = psMinionsCount.executeQuery();
            rsMinionsCount.next();
            int minionsCount = rsMinionsCount.getInt("count");


            // 5. Remove connection between minions and villain
            psRemoveRelation.setInt(1, villainId);
            psRemoveRelation.executeUpdate();

            // 4. Delete villain
            psGetVillainName.setInt(1, villainId);
            ResultSet rsVillainName = psGetVillainName.executeQuery();
            rsVillainName.next();
            String villainName = rsVillainName.getString("name");
            psDeleteVillain.setInt(1, villainId);
            psDeleteVillain.executeUpdate();

            // 5. Print messages
            System.out.printf("%s was deleted%n%d minions released",villainName,minionsCount);

        // 6. Close connection
        } catch (SQLException e) {
            System.err.printf("Error closing DB connection %s", e.getMessage());
        }

    }
}