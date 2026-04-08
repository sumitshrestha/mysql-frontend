
//                             !! RAM !!

/**
 * TableAttributeInterface.java
 *
 * Created on September 26, 2007, 12:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 *
 * this interface is specifically for creating an independent interface to the attributes of table with other componets.
 * @author sumit
 */
public interface TableAttributeInterface {
    
    public String getAttName();
    
    public String getAttType();
    
    public int getAttSize();
    
    public String getAttKey();
    
    public boolean isNullable();
    
    public String getAttDefaultVal();     
}
