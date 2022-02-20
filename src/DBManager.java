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
            connection = DriverManager.getConnection("jdbc:mysql://localhost/account?user=root&password=D211723m5");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM user");
            
            while(resultSet.next()){
                System.out.println(resultSet.getString("user_id") + " - " + resultSet.getString("email") + " - " + resultSet.getString("password"));
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }

}