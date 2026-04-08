
//                         !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System;

/***
 *  This interface holds the system information. it will be accessed by the backends to perform any operation. it must not be visizle to others.
 * 
 * @author Sumit Shrestha
 */
public interface SystemInformationDatabase {
    
    String SystemLibraryName = "lib";
    
    String DriverLibraryName = "Driver";
    
    String DriverInformationFile = "DriverInfo.xml";
    
}
