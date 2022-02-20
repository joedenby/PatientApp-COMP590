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
            connection = DriverManager.getConnection("jdbc:mysql://localhost/COMP5590?user=root&password=D211723m5");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");
            
            while(resultSet.next()){
                System.out.println(resultSet.getString(0) + " - " + resultSet.getString(1) + " - " + resultSet.getString(2));
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }

}