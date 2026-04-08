
//                          !! RAM !!

/**
 * TableValueChange.java
 *
 * Created on August 28, 2007, 6:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 *
 * @author sumit
 * This is the special interface to the query generator for any table changes that occur to the table panel by the user.
 *
 * NOTE:: This interface assumes that any change will be on single tuple at a time for now.
 * so to support the assumption the following function will be use full.
 */
public interface TableValueChange extends org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableInterface {
    
      public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Tuple [] getChangedTuple();
    
      public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Tuple [] getInsertedValues();
    
      public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Tuple [] getUpdatedValues();
      
}// interface ends
