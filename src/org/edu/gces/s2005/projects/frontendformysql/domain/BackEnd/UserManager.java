
//                      !! RAM !!

/**
 * UserManager.java
 *
 * Created on November 2, 2007, 7:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd;

/***
 *
 * @author sumit shrestha
 */
public class UserManager {
    
    /*** Creates a new instance of UserManager */
    public UserManager() {
    }
       
    public void onConnection( java.sql.Connection conn ){
        this.Conn = conn;
        try{
        this.DatabaseInfo = this.Conn.getMetaData();
        }
        catch( java.sql.SQLException e ){
            
        }       
    }
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.User[] getUsersInServer(){
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.User[] Users = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.User[1];
        try{
        Users[0] = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.User( this.DatabaseInfo.getUserName(), "localhost" );
        }
        catch( java.sql.SQLException e ){
            Users[0] = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.User( null, null );
        }
        return Users;
    }
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.User getPresentUser(){
        try{
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.User PresentUser = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.User( this.DatabaseInfo.getUserName(), "localhost" );
        return PresentUser;
        }
        catch( java.sql.SQLException e ){
            return null;
        }
    }
    
    private java.sql.DatabaseMetaData DatabaseInfo = null;
    private java.sql.Connection Conn = null;
}
