
/**
 *
 * @author fredylezama
 */

import java.sql.*;
import javax.swing.JOptionPane;


public class Jconnect {
    
    Connection connect = null;
    
    public static Connection ConnectDatabase(){
    
        try{
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:/Users/fredylezama/NetBeansProjects/EliteBankingSystem/EliteBankSystem.sqlite");
            
            return connect;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
           
        }
        return null;
    }
}