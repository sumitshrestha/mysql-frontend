
//                            !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System;

/***
 * This is temp interface to be a substitute to InitialDriverInfo.xml file.
 * 
 * @author Sumit Shrestha
 */
public interface InitialDriverInfo {
    
    String Info = "<?xml version=\"1.0\" encoding=\"windows-1252\"?> \n<!DOCTYPE DriverInfo [\n<!ELEMENT DriverInfo (Driver)*>\n<!ELEMENT Driver EMPTY >    \n    <!ATTLIST DriverInfo DefaultDriver IDREF #REQUIRED >\n    <!ATTLIST Driver\n                    DriverID ID #REQUIRED\n                    DriverName CDATA #REQUIRED\n                    DriverJarFileName CDATA #REQUIRED\n                    DatabaseServerName CDATA #IMPLIED\n                    DatabaseServerURL CDATA #IMPLIED >\n]>\n\n<DriverInfo DefaultDriver=\"_-1\">\n    <Driver DriverName=\"null\" DriverID=\"_-1\" DriverJarFileName=\"null\" />\n</DriverInfo>";
    
}
