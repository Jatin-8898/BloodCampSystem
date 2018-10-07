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
   PROGRAM:     Seeker
   AUTHOR:      Jatin Varlyani
   CREATED AT:  07/10/2018

   FUNCTION:    This is the Seeker Class for the blood camp system.

   INPUT:       Standard Input

   OUTPUT:      The system processes and outputs the appropriate result based
                on the inputs provided to the system.
****************************************************************/
public class Seeker {
    
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
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    
    
    /****************************************************************
        FUNCTION:   seekBlood()

        ARGUMENTS:  None

        RETURNS:    It returns the status whether the blood is successfully  
                    received or not.

        NOTES:      This function inserts the seekers details in the database
                    and then calls the decrementStock since the stock needs
                    to be updated..
    ****************************************************************/  

    public void seekBlood() {
        System.out.println("****************SEEK BLOOD*********************");
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
            String sql = "INSERT INTO seeker(seeker_username, seeker_blood_type, seeker_fname, seeker_lname, seeker_age, seeker_gender, seeker_weight, seeker_city, seeker_email, seeker_phone, seeker_address) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
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
                
                //First get the bloodtype and bring the id from the blood group table.
                String queryString = "SELECT * FROM blood_group WHERE blood_group_type = ?";
                preparedStatement = conn.prepareStatement(queryString);
                preparedStatement.setString(1, bloodType);
                rs = preparedStatement.executeQuery();
                
                if(rs.next()){
                    int bloodID = rs.getInt("blood_id");

                    //Now the stock table should also be updated
                    BloodStock obj = new BloodStock();
                    obj.decrementBloodStock(bloodID);
                }
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
    
}
