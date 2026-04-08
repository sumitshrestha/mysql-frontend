
//                          !! RAM !!

/**
 * ForeignKeyInterface.java
 *
 * Created on October 15, 2007, 10:13 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 *
 * @author sumit
 * NOTE: HERE THIS INTERFACE WAS A SUBCLSS OF ATTRIBUTE INTERFACE BUT SINCE IT IS NOT USED IN PRACTICE, I HAVE REMOVED IT.
 */
public interface ForeignKeyInterface  {
    
    public String getAttName();
    
    public String getPK_Name();
    
    public String getPk_Database();
    
    public String getPk_Table();
    
    public String getUpdate_Rule();
    
    public String getDelete_Rule();
    
    public String getDeferability_Rule();
}
