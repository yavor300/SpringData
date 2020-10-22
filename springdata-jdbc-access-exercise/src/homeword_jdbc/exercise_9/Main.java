package homeword_jdbc.exercise_9;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String DB_URL = "jdbc:mysql://localhost:3306/minions_db";

    public static String SQL_QUERY_FOR_CALLING_PROCEDURE = "call usp_get_older(?);";

    public static String SQL_QUERY_FOR_FINDING_MINION = "select name, age from minions where id = ?;";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Connect to DB
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "1234");

        try (Connection con = DriverManager.getConnection(DB_URL, props);
             PreparedStatement psCallingProcedure = con.prepareStatement(SQL_QUERY_FOR_CALLING_PROCEDURE);
             PreparedStatement psFindMinion = con.prepareStatement(SQL_QUERY_FOR_FINDING_MINION);) {

            // 2. Read input
            int id = Integer.parseInt(sc.nextLine());

            // 3. Call the procedure
            psCallingProcedure.setInt(1, id);
            psCallingProcedure.executeQuery();


            // 4. Print messages
            psFindMinion.setInt(1, id);
            ResultSet rsMinion = psFindMinion.executeQuery();
            rsMinion.next();
            System.out.printf("%s %d", rsMinion.getString("name"),
                    rsMinion.getInt("age"));

            // 6. Close connection
        } catch (SQLException e) {
            System.err.printf("Error closing DB connection: %s", e.getMessage());
        }

    }
}