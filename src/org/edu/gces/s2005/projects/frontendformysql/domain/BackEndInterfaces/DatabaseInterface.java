
//                      !! RAM !!

/**
 * DatabaseInterface.java
 *
 * Created on October 13, 2007, 9:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 *
 * @author sumit
 */
public interface DatabaseInterface {
    
    public String getName();
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table[] getTables();
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.View[] getViews();
    
}
