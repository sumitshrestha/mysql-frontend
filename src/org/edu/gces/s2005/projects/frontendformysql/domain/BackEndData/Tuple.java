
//                               !! RAM !!

/**
 * Tuple.java
 *
 * Created on August 31, 2007, 2:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData;

/***
 *
 * @author sumit
 * This class is made out of the need to have a component that will hold a tuple of the table.
 * it will have values for
 * 1. value
 * 2. type
 */
public class Tuple {
    
    /*** Creates a new instance of Tuple */
    public Tuple( String v, String Cnm ) {
        Val = v;
        CollNm = Cnm;        
    }
    
    public String getValue()
    {
        return Val;
    }
    
    public String getCollNm()
    {
        return CollNm;
    }
        
    private String Val;
    private String CollNm;
    
}
