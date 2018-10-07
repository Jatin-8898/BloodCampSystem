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
   PROGRAM:     Contact Us
   AUTHOR:      Jatin Varlyani
   CREATED AT:  07/10/2018

   FUNCTION:    This is the Contact Us Handler for the blood camp system.

   INPUT:       Standard Input

   OUTPUT:      The system processes and outputs the appropriate result based
                on the inputs provided to the system.
****************************************************************/

public class ContactUs {
    
    Scanner s = new Scanner(System.in);
    private String name;
    private String email;
    private String subject;
    private String phone;
    private String message;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;

    
    /****************************************************************
        FUNCTION:   contactUsDetails()

        ARGUMENTS:  None

        RETURNS:    It returns the status whether is contact us is successfully  
                    registered or not.

        NOTES:      This function inserts the entered contact us details 
                    in the database.
    ****************************************************************/  
    public void contactUsDetails(){
    
        System.out.println("****************CONTACT US*********************");
        System.out.println("ENTER THE NAME");
        name = s.nextLine();
        System.out.println("ENTER THE EMAIL");
        email = s.nextLine();
        System.out.println("ENTER THE SUBJECT");
        subject = s.nextLine();
        System.out.println("ENTER THE PHONE");
        phone = s.nextLine();
        System.out.println("ENTER THE MESSAGE");
        message = s.nextLine();
        
        conn = MySqlConnect.connectDB();
        
        try{
            String sql = "INSERT INTO enquiry(enquiry_name, enquiry_email, enquiry_subject, enquiry_phone, enquiry_message) VALUES (?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,subject);
            preparedStatement.setString(4,phone);
            preparedStatement.setString(5,message);

            boolean stmt = preparedStatement.execute();     //this returns false
            if(!stmt){
                System.out.println("\nYOU HAVE SUCCESSFULLY CONTACTED US");
            }
            else{
                System.out.println("\nTHERE WAS SOME ISSUE");
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
        
        System.out.println("*******************************************\n");
    }
}
