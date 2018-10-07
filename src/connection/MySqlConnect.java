/**
 *
 * @author Jatin Varlyani
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/****************************************************************
   PROGRAM:     MySqlConnect
   AUTHOR:      Jatin Varlyani
   CREATED AT:  06/10/2018

   FUNCTION:    This is the Database Controller for the blood camp system.

   INPUT:       Standard Input

   OUTPUT:      The system does the connection with the database and returns
                the connection object.
****************************************************************/
public class MySqlConnect {
    
    
    /****************************************************************
        FUNCTION:   connectDB()

        ARGUMENTS:  None

        RETURNS:    It returns the connection object

        NOTES:      This function attempts to make the connection with the database.
    ****************************************************************/  
    public static Connection connectDB(){
        Connection conn = null;
        
        try {
            String url       = "jdbc:mysql://localhost:3306/blood_camp";
            String user      = "Jatin8898";
            String password  = "kingjatin";
            
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                //System.out.println("Connected to the database");
            }
            return conn;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());      
            return null;
                  
        }
        catch (Exception e) {
            System.out.println(e.getMessage());   
            return null;
        
        }
    }
    
    
}
