/**
 *
 * @author Jatin Varlyani
 */
package core;

import connection.MySqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;


/****************************************************************
   PROGRAM:     Admin
   AUTHOR:      Jatin Varlyani
   CREATED AT:  06/10/2018

   FUNCTION:    This is the Admin class for the blood camp system.
                where the Admin can view the Users, the Donors, and the Seekers. 

   INPUT:       The details of the admin who will enter his Email and Password. 

   OUTPUT:      The system processes and outputs the appropriate result based
                on the inputs provided to the system.
                If admin is already Registered then he can Login Otherwise he
                will have to Register first.
****************************************************************/

public class Admin {
    Scanner s = new Scanner(System.in);
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
    private final String userRole = "admin";
    private final String token = "";
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;



    /****************************************************************
        FUNCTION:   processAdminInfo()

        ARGUMENTS:  None

        RETURNS:    It returns the processAdminLogin or the processAdminRegister
                    depending upon the database.

        NOTES:      This function checks the admin details in the database 
                    if found prompts it for login Else the admin should.
                    register first.
    ****************************************************************/    
    public void processAdminInfo(){
        //If the admin is already Registered tell him to login in.
        conn = MySqlConnect.connectDB();
        
         try {
             
            String sql = "SELECT * From users WHERE user_role = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userRole);
            rs = preparedStatement.executeQuery();

            
            /*IF THE DETAILS R VALID THEN ADMIN IS ALREADY PRESENT DISPAY LOGIN*/            
            if(rs.next()){
                processAdminLogin();
                
            } else{
                processAdminRegister();
            }
        
         } catch (SQLException ex) {
             System.out.println("ERROR"+ ex);
         }
    }
    
    
    
    
    /****************************************************************
        FUNCTION:   processAdminLogin()

        ARGUMENTS:  None

        RETURNS:    It returns the status whether the Login is successful or not.

        NOTES:      This function checks whether the entered login details 
                    matches in the database.
    ****************************************************************/   
    private void processAdminLogin(){
        System.out.println("****************ADMIN LOGIN*********************");
        
        System.out.println("ENTER THE ADMIN EMAIL");
        email = s.nextLine();
        System.out.println("ENTER THE ADMIN PASSWORD");
        password = s.nextLine();

        conn = MySqlConnect.connectDB();
        try {
            String sql = "SELECT * From users WHERE user_email = ? AND user_password = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
     
            if(rs.next()){
                System.out.println("\nADMIN YOU HAVE SUCCESSFULLY LOGGED IN");
                System.out.println("******************************************\n");
                adminOptions();
                
            } else{
                System.out.println("\nINVALID ADMIN DETAILS, PLEASE TRY AGAIN");
            }
        
         } catch (SQLException ex) {
             System.out.println("ERROR"+ ex);
        
        }
       
       
    }
    
    
    
    
    /****************************************************************
        FUNCTION:   processAdminRegister()

        ARGUMENTS:  None

        RETURNS:    It returns the status whether the Registration is successful or not.

        NOTES:      This function inserts the entered registration details 
                    in the database.
    ****************************************************************/ 
    private void processAdminRegister() {
        System.out.println("*****************ADMIN REGISTRATION******************");
        
        System.out.println("ENTER THE FIRST NAME");
        firstName = s.nextLine();
        
        System.out.println("ENTER THE LAST NAME");
        lastName = s.nextLine();
        
        System.out.println("ENTER THE EMAIL");
        email = s.nextLine();

        System.out.println("ENTER THE ADMIN USERNAME");
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
                System.out.println("\nADMIN SUCCESSFULLY REGISTERED, KINDLY LOGIN TO CONTINUE");
                System.out.println("******************************************\n");
                processAdminLogin();
            }
            else{
                System.out.println("\nADMIN RECORD NOT SUCCESSFULLY REGISTERED");
            }

        }catch(SQLException e){
            System.out.println("Error :" +e);
       
        }
        
        
    }

    
    /****************************************************************
        FUNCTION:   adminOptions()

        ARGUMENTS:  None

        RETURNS:    It calls the specified function depending upon the input.

        NOTES:      This function displays the users, or the donors , or the
                    seekers depending upon the admin input.
    ****************************************************************/ 
    private void adminOptions() {
        boolean quit = false;
        int choice;
        displayOptions();
        
        
        while(!quit){
            System.out.println("ADMIN KINDLY ENTER YOUR DESIRED CHOICE:  ");
            choice = s.nextInt();
            
            switch(choice){
                case 1:
                    viewUsers();
                    displayOptions();
                    break;

                case 2:
                    viewDonors();
                    displayOptions();
                    break;

                case 3:
                    viewSeekers();
                    displayOptions();
                    break;

                default:
                    System.out.println("\nEXITING FROM THE ADMIN AREA");
                    System.out.println("***************************************************************");
                    quit = true;
                    break;
                
            }
        }    
        
    }

    
    /****************************************************************
        FUNCTION:   viewUsers()

        ARGUMENTS:  None

        RETURNS:    It returns the list of the Users present in the database.

        NOTES:      This function displays the users.
    ****************************************************************/ 
    private void viewUsers() {
        System.out.println("****************DISPLAYING USER DETAILS*********************");
        conn = MySqlConnect.connectDB();
        
        try {
            String sql = "SELECT * FROM USERS";
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            while(rs.next()){
                 for (int col=1; col < colCount-6; col++) {
                    System.out.print("\nUSERNAME:  ");
                    System.out.print(rs.getString("user_name"));
                    System.out.print("\nEMAIL:     ");
                    System.out.print(rs.getString("user_email"));
                    System.out.print("\nPASSWORD:   ");
                    System.out.print(rs.getString("user_password"));
                    System.out.print("\nFIRST NAME: ");
                    System.out.print(rs.getString("user_firstname"));
                    System.out.print("\nLAST NAME:  ");
                    System.out.print(rs.getString("user_lastname"));
                    System.out.print("\nROLE:       ");
                    System.out.print(rs.getString("user_role"));
                    System.out.println("");
                 }
            }
            System.out.println("***************************************************************");
                        
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
    }
    
    
    
    /****************************************************************
        FUNCTION:   viewDonors()

        ARGUMENTS:  None

        RETURNS:    It returns the list of the Donors present in the database.

        NOTES:      This function displays the donors.
    ****************************************************************/ 
    private void viewDonors() {
        System.out.println("****************DISPLAYING DONORS DETAILS*********************");
        conn = MySqlConnect.connectDB();
        
        try {
            String getDonorInfo = "SELECT * FROM donor";
            preparedStatement = conn.prepareStatement(getDonorInfo);
            rs = preparedStatement.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            while(rs.next()){
                 for (int col=1; col < colCount-10; col++) {
                    System.out.print("\nUSERNAME:    ");
                    System.out.print(rs.getString("donor_username"));
                    System.out.print("\nBLOOD TYPE:  ");
                    System.out.print(rs.getString("donor_blood_type"));
                    System.out.print("\nFIRST NAME:  ");
                    System.out.print(rs.getString("donor_fname"));
                    System.out.print("\nLAST NAME:   ");
                    System.out.print(rs.getString("donor_lname"));
                    System.out.print("\nAGE:         ");
                    System.out.print(rs.getString("donor_age"));
                    System.out.print("\nGENDER:      ");
                    System.out.print(rs.getString("donor_gender"));
                    System.out.print("\nWEIGHT:      ");
                    System.out.print(rs.getString("donor_weight"));
                    System.out.print("\nCITY:        ");
                    System.out.print(rs.getString("donor_city"));
                    System.out.print("\nEMAIL:       ");
                    System.out.print(rs.getString("donor_email"));
                    System.out.print("\nPHONE:       ");
                    System.out.print(rs.getString("donor_phone"));
                    System.out.print("\nADDRESS:     ");
                    System.out.print(rs.getString("donor_address"));
                    System.out.println("");
                 }
            }
            System.out.println("***************************************************************");
                        
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
    }

    
    
    /****************************************************************
        FUNCTION:   viewSeekers()

        ARGUMENTS:  None

        RETURNS:    It returns the list of the Seekers present in the database.

        NOTES:      This function displays the seekers.
    ****************************************************************/ 
    private void viewSeekers() {
        System.out.println("****************DISPLAYING SEEKERS DETAILS*********************");
        conn = MySqlConnect.connectDB();
        
        try {
            String sql = "SELECT * FROM seeker";
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            while(rs.next()){
                 for (int col=1; col < colCount-10; col++) {
                    System.out.print("\nUSERNAME:    ");
                    System.out.print(rs.getString("seeker_username"));
                    System.out.print("\nBLOOD TYPE:  ");
                    System.out.print(rs.getString("seeker_blood_type"));
                    System.out.print("\nFIRST NAME:  ");
                    System.out.print(rs.getString("seeker_fname"));
                    System.out.print("\nLAST NAME:   ");
                    System.out.print(rs.getString("seeker_lname"));
                    System.out.print("\nAGE:         ");
                    System.out.print(rs.getString("seeker_age"));
                    System.out.print("\nG0NDER:      ");
                    System.out.print(rs.getString("seeker_gender"));
                    System.out.print("\nWEIGHT:      ");
                    System.out.print(rs.getString("seeker_weight"));
                    System.out.print("\nCITY:        ");
                    System.out.print(rs.getString("seeker_city"));
                    System.out.print("\nEMAIL:       ");
                    System.out.print(rs.getString("seeker_email"));
                    System.out.print("\nPHONE:       ");
                    System.out.print(rs.getString("seeker_phone"));
                    System.out.print("\nADDRESS:     ");
                    System.out.print(rs.getString("seeker_address"));
                    
                    System.out.println("");
                 }
            }
            System.out.println("***************************************************************");
                        
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
    }

    
    
    /****************************************************************
        FUNCTION:   displayOptions()

        ARGUMENTS:  None

        RETURNS:    None.

        NOTES:      This function displays the options to display the users, 
                    or the donors , or the seekers. 
    ****************************************************************/ 
    private void displayOptions() {
        System.out.println("1. VIEW THE USERS\n"+ 
                           "2. VIEW THE DONORS\n" +
                           "3. VIEW THE SEEKERS\n"
                            + "4. EXIT");
    }

    
    
    
}
