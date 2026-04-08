
//                           !! RAM !!

/**
 * StatusBarDataStructure.java
 *
 * Created on October 31, 2007, 7:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DataStructures;

/***
 *
 * @author sumit
 */
public class StatusBarDataStructure {
    
    /*** Creates a new instance of StatusBarDataStructure */
    public StatusBarDataStructure( int i ) {
        this.SIZE = i;
    }
    
    public void setStatus( String state ){
        if( this.isFull() )
            this.StatusList.remove();
        
        this.StatusList.add( state );
        
        present = this.StatusList.size();
    }
    
    private final boolean isFull(){
        return this.StatusList.size() == this.SIZE;
    }   
    
    public final boolean isPreviousStatusReset(){
        try{
        String s = this.StatusList.getLast().toString();
        return s.equals( "" );
        }
        catch( java.util.NoSuchElementException e ){
            return false;
        }
    }
    
    public void resetStatus(){
        if( this.isPreviousStatusReset() )
            return;
        
        this.setStatus( "" );
    }
    
    public String addStatus( String state ){
        String s = null;
        
        if( this.StatusList.size() != 0 )
            s = this.StatusList.getLast();
        else
            s = "";
        
        String add = s + state;
        
        this.setStatus( add );
        
        return add;
    }
    
    public String getPreviousStatus(){        
        try{
        java.util.ListIterator Iterate = this.StatusList.listIterator( present - 1 );
        if( Iterate.hasPrevious() ){
            --present;
            return Iterate.previous().toString();                        
        }
        else
            return this.NO_PREVIOUS;
        }
        catch( java.lang.IndexOutOfBoundsException e ){            
            return this.NO_PREVIOUS;
        }                
    }
    
    public String getNextStatus(){        
        try{
        java.util.ListIterator Iterate = this.StatusList.listIterator( present );
        if( Iterate.hasNext() ){
            ++present;
            return Iterate.next().toString();            
        }
        else
            return this.NO_NEXT;
        }
        catch( java.lang.IndexOutOfBoundsException e ){            
            return this.NO_NEXT;
        }
    }
    
    public int getPresentPointer(){
        return this.present;
    }
    
    public int getContent(){
        return this.StatusList.size();
    }
    
    public boolean reachedMostPrevious(){
        return ( present < 2 );
    }
    
    public boolean reachedFirstMostRecent(){
        return StatusList.size() == present;
    }
    
    public String getRecentStatus(){
        try{
        return this.StatusList.getLast();
        }
        catch( Exception e ){
            return this.NO_NEXT;
        }
    }
    
    private java.util.LinkedList<String> StatusList = new java.util.LinkedList<String>();
    private int SIZE;
    private int present = 0;    
    public static final String NO_PREVIOUS = "NO Previous status available";
    public static final String NO_NEXT = "No Next";
}
