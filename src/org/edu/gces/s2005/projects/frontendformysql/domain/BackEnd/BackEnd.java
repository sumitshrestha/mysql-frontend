
//                                      !! RAM !!

/**
 * BackEnd.java
 *
 * Created on July 28, 2007, 2:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd;

/***
 *
 * @author Sumit Shrestha
 */
public class BackEnd {
    
    /*** Creates a new instance of BackEnd */
    public BackEnd() {
        
        // check if system initialization is done or not.
        if( ! org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInitializer.isInitialized() )
            org.edu.gces.s2005.projects.frontendformysql.Factory.initializeSystemStatus();
        
        this.DriverMan = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.DriverManager( this.IOManager );
    }
    
    /**
     * This method is used to connect to the database. 
     * 
     * @param User name of user to connected
     * @param Password password required to connect. if no password is required then it can be empty ""
     * 
     * @return message of successfull connection or not
     */
    public final String connect( String User, char[] Password )
    {   
         java.sql.DatabaseMetaData DatabaseInfo = this.DriverMan.makeConnection( User, Password );         
         
         if( DatabaseInfo != null ){ // connected
             
             try{             
                 this.DatabaseInfo = DatabaseInfo;
             
                // start transaction
                this.TransacMan.start( DatabaseInfo.getConnection() );
             
                // create proc executer
                this.ProcExec = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.ProcedureExecuter( this.DatabaseInfo.getConnection() );
             
                // notifying user manager of new connection
                this.UserMan.onConnection( DatabaseInfo.getConnection() );
             
                // notify exec of connection
                this.Exec.onConnection( DatabaseInfo.getConnection() );                          
             }
             catch( java.sql.SQLException e ){
                 // do nothing as conn obj cannot be obtained
                 return e.getMessage();
             }
             
             this.DbReader = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.DatabaseReader( this, this.QueryGenerate, this.Exec );
             this.ProcCompiler = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.ProcedureCompiler( Exec );
             
             return this.DriverMan.SuccessfullConnection;
         }
         else {
             return this.DriverMan.getErrorMessage();
         }
    }
    
    /**
     * added code for driver properties
     */
    public final String addDriver( String DriverName, String DriverAbsPath ){
        if( this.DriverMan.addDriver( DriverName, DriverAbsPath ) == null )
            return this.DriverMan.getErrorMessage();
        else
            return org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.DriverManagerInterface.SuccessfullDriverAddition;            
    }
    
    
    /**
     * This method will remove driver from system as defined by driver interface.
     * 
     * @param DriverID ID of driver to deleted.               
     * @return Message of successful deletion on success else error message
     */
    public final String removeDriver( String DriverID ){
        if( this.DriverMan.deleteDriver(DriverID) )
            return this.DriverMan.SuccessfullDeletion;
        else
            return this.DriverMan.getErrorMessage();
    }
    
    public final String renameDriverServerName( String DriverID, String DriverServerName ){
        if( this.DriverMan.renameDriverServerName(DriverID, DriverServerName) )
            return this.DriverMan.SuccessfullModification;
        else
            return this.DriverMan.getErrorMessage();
    }
    
    public final String renameDriverServerURL( String DriverID, String URL ){
        if( this.DriverMan.renameDriverServerURL(DriverID, URL))
            return this.DriverMan.SuccessfullModification;
        else
            return this.DriverMan.getErrorMessage();
    }
    
    public final boolean isAnyDriver(){
        return this.DriverMan.getRecentlyAddedDriverID() == null;
    }
    
    public final String getRecentlyAddedDriver(){
        return this.DriverMan.getRecentlyAddedDriverID();
    }
    
    public final boolean registerDriverListener( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DriverListener DrvList ){
        return this.DriverMan.registerDriverListener( DrvList );
    }
    
    public final String setDefaultDriver( String DriverID ){        
        if( this.DriverMan.setDefaultDriver( DriverID ) )
            return this.DriverMan.DefaultDriverSetSuccess;
        else
            return this.DriverMan.getErrorMessage();        
    }
    
    public final org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver getDefaultDriver(){
        return this.DriverMan.getDefaultDriver();
    }
    
    public final org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver[] getLoadedDrivers(){
        return this.DriverMan.getLoadedDriver();
    }
    
    public void close()
    {
        this.DbReader = null;
        this.DriverMan.closeConnection();
    }// func ends
    
    public String execute( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DatabaseTableChangeInterface createdTable  )
    {
         String qur = QueryGenerate.generateQuery( createdTable );         
         //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( qur) );
         return Exec.executeQuery( qur );          
    }// func ends
    
    public String selectDatabase( String DBName )
    {        
        String q = QueryGenerate.generateDatabaseUsageQuery( DBName );        
        String Mess = Exec.executeQuery( q );
        if( Mess == org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.QueryExecuter.EXECUTE_SUCCESS )
            PresentlySelected.setName( DBName );
        return Mess;
    }// func ends
        
    public String createDatabase( String DbName )
    {
        String Query = QueryGenerate.generateDatabaseCreateQuery( DbName );
        //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return Exec.executeQuery( Query );
    }// func ends
    
    public String deleteDatabase( String DbName )
    {
        String Query = QueryGenerate.generateDatabaseDeleteQuery( DbName );
        //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return Exec.executeQuery( Query );
    }// func ends
    
    public String createView(  org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DatabaseViewChangeInterface form )
    {
        String Query = QueryGenerate.generateViewCreateQuery( form );
        //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return Exec.executeQuery( Query );
    }// func ends
    
    public String returnSelectedDatabase()
    {
       return PresentlySelected.getName();
    }
    
    public String insertTableData( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableValueChange T )
    {
        String Query = QueryGenerate.generateTableInsertQuery( T );                
        final String Mess = Exec.executeQuery( Query );
        if( Mess == this.Exec.EXECUTE_SUCCESS )
            this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return Mess;
    } // func ends
    
    public String deleteTableData( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableValueChange T )
    {        
        String Query = QueryGenerate.generateTableValueDeleteQuery( T );                 
        final String Mess = Exec.executeQuery( Query );
        if( Mess == this.Exec.EXECUTE_SUCCESS )
            this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return Mess;
    }// func ends
    
     public String updateTableData( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableValueChange T )
    {
         String Query = QueryGenerate.generateTableValUpdateQuery( T );
         final String Mess = Exec.executeQuery( Query );
        if( Mess == this.Exec.EXECUTE_SUCCESS )
            this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return Mess;
    }// func ends
     
    public String insertTableAtt( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeChangedInterface AttaddEvent  ) 
    {
        String Query = QueryGenerate.generateTableAttAddQuery( AttaddEvent );
        //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        System.out.println( Query );
        return Exec.executeQuery( Query );        
    }// func ends
    
    public String deleteTableAtt( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeChangedInterface AttDelEvent )
    {
        String Query = QueryGenerate.generateTableAttDeleteQuery( AttDelEvent );
        //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return Exec.executeQuery( Query );
    }// func ends
    
    public String deleteTable( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DatabaseTableChangeInterface TbDelEvnt )
    {
        String Query = QueryGenerate.generateTableDeleteQuery( TbDelEvnt );
        //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return Exec.executeQuery( Query );
    }
    
    public String deleteView( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DatabaseViewChangeInterface deletedView  )
    {
        String Query = QueryGenerate.generateViewDeleteQuery( deletedView  );
        //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return Exec.executeQuery( Query );
    }// func ends
    
    public String addForeignKey( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ForeignKeyChangeInterface ForKeyAddEvt ){
        String Query = this.QueryGenerate.generateForeignKeyAddQuery( ForKeyAddEvt );
        //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return this.Exec.executeQuery( Query );
    }
    
    public String dropForeignKey( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ForeignKeyChangeInterface ForKeyDropEvt ){
        String Query = this.QueryGenerate.generateForeignKeyDeleteQuery( ForKeyDropEvt );
        if( Query == null )
            return "NO FOREIGN KEY NAME SPECIFIED";
        // else
        //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return this.Exec.executeQuery( Query );
    }// func ends
    
    public String addTrigger( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TriggerChangeInterface  TriggerChangeEvt ){
        String Query = this.QueryGenerate.gnerateTriggerAdditionQuery( TriggerChangeEvt );        
        //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return this.Exec.executeQuery( Query );
    }
    
    public String dropTrigger( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TriggerChangeInterface TriggDropEvt ){
        String Query = this.QueryGenerate.generateTriggerDeleteQuery( TriggDropEvt );        
        //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( Query ));
        return this.Exec.executeQuery( Query );
    }
    
    public java.sql.DatabaseMetaData returnDatabaseInfo()
    {
        return this.DatabaseInfo;
    }
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.DatabaseReader getDatabaseReader(){
        return this.DbReader;
    }    
    
    /**
     * For Proc
     */
    public String compileProcedure( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ProcedureInterface Proc ){
        String p = this.ProcGen.generateProcedure( Proc );
        //this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( p ));
        return this.compileProcedure( p );
    }
    
    /**
     * this func is for sql editor that will directly write sql proc query to be compiled directly without needing the domain proc object as usual.
     * @param Proc the procedure query to be compiled.
     */
    public String compileProcedure( String Proc ){
        if( Proc != null ){        
          //  this.TransacMan.addTransaction( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction( "Procedure "+ Proc +" compilation" ));
            return this.ProcCompiler.compile( Proc );
        }        
        return "compilation failed while adding name";
    }
    
    public String ExecuteProcedure( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ProcedureInterface Proc ){
        return this.ProcExec.ExecuteProcedure( Proc );        
    }
     
    /**
     * here is the code for getting the result set object from domain.
     */
    public final java.sql.ResultSet getResultSet(){
        return this.ProcExec.getResult();
    }
    
    public final java.sql.ResultSet getQueryResult(){
        return this.Exec.returnResult();
    }
    
    /**
     * Method for delegating the IO Manager to the upper UI layer
     */
    public final org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOManager getIOManager(){
        return this.IOManager;
    }
    
    // this code is for directly executing the query from terminal
    public final String execute( String Query ){
        return this.Exec.executeQuery( Query );
    }
    
    // method to get transac man
    public final org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.TransactionManager getTransactionManager(){
        return this.TransacMan;
    }
    
    // method for getting user manager
    public final org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.UserManager getUserManager(){
        return this.UserMan;
    }
    
    private QueryExecuter Exec = new QueryExecuter();
    private QueryGenerator QueryGenerate = new QueryGenerator();
    
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Database PresentlySelected = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Database( null ); // initially none is selected
    
    /**
     * this is the object for reading the database server( more on its own file).
     *  this is initialized every time a connection is made n nullified after diacoonection.
     */
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.DatabaseReader DbReader = null;
    
    /**
     *   HERE IS THE FIELD THAT WILL HOLD THE NECESSARY INFO OF DATABASE AND ITS TABLES 
     *   
     *   NOTE:: THIS FIELD COULD BE CHANGED IN FUTURE. IT IS JUST A TEST 
     */
    private java.sql.DatabaseMetaData DatabaseInfo = null;
    
    /**
     * atts for procedure
     */
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.ProcedureGenerator ProcGen = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.ProcedureGenerator();
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.ProcedureCompiler ProcCompiler = null;
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.ProcedureExecuter ProcExec = null;
    
    // holds IOManager as it is active backend
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOManager IOManager = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOManager();
    
    // transaction manager
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.TransactionManager TransacMan = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.TransactionManager();
    
    // User Manager
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.UserManager UserMan = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.UserManager();
    
    // connection manager for managing connection to db server
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.JDBC2_ConnectionManager ConnMan = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.JDBC2_ConnectionManager();
    
    // driver manager for managing all driver related tasks
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.DriverManagerInterface DriverMan = null;
        
}
