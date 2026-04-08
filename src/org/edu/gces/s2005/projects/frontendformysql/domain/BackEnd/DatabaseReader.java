
//                            !! RAM !!

/**
 * DatabaseReader.java
 *
 * Created on October 16, 2007, 9:24 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd;

import org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableTriggerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * This class is meant to read the database server to get the database domain object so that it could be used by any UI layer object.
 * it uses databaseMetadata Object as well as other class like querygenerator n queryexecuter to perform reading.
 * Note: this class will read the database only n not write.
 * @author sumit   
 */

public class DatabaseReader {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseReader.class);
    
    /*** Creates a new instance of DatabaseReader */
    public DatabaseReader( org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.BackEnd  eng, org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.QueryGenerator gen, org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.QueryExecuter ex ) {
        
        this.ParentEngine = eng;
        this.QueryGenerate = gen;
        this.Exec = ex;
        this.DatabaseInfo = this.ParentEngine.returnDatabaseInfo();
    }
    
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DatabaseInterface readDatabase( String DbName ){
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Database Db = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Database( DbName );
        String[] list = this.returnTableList( DbName );
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table[] Tbs = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table[ list.length ];
        
        for( int i=0 ;i <list.length; i++ ){
            Tbs[i] = this.readTable( list[i], DbName );
        }
        
        Db.setTables( Tbs );
        return Db;
    }
    
    /**
     * func for getting the list of database in the server.
     */             
    public String[] readDatabaseList(){
        java.util.LinkedHashSet<String> dbNames = new java.util.LinkedHashSet<String>();

        try{
            java.sql.ResultSet rs = this.DatabaseInfo.getCatalogs();
            while( rs.next() ){
                String name = rs.getString( 1 );
                if( name != null && !name.equals( "" ) )
                    dbNames.add( name );
            }
        }
        catch( Exception e ){
            LOG.warn( "Unable to enumerate databases from DatabaseMetaData.getCatalogs()", e );
        }

        try{
            java.sql.ResultSet rs = this.DatabaseInfo.getSchemas();
            while( rs.next() ){
                String name = rs.getString( 1 );
                if( name != null && !name.equals( "" ) )
                    dbNames.add( name );
            }
        }
        catch( Exception e ){
            LOG.debug( "Unable to enumerate schemas from DatabaseMetaData.getSchemas()", e );
        }

        try{
            if( this.Exec.executeQuery( "SHOW DATABASES" ) == org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.QueryExecuter.EXECUTE_SUCCESS ){
                java.sql.ResultSet rs = this.Exec.returnResult();
                while( rs != null && rs.next() ){
                    String name = rs.getString( 1 );
                    if( name != null && !name.equals( "" ) )
                        dbNames.add( name );
                }
            }
        }
        catch( Exception e ){
            LOG.debug( "Unable to enumerate databases with SHOW DATABASES", e );
        }

        if( dbNames.isEmpty() )
            return new String[]{};

        return dbNames.toArray( new String[]{} );
    }
    
    public String[] returnTableList( String DbName){
        try{
       java.sql.ResultSet rs = this.DatabaseInfo.getTables( DbName , null, null, null );
       String[][] array = org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.DatabaseReader.getArrayofResultSet( rs );
       java.util.ArrayList<String> a = new java.util.ArrayList<String>();
       for( int i=0; i< array.length; i++){                          
                    if( array[i][ rs.findColumn("TABLE_TYPE") - 1 ].equals( "TABLE" ))
                    {
                         a.add( array[i][ rs.findColumn("TABLE_NAME") -1 ] );
                    }                 
       }             
       return a.toArray( new String[]{} );
       }
       catch( Exception e)
       {
           System.out.println( e + " exception in getting table list...");
           return null;
       }
    }
    
    
    /**
     * reads the table object from server of tha dabases supplied as param
     * 
     * @param TableName the name of the table of which has to be raad.
     * @param DBName tha name of tha database containing table to be raad.
     * @return Table object that has been read
     */
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table readTable( String TableName, String DBName)
    {       
     
        String s = null;
        Exec.executeQuery( ( s = QueryGenerate.generateTableSelectQuery( TableName ) ) );
        
        java.sql.ResultSet Result = Exec.returnResult();
        
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table Table = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table( TableName );
        
        try{
        // adding attributes of the table
       java.sql.ResultSetMetaData rmd = Result.getMetaData();
       int coll= rmd.getColumnCount();
       ///////////////////////////////////////////////////////////////////
       
       java.sql.ResultSet PrimKeys = DatabaseInfo.getPrimaryKeys( DBName, DBName , TableName );
       
       java.sql.ResultSetMetaData p = PrimKeys.getMetaData();
       java.util.ArrayList prim = new java.util.ArrayList();       
       
        while( PrimKeys.next()){ 
	      prim.add( PrimKeys.getString( 4 ) );              
                               }
       //////////////////////////////////////////////////////////////////**/
         for( int i=1; i<= coll; i++)
         {
           org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute Att = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute();
           Att.setName( rmd.getColumnName( i ) ); // setting name      
           
           // set its type now
           Att.setType( rmd.getColumnTypeName( i ) );
           
           //set its null position
           Att.setNullable ( rmd.isNullable( i ) != 0 ) ;
           Att.setKey( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute.ORDINARY_KEY );
           for( int j=0;j<prim.size(); j++)
           {
               if( Att.getAttName().equals( prim.get(j)) )
               {
                   Att.setKey( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute.PRIMARY_KEY );
                   prim.remove( j );
                   j--;
                   break;
               }
           }
           
           Table.addAttribute( Att );
         }
       String[] ResultString = new String[ coll ];
       while( Result.next()){
  
	      for( int i=1; i<= coll; i++){	      
               ResultString[ i - 1 ] = Result.getString( i );
		                          }
              Table.insertRow( ResultString );
                            }
       this.setOtherValforTablefromDbInfo( Table , DBName );
       
        return Table;
        }
        catch( Exception e)
        {
            System.out.println( "exception in gettable" +e );            
            return null;
        }
    }// func ends
    
    
    private void setOtherValforTablefromDbInfo( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table Table , String DbName )
    {
        try{
            setForeignKeys(Table, DbName);
            this.setTriggers( Table );        
        }
        catch( Exception e )
        {
            System.out.println( e +" in setothervalfortable... ");
        }
    }// func ends

    
    private void setForeignKeys( final org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table Table, final String DbName) throws java.lang.NumberFormatException, java.sql.SQLException {
        java.sql.ResultSet rs;        
        //System.out.println( "we'r trying to get foreign keys of ::"+ DbName +"/"+ Table.getName() );
        
        final int PKTABLE_CAT =1;
        final int PKTABLE_NAME =3;
        final int KEY_SEQ =9;
        final int UPDATE_RULE=10;
        final int DELETE_RULE=11;
        final int FK_NAME=12;
        final int PK_NAME = 13;
        final int DEFERRABILITY = 14;
                
        rs = this.DatabaseInfo.getImportedKeys( DbName, null, Table.getName() );
        String[][] array = getArrayofResultSet( rs );
        int TotForKey = array.length;
        if( TotForKey == -1)
            return;
        // else
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Foreign_key[] ForKey = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Foreign_key[ TotForKey ];
        for( int i=0; i< TotForKey; i++){
            int real_seq = Integer.parseInt( array[i][ KEY_SEQ -1 ] ) ;
            ForKey[ real_seq -1 ] = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Foreign_key( array[i][ FK_NAME-1 ]);
            ForKey[ real_seq -1 ].setPk_Database( array[i][ PKTABLE_CAT -1 ]);
            ForKey[ real_seq -1 ].setPk_Table( array[i][ PKTABLE_NAME -1 ] );
            ForKey[ real_seq -1 ].setPK_Name( array[i][PK_NAME -1 ] );
            ForKey[ real_seq -1 ].setUpdate_Rule( this.covertUpdateDeleteRule_String( Integer.parseInt( array[i][UPDATE_RULE -1 ] )) );
            ForKey[ real_seq -1 ].setDelete_Rule( this.covertUpdateDeleteRule_String( Integer.parseInt( array[i][ DELETE_RULE -1 ] )));
            ForKey[ real_seq -1 ].setDeferability_Rule( this.convertDeferableRule_String( Integer.parseInt( array[i][ DEFERRABILITY -1 ] ))) ;            
        }
        Table.setForeignKeys( ForKey );    
    }// func ends

    
    
    private final String covertUpdateDeleteRule_String( int r ){        
        switch( r ){
            case java.sql.DatabaseMetaData.importedKeySetDefault:
                return org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Foreign_key.importedKeySetDefault;
            case java.sql.DatabaseMetaData.importedKeyNoAction:
                return org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Foreign_key.importedKeyNoAction;
            case java.sql.DatabaseMetaData.importedKeyCascade:
                return org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Foreign_key.importedKeyCascade;
            case java.sql.DatabaseMetaData.importedKeySetNull:
                    return org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Foreign_key.importedKeySetNull;
            case java.sql.DatabaseMetaData.importedKeyRestrict:
                    return org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Foreign_key.importedKeyRestrict;
            default:
                return null; 
        }
    }
    
    private final String convertDeferableRule_String( int r ){
        switch( r ){
         case java.sql.DatabaseMetaData.importedKeyInitiallyDeferred:
                    return org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Foreign_key.importedKeyInitiallyDeferred;
            case java.sql.DatabaseMetaData.importedKeyInitiallyImmediate:
                    return org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Foreign_key.importedKeyInitiallyImmediate ;
            case java.sql.DatabaseMetaData.importedKeyNotDeferrable:
                    return org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Foreign_key.importedKeyNotDeferrable;            
            default:
                return null;
    }
    }
    
    /**
     *  here are the codes for getting procedures for a database supplied.
     *  1. for getting list of procedures in the database.
     *  2. for getting procedure domain object for a proc of a database.
     */
    
    /**
     *  gets the list of procedure in the database suupplied by the param.
     *  @param DbName Name of the database
     *  @return list list of proc names in database
     */
    public String[] getProcedureList( String DbName ){
        try{
            int PROCEDURE_NAME = 3;
            
            java.sql.ResultSet rs = this.DatabaseInfo.getProcedures( DbName, null, null );
          
            String Array[][] = this.getArrayofResultSet( rs );
            
            if( Array.length == -1 ){
                return null;
            }
            
            String [] ProcArray = new String[ Array.length ];
            
            for( int i=0 ;i< ProcArray.length; i++ ){
                ProcArray[i] = Array[i][ PROCEDURE_NAME - 1 ];
            }
            
            return ProcArray;
        }
        catch( Exception e){
            System.out.println( "error while getting proc list "+ e  );
            return null;
        }        
    }
    
    /**
     *  Reads the procedure from database server as splecified by the procname n dbname.
     *  @param ProcName name of the procedure to be read
     *  @param DbName name of the database containing the procedure
     */
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ProcedureInterface readProcedure( String ProcName, String DbName ){
               
        int PARAM_NAME = 4;
        int PARAM_TYPE = 5;
        int PARAM_DATA_TYPE = 7;
        int SIZE = 8;
        int COMMENT = 13;
        
        try{
        this.ParentEngine.selectDatabase( DbName );
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Procedure Proc = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Procedure( ProcName, DbName );
        java.sql.ResultSet rs = this.DatabaseInfo.getProcedureColumns( DbName, null, ProcName, null );
        
        String[][] array = this.getArrayofResultSet( rs );
        
        if( array == null || array.length < 0  ) {
            return Proc;
        }
        
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute[] param = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute[ array.length ];
        
        for( int i=0 ;i< array.length; i++){
            param[i] = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute();
            param[i].setName( array[i][ PARAM_NAME - 1 ] );
            param[i].setType( array[i][ PARAM_DATA_TYPE - 1 ] );
            try{
            param[i].setSize( Integer.parseInt( array[i][ SIZE - 1 ] ) );
            }
            catch( Exception e ){
                System.out.println( "error while parsing " + e );
            }
        }
        Proc.setParameters( param );
        
        /**
         * this part of code will read values using query executer
         */
        String query = this.QueryGenerate.getProcedureShowQuery();
        String Mess = this.Exec.executeQuery( query );
        
        if( Mess == this.Exec.EXECUTE_SUCCESS ){            
            rs = this.Exec.returnResult();            
            this.testprint( rs );            
        }
        else{
            System.out.println( " sorry wasnt executed" );
        }
        return Proc;
        }
        catch( Exception e ){
            System.out.println( "error while getting procedure collumn "+ e );
            return null;
        }
        
    }
    
    
     private String getProcedureTerm(){
         try{
         return this.DatabaseInfo.getProcedureTerm();
         }
         catch( Exception e){
             System.out.println( "error while getting procedure term" + e );
             return "PROCEDURE";
         }
     }
     
    public void testprint( java.sql.ResultSet rs ){                
  try{
      java.sql.ResultSetMetaData rmd = rs.getMetaData();
  int coll= rmd.getColumnCount();
  while(rs.next()){
  
	      for( int i=1; i<= coll; i++){
	      System.out.print( rs.getString( i )+"  " );
		  }
		  System.out.println( "\n" );
    }
  rs.first();
	}
	catch( Exception e )
	{
	 System.out.println( e );
	}
    }
    
    
   public final static String[][] getArrayofResultSet( java.sql.ResultSet rs )
   {
       try{
           if( rs == null )
               return new String[][]{};

           int coll = rs.getMetaData().getColumnCount();
           java.util.ArrayList<String[]> rows = new java.util.ArrayList<String[]>();

           while( rs.next() ){
               String[] row = new String[ coll ];
               for( int j = 0; j < coll; j++ ){
                   row[j] = rs.getString( j + 1 );
               }
               rows.add( row );
           }

           return rows.toArray( new String[ rows.size() ][ coll ] );
       }
       catch( Exception e)
       {
           System.out.println( "exception while converting to array "+ e );
           return new String[][]{};
       }
   }

   
    private static int getRowCountofResultSet( java.sql.ResultSet rs) throws java.sql.SQLException {
        int row = 0;
        while( rs.next() ){                    
            row++;
        }     
        rs.first();            
        return row;
    }
    public String[] getSupportedTypesList(){
        try{
        java.sql.ResultSet rs = this.DatabaseInfo.getTypeInfo();
        String[][] array = this.getArrayofResultSet( rs );
        String[] a = new String[array.length ];
        for( int i=0 ;i< a.length; i++){
            a[i] = array[i][0];
        }
        return a;
        }
        catch( Exception e){
            System.out.println( " error while getting types list" + e );
            return null;
        }
    }
    
    public void setTriggers( final org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table Table ){
        
        final int TRIGGER_NAME = 1;
        final int TRIGGER_TABLE = 3;
        final int TRIGGER_EVENT = 2;
        final int TRIGGER_STATEMENT = 4;
        final int TRIGGER_TIMING = 5;
        
        String q = this.QueryGenerate.generateTriggerReadQuery();
        if( this.Exec.executeQuery( q ) == org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.QueryExecuter.EXECUTE_SUCCESS  ){
            java.sql.ResultSet rs = this.Exec.returnResult();
            
            String[][] array = this.getArrayofResultSet( rs );
            
            java.util.ArrayList< org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableTriggerInterface > Temp = new java.util.ArrayList< org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableTriggerInterface > ();
            
            for( int i = 0; i< array.length; i++ ){
                
                if( array[i][ TRIGGER_TABLE - 1 ].equalsIgnoreCase( Table.getName() ) ){
                    
                     org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableTrigger val = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableTrigger( array[i][ TRIGGER_NAME - 1 ] );
                     
                     val.setOperation( array[i][ TRIGGER_STATEMENT - 1 ] );
                     val.setEvent( array[i][ TRIGGER_EVENT - 1 ] );
                     val.setTiming( array[i][ TRIGGER_TIMING - 1 ] );
                     
                     Temp.add( val );                     
                }
                else{
                    // do nothing                   
                }
            }
                
                Table.setTriggers( Temp.toArray( new  org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableTriggerInterface[ Temp.size() ] ) );
        }
        else{
         //   System.out.println( "error while reading triggers" );
            Table.setTriggers( null );// system could not read the triggers so there was an error n hence setting it to null
        }
        
    }// func ends
    
    org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.BackEnd ParentEngine = null;
    org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.QueryGenerator QueryGenerate = null;
    org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.QueryExecuter Exec = null;
    private java.sql.DatabaseMetaData DatabaseInfo = null;
    
}// class ends
