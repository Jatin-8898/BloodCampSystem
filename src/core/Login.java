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
import java.util.*;

/****************************************************************
   PROGRAM:     Login
   AUTHOR:      Jatin Varlyani
   CREATED AT:  06/10/2018

   FUNCTION:    This is the Login class for the blood camp system.

   INPUT:       The details of the user who wants to Login

   OUTPUT:      The system processes and outputs the appropriate result based
                on the inputs provided to the system.
                If proper details then successfully logged into the system.
                else try again until you get the correct Email and Password.
****************************************************************/

public class Login {
    
    Scanner s = new Scanner(System.in);
    private String email;
    private String password;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    
    /****************************************************************
        FUNCTION:   processLogin()

        ARGUMENTS:  None

         RETURNS:    It returns the status whether the Login is successful or not.

        NOTES:      This function checks whether the entered login details 
                    matches in the database.
    ****************************************************************/
    public void processLogin(){
    
        System.out.println("****************LOGIN*********************");
        System.out.println("ENTER THE USER EMAIL");
        email = s.nextLine();
        System.out.println("ENTER THE PASSWORD");
        password = s.nextLine();
        
        conn = MySqlConnect.connectDB();
        
        try {
            String sql = "SELECT * From users WHERE user_email = ? AND user_password = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();

            
            /*IF THE DETAILS R VALID THEN SUCCESSFULLY LOGGED IN*/            
            if(rs.next()){
                System.out.println("\nYOU HAVE SUCCESSFULLY LOGGED IN");
                
            } else{
                System.out.println("\nINVALID DETAILS, PLEASE TRY AGAIN");
            }
        
         } catch (SQLException ex) {
             System.out.println("ERROR"+ ex);
        
        }finally {
            try{
                if(conn != null) {
                    conn.close();
                }
            }catch(SQLException ex){
                 System.out.println(ex.getMessage());
            }
       }

        System.out.println("\n*******************************************");
    }
}
