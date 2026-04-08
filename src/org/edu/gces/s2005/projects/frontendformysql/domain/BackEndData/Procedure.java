
//                      !! RAM !!

/**
 * Procedure.java
 *
 * Created on October 17, 2007, 10:28 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData;

import org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ProcedureInterface;

/***
 *
 * @author sumit
 */
public class Procedure implements ProcedureInterface {
    
    /*** Creates a new instance of Procedure */
    public Procedure( String Nm, String Db ) {
        this.Name = Nm;    
    }
    
    public void setComment( String Comm ){
        this.Comment = Comm;
    }
    
    public void setParameters( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeInterface[] param ){
        this.Param = param;
    }
    
    public void setOperation( String Opr ){
        this.Operation = Opr;
    }
    
    
    public String getName(){
        return this.Name;
    }
    
    public String getComment(){
        return this.Comment;
    }
    
    public String getOperation(){
        return this.Operation;
    }
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeInterface[] getParameters(){
        return this.Param;
    }
    
    public String getDatabase(){
        return Db;
    }
    
    
    private String Name = null;
    private String Comment = null;    
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeInterface[] Param = null;
    private String Operation = null;
    private String Db = null;
}
