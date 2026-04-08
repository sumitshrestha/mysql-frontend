
//                    !! RAM !!

/**
 * JDBC2_ConnectionManager.java
 *
 * Created on November 2, 2007, 10:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd;

/***
 * This is the more portable version of connection manager using JDBC2 Technology.
 * This class is the subclass of earlier connection manager that overrides the makeconnection
 * method of earlier class.
 *
 * @author sumit shrestha
 */
public class JDBC2_ConnectionManager extends org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.JDBC_ConnectionManager {
    
    /*** Creates a new instance of JDBC2_ConnectionManager */
    public JDBC2_ConnectionManager() {
    }
    
    /**
     * this overriding method will make connection and return database meta data object on successfull connecion else use super class implementation in case of failure to do and return its result.
     */
    public java.sql.DatabaseMetaData  makeConnection( String username, char[] password, String Port ){
    
        try{                
        Object LoadedInstance = Class.forName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource").newInstance(); 
        
        javax.sql.DataSource LoadedDataSource = ( javax.sql.DataSource ) LoadedInstance;
                
        Conn = LoadedDataSource.getConnection( username, String.valueOf( password ) ); 
        
        if( Conn == null ) // if this implementation failed then try its super class's'
            return super.makeConnection( username, password, Port );
        
        else{
            
        ConnectState = true;

        return Conn.getMetaData();
        }
        
        }
        catch( Exception e ){
            System.out.println( "exception occured in jdbc"+e );
            return super.makeConnection( username, password, Port );
        }        
    }// func ends
    
}
