
//                            !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql;

/***
 *
 * @author Sumit Shrestha
 */
public class Factory {
    private Factory(){
        
    }
    
    public static org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.BackEnd createEngine(){
        return new org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.BackEnd();
    }
    
    public static void initializeSystemStatus(){
        new org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInitializer();
    }
}
