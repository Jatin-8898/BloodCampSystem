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


/****************************************************************
   PROGRAM:     Blood Stock
   AUTHOR:      Jatin Varlyani
   CREATED AT:  06/10/2018

   FUNCTION:    This is the Stock Class for the blood camp system.

   INPUT:       Standard Input

   OUTPUT:      The system processes and outputs the appropriate result based
                on the inputs provided to the system.
****************************************************************/
public class BloodStock {
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    private int stockCount;
    private int updatedCount = 1;
    private int decrementedCount;


    /****************************************************************
        FUNCTION:   incrementBloodStock()

        ARGUMENTS:  blood ID

        RETURNS:    It returns the status whether the stock is updated
                    successfully or not.

        NOTES:      This function first gets the current stock and then 
                    if found it updates the stock by 1,
                    if not found it inserts it for the first time.
    ****************************************************************/  
    public void incrementBloodStock(int bloodID){
        conn = MySqlConnect.connectDB();
        
        try{
            String sql = "SELECT * FROM blood_stock WHERE blood_group_id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, bloodID);
            rs = preparedStatement.executeQuery();
            
            int count = rs.getRow();
            if(count > 0){
                
                //System.out.println("BLOOD GRP TYPE ALREADY PRESENT SO GET THE STOCK AND UPDATE");
                stockCount = rs.getInt("blood_stock");
                updatedCount += stockCount;
                
                try {
                    
                    String updateQuery = "UPDATE blood_stock SET blood_stock = ? WHERE blood_group_id = ?";
                    preparedStatement = conn.prepareStatement(updateQuery);
                    preparedStatement.setInt(1, updatedCount);
                    preparedStatement.setInt(2, bloodID);
                    preparedStatement.execute();

                    System.out.println("STOCK UPDATED SUCCESSFULLY");
                
                } catch (SQLException e) {
                    System.out.println("Error :" +e);
                }
                        
            }else{
                //System.out.println("WLL INSERT FOR THE FIRST TIME");
               
                try{
                    String insertQuery = "INSERT INTO blood_stock(blood_group_id, blood_stock) VALUES (?,?)";
                    preparedStatement = conn.prepareStatement(insertQuery);
                    preparedStatement.setInt(1,bloodID);
                    preparedStatement.setInt(2,updatedCount);
                    
                    boolean stmt = preparedStatement.execute();     //this returns false
                    
                    if(!stmt){
                        System.out.println("\nSTOCK SUCCESSFULLY INSERTED");
                        
                    }
                    else{
                        System.out.println("\nSTOCK NOT SUCCESSFULLY INSERTED");
                    }

                }catch(SQLException e){
                    System.out.println("Error :" +e);
                }
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
    }

    /****************************************************************
        FUNCTION:   incrementBloodStock()

        ARGUMENTS:  blood ID

        RETURNS:    It returns the status whether the stock is updated
                    successfully or not.

        NOTES:      This function first gets the current stock and then 
                    if found it decrements the stock by 1,
                    if not found it displays the blood cannot be given.
    ****************************************************************/  
    public void decrementBloodStock(int bloodID) {
        conn = MySqlConnect.connectDB();
        
        try{
            String sql = "SELECT * FROM blood_stock WHERE blood_group_id = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, bloodID);
            rs = preparedStatement.executeQuery();
            
            int count = rs.getRow();
            if(count > 0){
                
                //System.out.println("BLOOD GRP TYPE ALREADY PRESENT SO GET THE STOCK AND DECREMENT");
                stockCount = rs.getInt("blood_stock");
                if(stockCount == 0){
                    System.out.println("THE STOCK IS ZERO, CANNOT RECIEVE THE BLOOD");
                }else{
                    decrementedCount = stockCount - 1;

                    try {

                        String updateQuery = "UPDATE blood_stock SET blood_stock = ? WHERE blood_group_id = ?";
                        preparedStatement = conn.prepareStatement(updateQuery);
                        preparedStatement.setInt(1, decrementedCount);
                        preparedStatement.setInt(2, bloodID);
                        preparedStatement.execute();

                        System.out.println("\nSUCCESSFULLY RECIEVED 1 UNIT OF BLOOD");


                    } catch (SQLException e) {
                        System.out.println("Error :" +e);
                    }
                }
                
            }else{
                System.out.println("CANNOT TAKE THE BLOOD SINCE THE STOCK IS NOT PRESENT");
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
    }
}
