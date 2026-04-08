
//                     !! RAM !!

/**
 * User.java
 *
 * Created on November 2, 2007, 8:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData;

/***
 *
 * @author sumit shrestha
 */
public class User {
    
    /*** Creates a new instance of User */
    public User( String UserName , String Type ) {
        this.UserName = UserName;
        this.ConnectionType = Type;
    }
    
    public String getUserName(){
        return this.UserName;
    }
    
    public String getConnectionType(){
        return this.ConnectionType;
    }            
    
    private String UserName = null;
    private String ConnectionType = null;
}
