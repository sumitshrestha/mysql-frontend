
//                          !! RAM !!

/**
 * ProcedureCompiler.java
 *
 * Created on October 17, 2007, 12:38 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd;

/***
 *
 * @author sumit
 */
public class ProcedureCompiler {
    
    /*** Creates a new instance of ProcedureCompiler */
    public ProcedureCompiler( org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.QueryExecuter Ex ) {
        this.Exec = Ex;
    }
    
    public String compile( String Proc ){
        return Exec.executeQuery( Proc );
    }
    
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.QueryExecuter Exec = null;
}
