
//                         !! RAM !!

/**
 * ConnectionManager.java
 *
 * Created on November 2, 2007, 9:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd;


/***
 * This class is the older implementation of connection manager using rather older and
 * non portable version of JCBC Technology. It defines the implementations necessary for 
 * efficient database connection management.
 *
 * @author sumit shrestha
 */
public class JDBC_ConnectionManager  {
    
    /*** Creates a new instance of ConnectionManager */
    public JDBC_ConnectionManager() {
    }
    
    public java.sql.DatabaseMetaData  makeConnection( String username, char[] password, String Port ){
    try{

        String url="jdbc:mysql://localhost:" + Port+ "/";

        Class.forName("com.mysql.jdbc.Driver").newInstance(); 

        Conn = java.sql.DriverManager.getConnection(url,username,String.valueOf( password ) );

        ConnectState = true;

        return Conn.getMetaData();
   }
    catch ( java.sql.SQLException ex) {
   
        String Message = "SQLException: " +ex.getMessage() + "\nSQLState: " + ex.getSQLState() + "\nVendorError: " + ex.getErrorCode();
   
        ConnectState= false;
   
        // failure hence no db info   
        return null; 
    }
    catch ( Exception e ){
    //System.out.println( e + " in exception " );
	String Message = e.getMessage();
	
        ConnectState= false;

        // failure hence no db info   
        return null; 
    }
    }// func ends

    
    public boolean isConnected(){
        return ConnectState;        
    }

    public boolean closeConnection(){
        try{
            if( this.Conn == null )// not even connected once ie. at start
                return true;
            else{
                this.Conn.close();
                return true;
            }
        }
        catch( java.sql.SQLException e ){
            return false;
        }
    }
    
    protected java.sql.Connection Conn = null;
    protected boolean ConnectState = false;
}
