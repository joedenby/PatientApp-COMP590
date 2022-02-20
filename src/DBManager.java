import java.sql.*;

public class DBManager {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;


    public static void main(String[] args) {
        connect();
        if(!isConnected()) {
            System.out.println("Connection failed ...");
            return;
        }
        System.out.println("Connected.");
        User x = logIn("jd744@kent.ac.uk", "EndlessNameless1");
        String s = x.equals(User.NULL) ? "Failed to login" : "Logged success!";
        System.out.println(s);
    }

    public static boolean connect() {
        if(connection != null) return true;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://becncqh5mhfrujm4nsgt-mysql.services.clever-cloud.com/becncqh5mhfrujm4nsgt?user=utgprzq0wm9n97xk&password=a6LBziz1kFxZ294AX63S");
            return true;
        
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isConnected() {
        if (connection == null) return false;
        
        try {
            return !connection.isClosed();
        } catch (SQLException exception) {
            exception.fillInStackTrace();
            return false;
        }
    }

    public static User logIn (String email, String password) {
        if(!isConnected()) return User.NULL;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM user where email = " + "'" + email + "'" + " and password = " + "'" + password + "'");
            
            while(resultSet.next()) {
                return new User(resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), true);
            }

            return User.NULL;
        } catch (Exception e) {
            e.printStackTrace();
            return User.NULL;
        }
    } 
    

}