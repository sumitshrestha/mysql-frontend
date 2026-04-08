
//                            !! RAM !!

/**
 * DatabaseTableChangeInterface.java
 *
 * Created on October 5, 2007, 5:32 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 *
 * @author sumit
 */
public interface DatabaseTableChangeInterface {
      
        // returns the table that has affected database like created or deleted
      public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table returnDatabaseAffectingTable();
      
      // return name of database where table change like creation, deletion occured
      public String returnChangedDatabase();
      
}

