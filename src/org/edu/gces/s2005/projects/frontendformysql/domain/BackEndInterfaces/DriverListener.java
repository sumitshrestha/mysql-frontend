
//                                 !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 * This Interface is for driver listener so that any class that implements it will be a listener to driver changes. 
 * To get nottify of changes the implementing class must register itself to DriverManager through registerListener method.
 * 
 * @author Sumit Shrestha   
 */
public interface DriverListener {
    void onDriverLosd();
    
    /**
     * Whenever a new Driver is added the Driver Manager will notify the listener class through this mehod.
     * 
     * @param addedDriver newly added Driver object
     */
    void onDriverAddition( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver addedDriver );   
    
    /**
     * This method will be called when drivers server name is renamed.
     * @param renamedDriver Driver Object that has been renamed
     */
    void onDriverServerNameRename( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver renamedDriver );
    
    /**
     * This method will be called when driver servers URL is changed.
     * @param renamedDriver Driver whose Servers URL has changed
     */
    void onDriverServerURLRename( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver renamedDriver );
    
    /**
     * This method will be called when driver is deleted.
     * 
     * @param DeletedDriver name of driver that has been deleted from database.
     */
    void onDriverDelete( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver DeletedDriver );
    
    /**
     * This method is called when the default driver for system is changed.
     * 
     * @param NewDefaultDriver new driver that is made default
     * @param OldDefaultDriver previous driver that has been chnaged
     */
    void onDefaultDriverChanged( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver NewDefaultDriver, org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver OldDefaultDriver );

}
