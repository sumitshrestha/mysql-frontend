
//                            !! RAM !!

/**
 * ForeignKeyChangeInterface.java
 *
 * Created on October 16, 2007, 10:15 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 *
 * @author sumit
 */
public interface ForeignKeyChangeInterface {
     /**
     * this will give the new Foreign keys.
     * Mainly used in update, insert.
     * not in delete
     */
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ForeignKeyInterface getNewForeignKey();
    
    /**
     * this will give the table that have changed.
     * Used in insert, delete, update
     */
    public String getChangedTable();
    
    /**
     * this will give the existing foreing key that have been changed.
     * used in update, delete.
     * not in insert.
     */
     public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ForeignKeyInterface getChangedForeignKey();
    
}
