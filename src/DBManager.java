import java.sql.*;

public class DBManager {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;


    public static void main(String[] args) {
        connect();
    }

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://becncqh5mhfrujm4nsgt-mysql.services.clever-cloud.com/becncqh5mhfrujm4nsgt?user=utgprzq0wm9n97xk&password=a6LBziz1kFxZ294AX63S");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM user");
            
            while(resultSet.next()){
                System.out.println(resultSet.getString("user_id") + " - " + resultSet.getString("email") + 
                    " - " + resultSet.getString("password"));
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}