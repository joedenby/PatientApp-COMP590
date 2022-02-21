import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Scanner;
import java.security.MessageDigest;

public class DBManager {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    
    public static void main(String[] args) {
        connect();
        if(!isConnected()) {
            System.out.println("\nConnection failed ...");
            return;
        }
        
        System.out.println("Connected.");
        Scanner input = new Scanner(System.in);

        System.out.println("\nEnter email:");
        String email = input.nextLine();
        System.out.println("\nEnter " + email + " password:");
        String password = input.nextLine();

        User x = logIn(email, password);
        System.out.println(x.isNull() ? "\n" + email + " loggin failed." : "\n" + email + " is logged in.");
        input.close();

        if(x.isNull()) return;
        System.out.println("================================");
        System.out.println(x.toString());
        System.out.println("================================");
    }

    /**
     * Attempts connection to database. Function returns
     * result of attempt, false if failed.
     * 
     * @return true if connection successful
     */
    public static boolean connect() {
        try {
            if(connection != null && !connection.isClosed()) 
                return true;   // Already connected.

            Class.forName("com.mysql.cj.jdbc.Driver");  // Grab driver and set connection.
            connection = DriverManager.getConnection("jdbc:mysql://becncqh5mhfrujm4nsgt-mysql.services.clever-cloud.com/becncqh5mhfrujm4nsgt?user=utgprzq0wm9n97xk&password=a6LBziz1kFxZ294AX63S");
            return true;    // Successfully connected.
        
        } catch (Exception e) {
            e.printStackTrace();
            return false;   // Something went wrong.
        }
    }

    /**
     * Returns true should both the 'connection' object is 
     * instantiated and is open. 
     * 
     * @return true if connected
     */
    public static boolean isConnected() {
        if (connection == null) return false;   // No Connection obj
        
        try {
            return !connection.isClosed();  // Return if the object connection is Open.
        } catch (SQLException exception) {
            exception.fillInStackTrace();
            return false;   // Something went wrong.
        }
    }

    /**
     * Attempts to return User object matching valid email & password
     * in database. Should invalid details be passed a NULL representation of
     * the User object is returned.
     * 
     * @param email, password
     * @return User
     */
    public static User logIn (String email, String password) {
        if(!isConnected()) return User.NULL;    //If not connected to DB return

        try {
            statement = connection.createStatement();   //Start statement
            resultSet = statement.executeQuery("SELECT * FROM user where email = " + 
                "'" + email + "'" + " and password = " + "'" + hashPassword(password) + "'");     //Execute query
            
            while(resultSet.next()) {   
                //If query returns result set we know a valid combination has been found, 
                //so we return it as User object.
                return new User(resultSet.getString("user_id"), resultSet.getString("first_name"), 
                    resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("dob"), resultSet.getString("phone_number"));
            }

            return User.NULL;   //No results found from query.
        } catch (Exception e) {
            e.printStackTrace();
            return User.NULL;   //Something went wrong, return null object.
        }
    } 

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte[] resultArr = digest.digest();
            StringBuilder builder = new StringBuilder();
            
            for (byte b : resultArr)
                builder.append(String.format("%02x", b));

            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {
            return "";
        }
    }
}