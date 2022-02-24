import java.sql.*;
import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * ============================================================================
 * ================================ DBMANAGER =================================
 * ============================================================================
 * DBManager is responsible for all database related responsibilities.
 * Expected typical use case would involve checking active connection, user
 * registration and user login.
 *
 * @author Group23a
 * @email <jd744@kent.ac.uk> <lgb30@kent.ac.uk>
 */
public class DBManager {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    // TODO: Remove main function, it's just a place holder for testing
    public static void main(String[] args) {
        connect();
        if (!isConnected()) {
            System.out.println("\nConnection failed ...");
            return;
        }

        System.out.println("Connected.");
        User john = new User("", "John", "Doe", "jd000@aol.com", null, "02073723427");
        generateID(john);

        User x = register(john, "WatchTower1992");

        System.out.println(x.isNull() ? "User " + john.firstName + " already exists." : "Added new user " + x.firstName);
    }

    /**
     * Attempts connection to database. Function returns
     * result of attempt, false if failed.
     * 
     * @return true if connection successful
     */
    public static boolean connect() {
        try {
            if (connection != null && !connection.isClosed())
                return true; // Already connected.

            Class.forName("com.mysql.cj.jdbc.Driver"); // Grab driver and set connection.
            connection = DriverManager.getConnection(
                    "jdbc:mysql://becncqh5mhfrujm4nsgt-mysql.services.clever-cloud.com/becncqh5mhfrujm4nsgt?user=utgprzq0wm9n97xk&password=a6LBziz1kFxZ294AX63S");
            return true; // Successfully connected.

        } catch (Exception exception) {
            exception.printStackTrace();
            return false; // Something went wrong.
        }
    }

    /**
     * Returns true should both the 'connection' object is
     * instantiated and is open.
     * 
     * @return true if connected
     */
    public static boolean isConnected() {
        if (connection == null)
            return false; // No Connection obj

        try {
            return !connection.isClosed(); // Return if the object connection is Open.
        } catch (SQLException exception) {
            exception.fillInStackTrace();
            return false; // Something went wrong.
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
    public static User logIn(String email, String password) {
        if (!isConnected())
            return User.NULL; // If not connected to DB return

        try {
            statement = connection.createStatement(); // Start statement
            resultSet = statement.executeQuery("SELECT * FROM user where email = " +
                    "'" + email + "'" + " and password = " + "'" + hashPassword(password) + "'"); // Execute query

            if (resultSet.next()) {
                // If query returns result set we know a valid combination has been found,
                // so we return it as User object.
                return new User(resultSet.getString("user_id"), resultSet.getString("first_name"),
                        resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getDate("dob"),
                        resultSet.getString("phone_number"));
            }

            return User.NULL; // No results found from query.
        } catch (Exception e) {
            e.printStackTrace();
            return User.NULL; // Something went wrong, return null object.
        }
    }
    @Test
    public void testLogIn(){
        //Tests that a invalid email and password will return a null user
        assertEquals(User.NULL, logIn("Fakeemail@kent.ac.uk","Fakepassword123"));
        //Tests that a valid email and password returns a User
        User testuser = new User("A70273XF","John","Johnson","jj@kent.ac.uk","null","07836333321");
        assertEquals(testuser,logIn("jj@kent.ac.uk","password"));
       
    }


    public static User register(User newUser, String password) {
        if (!isConnected())
            return User.NULL; // If not connected to DB return

        try {
            statement = connection.createStatement(); // Start statement
            resultSet = statement.executeQuery("SELECT * FROM user where email = " +
                    "'" + newUser.email + "'"); // Execute query

            if (resultSet.next())
                return User.NULL;

            insertUser(newUser, password);

            return newUser;
        } catch (Exception e) {
            e.printStackTrace();
            return User.NULL; // Something went wrong, return null object.
        }
    @Test
    public void testRegister(){
    //test that a already existing user cannot be registered
    User testuser2 = new User("B40212FG","Kane","Kay","kk@kent.ac.uk","null","07836333322");
    assertEquals(User.null,register(testuser,"testpassword123"));
    //test that registering a new user creates a new User
    assertEquals(newuser,register("newuser1","newuser1password"))
    }

    /**
     * Parse given password through MD5 algorithm. Database reference of
     * user passwords are expected to be hashed.
     * 
     * @param password
     * @return hashed
     */
    private static String hashPassword(String password) {
        try {
            // Turn password string into parsed byte arr using MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte[] resultArr = digest.digest();
            StringBuilder builder = new StringBuilder();

            // Take each parsed byte and convert to appended characters
            // through StringBuilder
            for (byte b : resultArr)
                builder.append(String.format("%02x", b));

            // Return final result
            return builder.toString();
        } catch (NoSuchAlgorithmException exception) {

            // This should be impossible
            return "problem with hashing algorithm";
        }
    }

    private static boolean insertUser(User newUser, String password) {
        try {
            java.util.Date date = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            PreparedStatement ps = connection.prepareStatement("INSERT INTO user " +
                    "(user_id, email, password, first_name, last_name, dob, phone_number, created) VALUES " +
                    "(?,?,?,?,?,?,?,?)");
            
            ps.setString(1, newUser.ID);
            ps.setString(2, newUser.email);
            ps.setString(3, hashPassword(password));
            ps.setString(4, newUser.firstName);
            ps.setString(5, newUser.lastName);
            ps.setDate(6, sqlDate);
            ps.setString(7, newUser.phonenumber);
            ps.setDate(8, sqlDate);
            ps.executeUpdate();
            ps.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void generateID(User newUser) {
        Random random = new Random();
        while (newUser.ID.isBlank()) {
            try {
                String newID = "";
                newID += (char) (97 + random.nextInt(25));
                newID += DateTime.getMonth().toString().toCharArray()[0];
                for (int i = 0; i < 4; i++)
                    newID += random.nextInt(10);
                newID += newUser.firstName.toCharArray()[0];
                newID += newUser.lastName.toCharArray()[0];

                statement = connection.createStatement(); // Start statement
                resultSet = statement.executeQuery("SELECT * FROM user where user_id = " +
                        "'" + newID + "'");

                if (!resultSet.next())
                    newUser.ID = newID;

                System.out.println(newUser.toString() + "\n >>> " + newID + " <<<");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
 
}
