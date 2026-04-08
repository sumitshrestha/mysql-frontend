
//                       !! RAM !!

/**
 * TableTriggerInterface.java
 *
 * Created on October 16, 2007, 5:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 *
 * @author sumit
 */
public interface TableTriggerInterface {
    String getCondition();

    String getEvent();

    String getName();

    String getOperation();

    String getTiming();
    
    String getOldReference();
    
    String getNewReference();    
}
