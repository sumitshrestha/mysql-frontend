
//                        !! RAM !!

/**
 * Transaction.java
 *
 * Created on November 1, 2007, 1:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData;

/***
 *
 * @author sumit shrestha
 */
public class Transaction {
    
    /*** Creates a new instance of Transaction */
    public Transaction( String Query ) {        
        this.Query = Query;
        this.createName();
    }
    
    private void createName(){
        this.Name = this.Query;
    }
    
    public void setSavePoint( java.sql.Savepoint SavePoint ){
        this.SavePoint = SavePoint;
    }
    
    public String getName(){
        return this.Name;
    }
    
    public String getQuery(){
        return this.Query;
    }
    
    public java.sql.Savepoint getSavePoint(){
        return this.SavePoint;
    }    
    
    private String Name = "";
    private String Query = null;
    private java.sql.Savepoint SavePoint = null;
}
