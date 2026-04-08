
//                                  !! RAM !!

/**
 * TableTrigger.java
 *
 * Created on October 16, 2007, 5:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData;

import org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableTriggerInterface;

/***
 *
 * @author sumit
 */
public class TableTrigger implements TableTriggerInterface {
    
    /*** Creates a new instance of TableTrigger */
    public TableTrigger( String N ) {
        this.Name = N;
    }
    
    public void setName( String N ){
        this.Name = N;
    }
    
    public void setEvent( String evt ){
        this.Event = evt;
    }
    
    public void setCondition( String cond){
        this.Condition = cond;        
    }
    
    public void setTiming( String Time ){
        this.Timing = Time;
    }
    
    public void setOperation( String Opr){
        this.Operation = Opr;
    }    
    
    public void setOldReference( String old ){
        this.OldRef = old;
    }
    
    public void setNewReference( String n ){
        this.NewRef = n;
    }
    
    
    /**
     * Non Mutators
     */
    
    public String getName(){
        return this.Name;
    }
    
    public String getCondition(){
        return this.Condition;
    }
    
    public String getEvent(){
        return this.Event;
    }
    
    public String getTiming(){
        return this.Timing;
    }
    
    public String getOperation(){
        return this.Operation;
    }
    
    
    public String getOldReference(){
        return this.OldRef;
    }
    
    public String getNewReference(){
        return this.NewRef;
    }
    
    
    private String Name = null;
    private String Event = null;
    private String Timing = null;
    private String Condition = null;
    private String Operation = null;
    private String OldRef = null;
    private String NewRef = null;
    
    /** 
     * timing constants
     */
    public static final String BEFORE = "BEFORE";
    public static final String AFTER = "AFTER";
    
    /**
     * events constants
     */
    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE  = "DELETE";
}
