import org.w3c.dom.ls.LSOutput;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApplication {
    private static final String url ="jdbc:mysql://localhost:3306/banking_system";
    private static final String username ="root";
    private static final String password ="KP28@tanu";



    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            System.out.println("connected");
            Scanner scanner = new Scanner(System.in);
            User user = new User(connection,scanner);
            Accounts accounts = new Accounts(connection,scanner);
            AccountManager accountManager = new AccountManager(connection,scanner);

            String mail_id;
            long account_number;

            while (true){
                System.out.println("****WELCOME TO BANKING SYSTEM****");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println("ENTER YOUR CHOICE");
                int choice1 = scanner.nextInt();
                switch (choice1){
                    case 1:
                        user.register();
                        System.out.println("\003[H\003[2J");
                        System.out.flush();
                        break;
                    case 2:
                        mail_id = user.login();
                        if(mail_id!=null){
                            System.out.println();
                            System.out.println("User logged in!");
                            if(!accounts.account_exist(mail_id)){
                                System.out.println();
                                System.out.println("1. Open new bank account");
                                System.out.println("2. Exit");
                                if(scanner.nextInt() == 1){
                                    account_number = accounts.open_account(mail_id);
                                    System.out.println("Account Created Succesfully");
                                    System.out.println("Your account number is:" + account_number);
                                }else {
                                    break;
                                }

                            }
                            account_number = accounts.getAccount_number(mail_id);
                            int choice2 = 0;
                            while (choice2 != 5){
                                System.out.println();
                                System.out.println("1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Logout");
                                System.out.println("Enter your choice: ");
                                choice2 = scanner.nextInt();
                                switch (choice2){
                                    case 1:
                                        accountManager.debit_money(account_number);
                                        break;
                                    case 2:
                                        accountManager.credit_money(account_number);
                                        break;
                                    case 3:
                                        accountManager.transfer_money(account_number);
                                        break;
                                    case 4:
                                        accountManager.getBalance(account_number);
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Please enter valid choice....");
                                        break;
                                }
                            }
                        }
                        else {
                            System.out.println("Incorrect email or password.....");
                        }
                    case 3:
                        System.out.println("Thanks for using ABC Bank....");
                        System.out.println("Exit System");
                        return;
                    default:
                        System.out.println("Enter the valid choice...");
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
