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
   PROGRAM:     BloodCamp
   AUTHOR:      Jatin Varlyani
   CREATED AT:  06/10/2018

   FUNCTION:    This is the BloodCamp class for the blood camp system.
                where the BloodCamp are displayed. 

   INPUT:       No such input required. 

   OUTPUT:      The system outputs the appropriate result based on
                the details in the database. 
                
****************************************************************/

public class BloodCamp {
    Scanner s = new Scanner(System.in); 
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;

    
    
    /****************************************************************
        FUNCTION:   displayCamps()

        ARGUMENTS:  None

        RETURNS:    It returns the BloodCamp which contains the info
                    about Location and the date of the blood camp.

        NOTES:      This function fetches the details from the database 
                    and displays it.
    ****************************************************************/
    public void displayCamps() {
        System.out.println("****************BLOOD CAMPS*********************");
        conn = MySqlConnect.connectDB();
        
        try {
            String sql = "SELECT * FROM blood_camps";
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            while(rs.next()){
                 for (int col=1; col < colCount-1; col++) {
                    System.out.print("\nTHE LOCATION OF BLOOD CAMP IS: ");
                    System.out.print(rs.getString("blood_camp_location"));
                    System.out.print("\nTHE DATE OF BLOOD CAMP IS: ");
                    System.out.print(rs.getString("blood_camp_date"));
                    System.out.println("");
                 }
            }
            System.out.println("\n**********************************************");
                        
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
