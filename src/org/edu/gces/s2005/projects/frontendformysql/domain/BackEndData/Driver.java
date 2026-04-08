
//                                    !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData;

/***
 * This Class represents Driver to be used to connect to database server.
 * 
 * @author Sumit Shrestha
 */
public class Driver {
    
    public Driver( String DriverName, String DriverJarFileName ){
        this.DriverName = DriverName;
        this.DriverJarFileName = DriverJarFileName;
    }
    
    public void setDatabaseServerName( String Nm ){
        this.DatabaseServerName = Nm;
    }
    
    public void setDatabaseServerPortNo( String pn){
        this.DatabaseServerPortNo = pn;
    }
    
    public void setDatabaseServerURL( String url ){
        this.DatabaseServerURL = url;
    }
    
    public void setDriver( java.sql.Driver D ){
        this.Driver = D;
    }
    
    public void setDriverID( String ID ){
        this.DriverID = ID;
    }
    
    public java.sql.Driver getDriver( ){
        return this.Driver;
    }
    
    public String getDriverName(){
        return this.DriverName;
    }
    
    public String getDriverJarFileName(){
        return this.DriverJarFileName;
    }
    
    public  String getDatabaseServerName( ){
        return this.DatabaseServerName;
    }
    
    public  String getDatabaseServerPortNo(){
        return this.DatabaseServerPortNo ;
    }
    
    public  String getDatabaseServerURL( ){
        return this.DatabaseServerURL ;
    }
    
    public String getDriverID(){
        return this.DriverID;
    }
    
    // private fields
    private String DriverName = null;
    private String DriverJarFileName = null;
    private String DatabaseServerName = null;
    private String DatabaseServerURL = null;
    private String DatabaseServerPortNo = null;
    private java.sql.Driver Driver = null;
    private String DriverID = null;
}
