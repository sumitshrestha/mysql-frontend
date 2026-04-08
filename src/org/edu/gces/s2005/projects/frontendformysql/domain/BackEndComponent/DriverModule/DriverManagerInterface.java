
//                          !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule;

import java.sql.DatabaseMetaData;

/***
 *
 * @author Sumit Shrestha
 */
public interface DriverManagerInterface {
    
    /**
     * This method will close the connection
     * 
     * @return true if successfull connection else false
     */
    boolean closeConnection();

    /**
     * This method will report if system is presently connected to server
     * 
     * @return true if successfully connected else false
     */
    boolean isConnected();
    
    /**
     * This method will use default driver to connect to particular URL.
     *
     * 
     * @param username name of user to connect. it cannot be null
     * @param password password to connect. it cannot be null. 
     * 
     * @return metadata of database connection if successfully connected else null
     */
    DatabaseMetaData makeConnection( String username, char[] password);
    
    /**
     * The method will set a default driver for the system from which to load.
     * The driver to be set must be already laoded.
     * 
     * @param DriverID ID of driver to be made default
     * @return boolean true if driver is made default else false
     */
    boolean setDefaultDriver( String DriverID );
    
    /**
     * This method will give the default driver in the system. 
     * 
     * @return default driver object if it is set else null.
     */
    org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver getDefaultDriver();
       
    
    /**
     * This method will be used to add driver to system. so to add a new driver the user have to specify needed parameters. 
     * 
     * 
     * @param DriverName Name of driver to be added
     * @param absPath absolute path of Driver
     * 
     * @return Driver object if valid else null
     */
    java.sql.Driver addDriver( String DriverName, String absPath );
    
    
    /**
     * This method will be used to delete the driver.
     * Calling this method will permanently delete the driver as well as its related info. So, this method must be called with caution.
     * 
     * @param DriverID ID of driver to be deleted.
     * 
     * @return true if successfully deleted else false.
     *
     */
    boolean deleteDriver( String DriverID );
    
    
    /**
     * This method will return name of driver that is most recently added to system. if no driver has yet been added to system then it will return null. it can be used to check if there is any drivers added to system.
     * @return ID of Driver most recnetly added
     */
    String getRecentlyAddedDriverID();
    
    /**
     * This method will give error message for preceeding operation that the manager performed. 
     * on every successfull operation performed it will be null else it will hold a error message.
     * 
     * @return Error message if occured else null
     */
    String getErrorMessage();
    
    
    /**
     * Methods to modify existing drivers status. These methods will set new values of the drivers that are already loaded by system i.e. kept in system database.
     * It will take DriverName as reference while setting new values.
     * 
     * @returns true if successfully set else false
     */
    
    /**
     * This method will be used to register listener to Driver changes.
     * 
     * 
     * @param DrvList Name of Driver Listener to be registered
     * 
     * @return true if registered successfully else false
     */            
    boolean registerDriverListener( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DriverListener DrvList );
    
    /**
     * @param DriverID ID of driver to ve renamed
     * @param DriverServerName Name of server to which driver connects
     */
    boolean renameDriverServerName( String DriverID, String DriverServerName );
    
    /**
     * @param DriverID ID of driver to ve renamed
     * @param URL Url of database server without port no. system will use existing port no to form actual URL
     */
    boolean renameDriverServerURL( String DriverID, String URL );
    
    /**
     * @param DriverName Name of driver to ve renamed
     * @param Drv Driver obj of driver. it is primarily done since driver name n its jar file name are inistially known but driver object is later loaded by system.
     */
    boolean setDriver( String DriverName, java.sql.Driver Drv );

    /**
     * This method will return the dirvers that were added n loaded by the system.
     * 
     * @return Array of Driver that has been loaded.
     */
    org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver[] getLoadedDriver();
    
    
    //////////// Driver Modification method ends /////////////////////////////////////////////////
    
    // constant fields
    String SuccessfullDriverAddition = "Driver Added Successfully";
    
    String SuccessfullConnection = "Successfully connected";
    
    String SuccessfullModification = "Driver Value Successfully Modified";// fiels for signalling successfull modification of driver values.
    
    String DefaultDriverSetSuccess = "Drier being set Default";
    
    String SuccessfullDeletion = "Successfully Deleted";
}
