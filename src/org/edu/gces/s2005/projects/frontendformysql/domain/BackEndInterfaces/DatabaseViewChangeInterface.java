
//                              !! RAM !!


package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/**
 *  This interface defines the function for interacting about the changes in the view of database like adding, deleting, etc.
 * 
 * @author Sumit Shresth
 */
public interface DatabaseViewChangeInterface {
    
    /**
     * This function will give the name of the view that has been affected. 
     * It may be new view or old view that has been deleted.
     * 
     * @return name of the view
     */
    String getViewName();
    
    /**
     * This function will give the database name of the changed view.
     * It has been done to inform the domain system where to perform changed.
     * 
     * @return name of the database where view change occured
     */
    String getDatabaseName();
    
    /**
     * This function will be used in the view creation. 
     * It defines the body of the view. It takes data from multiple tables as defined by user.
     * 
     * @return script filled by user that defines the body of view.
     */
    String getScript();
}
