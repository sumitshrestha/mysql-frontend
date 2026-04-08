
//                               !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System;

/***
 *
 * @author Sumit Shrestha
 */
public class SystemInitializer {
    public SystemInitializer(){
        init = this.createDriverLibraryInformation();
    }
    
    /**
     * This method will check if driver library exists or not.
     */
    private boolean doesDriverLibraryExists(){
        try{
            java.io.File temp = new java.io.File( SystemInformationProvider.getDriverLibraryPath() );
            return temp.isDirectory();
        }
        catch( Exception e ){
            return false;
        }        
    }
    
    /**
     * This method will check if driver information file exists or not.
     */
    private boolean doesDriverInformationExists(){
        try{
            java.io.File temp = new java.io.File( SystemInformationProvider.getDriverFilePath());
            return temp.isFile();
        }
        catch( Exception e ){
            return false;
        }
    }
        
    /**
     * This method will check if system library exists or not. 
     * NOTE:: this method is for system so it must not be here. it must be with backend. it is just for temp.
     * 
     * @return true if exists else false.
     */
    private boolean doesSystemLibraryExists(){
        try{
            java.io.File temp = new java.io.File( org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInformationDatabase.SystemLibraryName );
            return temp.isDirectory();
        }
        catch( Exception e ){
            return false;
        }
    }

    
    /**
     * This method will create library( it does not means folder but entire contents or driver ) for holding driver information and drivers by driver manager.
     * 
     * @return true if library is created else false
     */
    private boolean createDriverLibraryInformation(){
        try{
            if( ! this.doesSystemLibraryExists() ){
                if( this.createSystemLibrary() )
                    if( this.createDriverLibrary() )
                        this.createDriverFile();
            }
            else
                if( ! this.doesDriverLibraryExists() ){
                    if( this.createDriverLibrary() )
                        this.createDriverFile();
                }
                else
                    if( ! this.doesDriverInformationExists() ){
                        this.createDriverFile();
                    }            
            return true;            
        }
        catch( Exception e ){
            return false;
        }        
    }
    
    private boolean createSystemLibrary(){
        try{
            java.io.File temp = new java.io.File( org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInformationDatabase.SystemLibraryName );
            return temp.mkdir();            
        }
        catch( Exception e ){
            return false;
        }
    }
    
    private boolean createDriverFile(){
        try{
            java.io.File temp = new java.io.File( SystemInformationProvider.getDriverFilePath() );
            org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOManager man = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOManager();
            man.setDefaultExtension( "xml" );
            man.setBuffer( new java.lang.StringBuffer ( InitialDriverInfo.Info ) );
            if( temp.createNewFile() ){
                man.save( temp.getAbsolutePath() );
                return true;
            }
            else
                return false;
        }
        catch( Exception e ){
            return false;
        }
    }
    
    private boolean createDriverLibrary(){
        try{
            java.io.File temp = new java.io.File( SystemInformationProvider.getDriverLibraryPath() );
            return temp.mkdir();
        }
        catch( Exception e ){
            return false;
        }
    }

    public static boolean isInitialized(){
        return init;
    }
    
    // this privete field is for signalling that system initializing tasks is performed ot not. 
    private static boolean init = false;
}
