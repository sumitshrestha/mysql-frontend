/**
 * ProcedureInterface.java
 *
 * Created on October 17, 2007, 10:35 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 *
 * @author sumit
 */
public interface ProcedureInterface {
    String getComment();

    String getName();

    String getOperation();

    TableAttributeInterface[] getParameters();
    
    String getDatabase();
}
