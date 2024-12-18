import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    public void register(){
        scanner.nextLine();
        System.out.println("Fullname: ");
        String full_name = scanner.nextLine();
        System.out.println("Email: ");
        String mail_id = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();

        if(user_exist(mail_id)){
            System.out.println("User Already Exist with this mail-id....");
            return;
        }
        String register_query = "INSERT INTO User(full_name,mail_id,password) VALUES(?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(register_query);
            preparedStatement.setString(1,full_name);
            preparedStatement.setString(2,mail_id);
            preparedStatement.setString(3,password);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows  > 0){
                System.out.println("Registration Succesfull...");
            }
            else {
                System.out.println("Registration Failed...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String login(){
        scanner.nextLine();
        System.out.println("Email: ");
        String mail_id = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        String login_query = "SELECT * FROM User WHERE mail_id = ? AND password = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1,mail_id);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return mail_id;
            }else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean user_exist(String mail_id){
        String query = "SELECT * FROM user WHERE mail_id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,mail_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  false;
    }
}
