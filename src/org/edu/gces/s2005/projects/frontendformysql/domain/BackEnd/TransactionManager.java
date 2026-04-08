
//                      !! RAM !!

/**
 * TransactionManager.java
 *
 * Created on November 1, 2007, 9:30 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd;


/***
 *
 * @author sumit shrestha
 */
public class TransactionManager {
    
    /*** Creates a new instance of TransactionManager */
    public TransactionManager() {
    }
    
    /**
     * method for initializing the transaction after every connection.
     */
    public void start( java.sql.Connection Conn ){
        this.Conn = Conn;
        this.removeAutoCommit();                
    }
    
    public boolean rollback(){        
        try{           
        this.Conn.rollback();
        
        this.emptyTransactionList();
        
        this.notifyTransactionRollback( this.TransactionList.toArray( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction[ this.TransactionList.size() ] ), null );
        
        return true;
        }
        catch( java.sql.SQLException E ){
            return false;
        }
    }
    
    public boolean rollback( String SavePoint ){
        try{
            org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction Transac = this.getTransaction( SavePoint );
            
            if( Transac == null )
                return false;
            
            //else
            java.sql.Savepoint Sp = Transac.getSavePoint();
            
            this.Conn.rollback( Sp );
            
            // code to remove the transaction till Transac
            java.util.LinkedList< org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction > RollbackedTransactionList = new java.util.LinkedList < org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction > ();    
            try{                
                while( this.TransactionList.peekLast() != Transac ){
                    RollbackedTransactionList.addFirst( this.TransactionList.removeLast() );
                }
            }
            catch( java.util.NoSuchElementException e ){
                
            }
            
            this.notifyTransactionRollback( RollbackedTransactionList.toArray( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction[ RollbackedTransactionList.size() ] ), Sp );
            
            return true;
        }
        catch( java.sql.SQLException e ){
            return false;
        }
    }
    
    public boolean commit(){
        try{
        this.Conn.commit();
        
        this.emptyTransactionList();
        
        this.notifyTransactionCommit( this.TransactionList.toArray( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction[ this.TransactionList.size() ] ), null );
        
        return true;
        }
        catch( java.sql.SQLException E ){
            return false;
        }       
    }
    
    public boolean setAutoCommit(){
        try{
        this.Conn.setAutoCommit( true );
        return true;
        }
        catch( java.sql.SQLException E ){
            return false;
        }
    }
    
    public boolean removeAutoCommit(){
        try{
        this.Conn.setAutoCommit( false );        
        return true;
        }
        catch( java.sql.SQLException E ){
            return false;
        }
    }
    
    public final String addTransaction( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction Transac ){
        boolean s = false;
        
        try{
            s = this.Conn.getAutoCommit();
        }
        catch( java.sql.SQLException e ){
            return e.getMessage();
        }
        
        if( Transac != null && ! s ){
            if( this.TransactionList.add( Transac ) ){
                this.notifyTransactionAddition( Transac );
                return this.TRANSACTION_CREATED;
            }
            else
                return "Error Cannot add";
        }
        else
            return "Error!! Transaction to be added was null!!";
    }
    
    public final String createSavePoint( String Name ){
        try{          
            if( this.SavePointExists( Name ) )
                return "Error!! Save Point with given name Already Exists. Please Choose Other Name";
            
            if( this.TransactionList.isEmpty() )
                return " Error!! Transaction list empty";
            
            // else
        java.sql.Savepoint Spt = this.Conn.setSavepoint( Name );
        this.TransactionList.getLast().setSavePoint( Spt );
        
        this.notifySavePointAddition( Spt );
        
        return this.SAVEPOINT_CREATED;
        }
        catch( java.sql.SQLException e ){
            return e.getMessage();
        }
        catch( java.lang.NoClassDefFoundError e ){
            return e.getMessage();
        }
    }
    
    private boolean SavePointExists( String SavePoint ){
        java.util.Iterator< org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction > Iterate = this.TransactionList.descendingIterator();
        while( Iterate.hasNext() ){
            org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction Transac = Iterate.next();
            java.sql.Savepoint Sp = Transac.getSavePoint();
            if( Sp == null )
                continue;
            
            // else
            try{
            if( Sp.getSavepointName().equals( SavePoint ) )
                return true;
            else
                continue;
            }
            catch( java.sql.SQLException e ){
                // do nothing
            }
        }
        
        return false;
    }
    
    public boolean releaseSavePoint( String SpName ){
        
                org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction Transac = this.getTransaction( SpName );
        
                if( Transac == null ) // there was no transaction having the given Savepoint name
                    return false;
                
                java.sql.Savepoint Sp = Transac.getSavePoint();
                                
                try{
                this.Conn.releaseSavepoint( Sp );
                }
                catch( java.sql.SQLException e ){
                    // report
                    System.out.println( e );
                    return false;
                }
                
                Transac.setSavePoint( null );
                
                // notify of changes
                this.notifySavePointRelease( Sp );
                
                return true;
    }
    
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction getTransaction( String SpName ){
        java.util.Iterator< org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction > Iterate = this.TransactionList.descendingIterator();
        while( Iterate.hasNext() ){
            org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction Transac = Iterate.next();
            java.sql.Savepoint Sp = Transac.getSavePoint();
            
            try{
            if( Sp == null || ! Sp.getSavepointName().equals( SpName ) ){
                continue;
            }
            else{                
                return Transac;
            }            
            }
            catch( java.sql.SQLException e ){
                // do nothing
            }
                        
        }
        return null;
    }
    
    private final void emptyTransactionList(){
        while( ! this.TransactionList.isEmpty() ){
            this.TransactionList.remove();
        }
    }
       
    /**
     * returns an array of recoverable transaction that can be either rollbacked or committed.
     */          
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction[] getTransactions(){
        return this.TransactionList.toArray( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction[ this.TransactionList.size() ] );
    }
    
    /**
     * method for adding listener
     */
    public void addTransactionListener( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TransactionListener Listener ){
        if( Listener == null )
            return;
        
        //else
        this.Listener.add( Listener );
        
    }
    
    private void notifyTransactionAddition( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction Transac ){
        java.util.Iterator<org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TransactionListener> Iterate = this.Listener.descendingIterator();
        while( Iterate.hasNext() ){
            Iterate.next().onTransactionAdded( Transac );
        }
    }
    
    private void notifyTransactionRollback(  org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction[] RollbackedTransac, java.sql.Savepoint Sp ){
        java.util.Iterator<org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TransactionListener> Iterate = this.Listener.descendingIterator();
        while( Iterate.hasNext() ){
            Iterate.next().onTranactionRollbacked( RollbackedTransac, Sp );
        }
    }
    
    private void notifyTransactionCommit( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction[] CommittedTransac, java.sql.Savepoint Sp ){
        java.util.Iterator<org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TransactionListener> Iterate = this.Listener.descendingIterator();
        while( Iterate.hasNext() ){
            Iterate.next().onTransactionCommited( CommittedTransac, Sp );
        }
    }
    
    private void notifySavePointAddition( java.sql.Savepoint AddedSavePoint ){
        java.util.Iterator<org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TransactionListener> Iterate = this.Listener.descendingIterator();
        while( Iterate.hasNext() ){
            Iterate.next().onSavePointAdded( AddedSavePoint );
        }
    }
    
    private void notifySavePointRelease( java.sql.Savepoint ReleasedSavePoint ){
        java.util.Iterator<org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TransactionListener> Iterate = this.Listener.descendingIterator();
        while( Iterate.hasNext() ){
            Iterate.next().onSavePointRealeased( ReleasedSavePoint );
        }
    }
    
    
    public boolean isAutoCommit(){
        try{
        return this.Conn.getAutoCommit();
        }
        catch( java.sql.SQLException e ){
            return false;
        }
    }
    
    public boolean supportsSavePoints(){
       try{
        return this.Conn.getMetaData().supportsSavepoints();
        }
        catch( java.sql.SQLException e ){
            return false;
        }
    }
    
    public boolean supportsDML_DDL(){
       try{
        return this.Conn.getMetaData().supportsDataDefinitionAndDataManipulationTransactions();
        }
        catch( java.sql.SQLException e ){
            return false;
        }
    }

    public boolean supportsDDLOnly(){
       try{
        return this.Conn.getMetaData().supportsDataManipulationTransactionsOnly();
        }
        catch( java.sql.SQLException e ){
            return false;
        }
    }
    
    public String getCurrentIsolationType(){
        try{
        return this.convertIsolationType( this.Conn.getTransactionIsolation() );
        }
        catch( java.sql.SQLException e ){
            return "";
        }
    }
    
    public boolean setCurrentIsolationType( String t ){
        try{
            this.Conn.setTransactionIsolation( this.convertIsolationType( t ) );
            return true;
        }
        catch( java.sql.SQLException e ){
            return false;
        }
    }
    
    private String convertIsolationType( int Isolation ){
        
        switch( Isolation ){
            case java.sql.Connection.TRANSACTION_NONE:
                return TRANSACTION_NONE;
            case java.sql.Connection.TRANSACTION_READ_COMMITTED:
                return TRANSACTION_READ_COMMITTED;
            case java.sql.Connection.TRANSACTION_READ_UNCOMMITTED:
                return TRANSACTION_READ_UNCOMMITTED;
            case java.sql.Connection.TRANSACTION_REPEATABLE_READ:
                return TRANSACTION_REPEATABLE_READ;
            case java.sql.Connection.TRANSACTION_SERIALIZABLE:
                return TRANSACTION_SERIALIZABLE;
        }
        
        return "NONE";
    }
    
    private int convertIsolationType( String Isolation ){
        if( Isolation == this.TRANSACTION_NONE )
            return java.sql.Connection.TRANSACTION_NONE;
            else
                if( Isolation == this.TRANSACTION_READ_COMMITTED )
                    return java.sql.Connection.TRANSACTION_READ_COMMITTED;
                else
                    if( Isolation == this.TRANSACTION_READ_UNCOMMITTED)
                        return java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
                    else
                        if( Isolation == this.TRANSACTION_REPEATABLE_READ )
                            return java.sql.Connection.TRANSACTION_REPEATABLE_READ;
                        else
                            if( Isolation == this.TRANSACTION_SERIALIZABLE )
                                return java.sql.Connection.TRANSACTION_SERIALIZABLE;
            
        return -1;
    }
    private java.sql.Connection Conn = null;
    
    private java.util.LinkedList< org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction > TransactionList = new java.util.LinkedList < org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction > ();    
    
    // field for storing the transaction listeners
    private java.util.LinkedList< org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TransactionListener > Listener = new java.util.LinkedList< org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TransactionListener >();
    
    
    // some constants
    public static final String SAVEPOINT_CREATED = "save point created";
    public static final String TRANSACTION_CREATED = "Transaction Created";
    
    private final static String TRANSACTION_NONE = "TRANSACTION_NONE";
    private final static String TRANSACTION_READ_COMMITTED  = "TRANSACTION_READ_COMMITTED";
    private final static String TRANSACTION_READ_UNCOMMITTED = "TRANSACTION_READ_UNCOMMITTED";
    private final static String TRANSACTION_REPEATABLE_READ = "TRANSACTION_REPEATABLE_READ";
    private final static String TRANSACTION_SERIALIZABLE = "TRANSACTION_SERIALIZABLE";
            
}
