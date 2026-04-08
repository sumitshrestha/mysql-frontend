
//                    !! RAM !!

/**
 * Database.java
 *
 * Created on July 21, 2007, 3:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData;

/***
 *
 * @author sumit
 */
public class Database implements org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DatabaseInterface{
    
    /*** Creates a new instance of Database */
    public Database( String Nm ) {        
        Name = Nm; // give Database Name
    }
    
    public String getName(){
        return Name;
    }   
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table[] getTables(){
        return this.Table;
    }
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.View[] getViews(){
        return this.View;
    }
    
    public void setName( String Nm )
    {
        Name = Nm;
    }// func ends
    
    public void setTables( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table[] Tb ){
        this.Table = Tb;
    }
    
    public void setViews( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.View[] Vw ){
        this.View = Vw;
    }
    
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table[] Table = null;
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.View[] View = null;
    private String Name=null;
    
}
