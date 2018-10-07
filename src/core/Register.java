/**
 *
 * @author Jatin Varlyani
 */
package core;

import connection.MySqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


/****************************************************************
   PROGRAM:     Register
   AUTHOR:      Jatin Varlyani
   CREATED AT:  06/10/2018

   FUNCTION:    This is the Register class for the blood camp system.

   INPUT:       The details of the user who wants to register

   OUTPUT:      The system processes and outputs the appropriate result based
                on the inputs provided to the system.
                If proper details then the Registration is successful.
****************************************************************/

public class Register {
    Scanner s = new Scanner(System.in);
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private final String userRole = "user";
    private final String token = "";
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private final ResultSet rs = null;
    
    
    /****************************************************************
        FUNCTION:   processRegister()

        ARGUMENTS:  None

        RETURNS:    It returns the status whether the Registration is successful or not.

        NOTES:      This function inserts the entered registration details 
                    in the database.
    ****************************************************************/ 
    public void processRegister() {
    
        System.out.println("*****************REGISTER******************");
        
        System.out.println("ENTER THE FIRST NAME");
        firstName = s.nextLine();
        
        System.out.println("ENTER THE LAST NAME");
        lastName = s.nextLine();
        
        System.out.println("ENTER THE EMAIL");
        email = s.nextLine();

        System.out.println("ENTER THE USERNAME");
        username = s.nextLine();

        System.out.println("ENTER THE PASSWORD");
        password = s.nextLine();

        System.out.println("ENTER THE PASSWORD AGAIN");
        confirmPassword = s.nextLine();
        
        conn = MySqlConnect.connectDB();
        
        try{
            String sql = "INSERT INTO users(user_name, user_password, user_firstname, user_lastname, user_email, user_role, token) VALUES (?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,firstName);
            preparedStatement.setString(4,lastName);
            preparedStatement.setString(5,email);
            preparedStatement.setString(6,userRole);
            preparedStatement.setString(7,token);

            boolean stmt = preparedStatement.execute();     //this returns false
            if(!stmt){
                System.out.println("\nSUCCESSFULLY REGISTERED, YOU CAN NOW LOG IN");
                System.out.println("******************************************\n");
                Login login  = new Login();
                login.processLogin();
                
            }
            else{
                System.out.println("\nRECORD NOT SUCCESSFULLY REGISTERED");
            }

        }catch(SQLException e){
            System.out.println("Error :" +e);
       
        }finally {
            try{
                if(conn != null) {
                    conn.close();
                }
            }catch(SQLException ex){
                 System.out.println(ex.getMessage());
            }
       }
        
        System.out.println("******************************************\n");
    }
    
}
