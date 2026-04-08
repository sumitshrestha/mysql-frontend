
//                         !! RAM !!

/**
 * TriggerChangeInterface.java
 *
 * Created on October 16, 2007, 5:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 *
 * @author sumit
 */
public interface TriggerChangeInterface {
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableTriggerInterface getChangedTrigger();
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableTriggerInterface getNewTrigger();
    
    public String getChangedTableName();
    
    public String getTriggerDatabase();
    
}
