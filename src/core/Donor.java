
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
   PROGRAM:     Donor
   AUTHOR:      Jatin Varlyani
   CREATED AT:  07/10/2018

   FUNCTION:    This is the Donor Class for the blood camp system.

   INPUT:       Standard Input

   OUTPUT:      The system processes and outputs the appropriate result based
                on the inputs provided to the system.
****************************************************************/

public class Donor {
    Scanner s = new Scanner(System.in);
    private String bloodType;
    private String username;
    private String firstName;
    private String lastName;
    private String age;
    private String gender;
    private String weight;
    private String city;
    private String email;
    private String phone;
    private String address;
    
    private String location;
    private String bloodGroupType;

    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;

  
    /****************************************************************
        FUNCTION:   donateBlood()

        ARGUMENTS:  None

        RETURNS:    It returns the status whether the blood is successfully  
                    donated or not.

        NOTES:      This function inserts the donor details in the database
                    and then calls the incrementStock since the stock needs
                    to be updated..
    ****************************************************************/  
    public void donateBlood(){
        System.out.println("****************DONOTE BLOOD*********************");
        System.out.println("ENTER THE BLOOD TYPE");
        bloodType = s.nextLine();
        System.out.println("ENTER THE USERNAME");
        username = s.nextLine();
        System.out.println("ENTER THE FIRST NAME");
        firstName = s.nextLine();
        System.out.println("ENTER THE LAST NAME");
        lastName = s.nextLine();
        System.out.println("ENTER THE AGE");
        age = s.nextLine();
        System.out.println("ENTER THE GENDER");
        gender = s.nextLine();
        System.out.println("ENTER THE WEIGHT");
        weight = s.nextLine();
        System.out.println("ENTER THE CITY");
        city = s.nextLine();
        System.out.println("ENTER THE EMAIL");
        email = s.nextLine();
        System.out.println("ENTER THE PHONE");
        phone = s.nextLine();
        System.out.println("ENTER THE ADDRESS");
        address = s.nextLine();
        
        conn = MySqlConnect.connectDB();
        
        try{
            String sql = "INSERT INTO donor(donor_username, donor_blood_type, donor_fname, donor_lname, donor_age, donor_gender, donor_weight, donor_city, donor_email, donor_phone, donor_address) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,bloodType);
            preparedStatement.setString(3,firstName);
            preparedStatement.setString(4,lastName);
            preparedStatement.setString(5,age);
            preparedStatement.setString(6,gender);
            preparedStatement.setString(7,weight);
            preparedStatement.setString(8,city);
            preparedStatement.setString(9,email);
            preparedStatement.setString(10,phone);
            preparedStatement.setString(11,address);
           

            boolean stmt = preparedStatement.execute();     //this returns false
            if(!stmt){
                System.out.println("\nSUCCESSFULLY DONATED 1 UNIT OF BLOOD");
                
                //First get the bloodtype and bring the id from the blood group table.
                String queryString = "SELECT * FROM blood_group WHERE blood_group_type = ?";
                preparedStatement = conn.prepareStatement(queryString);
                preparedStatement.setString(1, bloodType);
                rs = preparedStatement.executeQuery();
                
                if(rs.next()){
                    int bloodID = rs.getInt("blood_id");

                    //Now the stock table should also be updated
                    BloodStock obj = new BloodStock();
                    obj.incrementBloodStock(bloodID);
                }
                
                displayDonorOption();
            }
            else{
                System.out.println("\nRECORD NOT SUCCESSFULLY DONATED");
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
        
        System.out.println("*******************************************");
    }

    
    
    /****************************************************************
        FUNCTION:   displayDonorOption()

        ARGUMENTS:  None

        RETURNS:    none

        NOTES:      This function asks the donors if he wishes to see
                    the blood camp.
    ****************************************************************/ 
    private void displayDonorOption() {
        System.out.println("DO YOU WANT TO SEE THE BLOOD CAMPS, Press 1");
        int choice = s.nextInt();
        switch(choice){
            case 1:
                BloodCamp obj = new BloodCamp();
                obj.displayCamps();
                break;
            default:
                System.out.println("YOU ENTERED INCORRECT INPUT, PLEASE TRY AGAIN");
                break;
        }
        
    }
    
    
    
    /****************************************************************
        FUNCTION:   findByLocation()

        ARGUMENTS:  None

        RETURNS:    It returns the details of the donors who is available 
                    at the specified location.

        NOTES:      This function just displays the information of the donor details 
                    from the database.
    ****************************************************************/  
    public void findByLocation(){
        System.out.println("ENTER YOUR LOCATION: ");
        location = s.nextLine();
        conn = MySqlConnect.connectDB();

        try {
            String sql = "SELECT * From donor WHERE donor_city = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, location);
            rs = preparedStatement.executeQuery();

            if(rs.next()){
                int count = rs.getRow();
            
                if(count > 0){
                    System.out.println("\nFOUND DONOR, DISPLAYING THE DETAILS");

                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();

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
                    
                    System.out.println("\n********************************************");

                }
                
            }else{
                System.out.println("\nNO DONOR FOUND AT THE ENTERED LOCATION");
            }
            

         } catch (SQLException ex) {
             System.out.println("ERROR"+ ex);
         }
                
    }
    
    
    
    /****************************************************************
        FUNCTION:   findByBloodType()

        ARGUMENTS:  None

        RETURNS:    It returns the details of the donors who is available 
                    of the specified blood type.

        NOTES:      This function just displays the information of the donor details 
                    from the database.
    ****************************************************************/  
    public  void findByBloodType(){
        System.out.println("ENTER YOUR BLOOD TYPE: ");
        bloodGroupType = s.nextLine();
        conn = MySqlConnect.connectDB();

        try {
            String sql = "SELECT * From donor WHERE donor_blood_type = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, bloodGroupType);
            rs = preparedStatement.executeQuery();

            if(rs.next()){
                int count = rs.getRow();
            
                if(count > 0){
                    System.out.println("\nFOUND DONOR, DISPLAYING THE DETAILS");

                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();

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
                    
                    System.out.println("\n********************************************");

                }
                
            }else{
                System.out.println("\nNO DONOR FOUND OF THE ENTERED BLOOD TYPE");
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
                
            
    }
}
