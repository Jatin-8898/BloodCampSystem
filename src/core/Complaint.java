/**
 *
 * @author Jatin Varlyani
 */
package core;

import connection.MySqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/****************************************************************
   PROGRAM:     Complaint
   AUTHOR:      Jatin Varlyani
   CREATED AT:  07/10/2018

   FUNCTION:    This is the Complaint Handler for the blood camp system.

   INPUT:       Standard Input

   OUTPUT:      The system processes and outputs the appropriate result based
                on the inputs provided to the system.
****************************************************************/

public class Complaint {
    Scanner s = new Scanner(System.in);
    private String firstName;
    private String lastName;
    private String email;
    private String subject;
    private String phone;
    private String details;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;

    
    /****************************************************************
        FUNCTION:   complaintDetails()

        ARGUMENTS:  None

        RETURNS:    It returns the status whether is complaint is successfully  
                    registered or not.

        NOTES:      This function inserts the entered complaint details 
                    in the database.
    ****************************************************************/  
    public void complaintDetails(){
    
        System.out.println("****************COMPLAINT*********************");
        System.out.println("ENTER THE FIRST NAME");
        firstName = s.nextLine();
        System.out.println("ENTER THE LAST NAME");
        lastName = s.nextLine();
        System.out.println("ENTER THE EMAIL");
        email = s.nextLine();
        System.out.println("ENTER THE COMPLAINT SUBJECT");
        subject = s.nextLine();
        System.out.println("ENTER THE PHONE");
        phone = s.nextLine();
        System.out.println("ENTER THE COMPLAINT DETAILS");
        details = s.nextLine();
        
        conn = MySqlConnect.connectDB();
        
        try{
            String sql = "INSERT INTO complaint(firstname, lastname, email, phone, complaint_subject, complaint_details) VALUES (?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,subject);
            preparedStatement.setString(5,phone);
            preparedStatement.setString(6,details);

            boolean stmt = preparedStatement.execute();     //this returns false
            if(!stmt){
                System.out.println("YOU HAVE SUCCESSFULLY COMPLAINT ABOUT SYSTEM");
            }
            else{
                System.out.println("THERE WAS SOME ISSUE IN REGISTERING COMPLAINT");
            }

        }catch(SQLException e){
            System.out.println("Error :" +e);
       
        }finally {
            try{
                if(conn != null)
                  conn.close();
            }catch(SQLException ex){
                 System.out.println(ex.getMessage());
            }
       }
        
        System.out.println("*******************************************\n");
    }
}
