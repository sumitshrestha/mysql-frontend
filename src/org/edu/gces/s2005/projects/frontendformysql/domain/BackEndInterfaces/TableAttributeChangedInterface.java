/**
 * TableAttributeChangedInterface.java
 *
 * Created on September 30, 2007, 8:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
 
package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 *
 * @author sumit
 */
public interface TableAttributeChangedInterface {
    /**
     * this will give the new attributes with new values.
     * Mainly used in update, insert.
     * not in delete
     */
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeInterface[] getNewAttributesValues();
    
    /**
     * this will give the table that have changed.
     * Used in insert, delete, update
     */
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table getChangedTable();
    
    /**
     * this will give the existing key that have been changed.
     * used in update, delete.
     * not in insert.
     */
     public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeInterface[] getChangedAttributes();
    
}
