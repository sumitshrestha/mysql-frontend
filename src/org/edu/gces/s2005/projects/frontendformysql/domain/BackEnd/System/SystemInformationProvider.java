
//                                 !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System;

/***
 *
 * This class will provide the backend class with necessary information on system status like path, etc. 
 * It will use the information in SystemInformation Database to give it.
 * 
 * @author Sumit Shrestha
 */
public class SystemInformationProvider {
    
    public static String getDriverFilePath(){
        return getDriverLibraryPath() + java.io.File.separatorChar + org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInformationDatabase.DriverInformationFile;
    }
    
    public static String getDriverLibraryPath(){
        return org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInformationDatabase.SystemLibraryName + java.io.File.separatorChar + org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInformationDatabase.DriverLibraryName;
    }
        
    /**
     * This method will give the absolute path of default folder where the system was called.
     * 
     * @return absolute path of default folder if exists else null.
     */
    public static String getAbsolutePathOfDefaultFolder(){
        try{        
            java.io.File temp = new java.io.File( "" );
            return temp.getAbsolutePath();
        }
        catch( Exception e ){
            return null;
        }
    }
    
}
