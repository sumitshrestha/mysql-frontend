
//                               !! RAM !!


/**
 * ProcedureExecuter.java
 *
 * Created on October 17, 2007, 2:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd;

/***
 *
 * @author sumit
 */
public class ProcedureExecuter {
    
    /*** Creates a new instance of ProcedureExecuter */
    public ProcedureExecuter( java.sql.Connection c ) {
        this.Conn = c;
    }
    
    public String ExecuteProcedure( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ProcedureInterface Proc ){
        try{
            String c = this.getCall( Proc );
            c = "{ call "+ c +"}";
           System.out.println( c ); 
        this.CallCs = this.Conn.prepareCall( c );
        rs = this.CallCs.executeQuery();       
       // this.testprint( rs );
        return org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.QueryExecuter.EXECUTE_SUCCESS;
        }
        catch( Exception e ){
            System.out.println( e.toString() );
            return e.getMessage();
        }
    }
    
    public java.sql.ResultSet getResult(){
        return rs;
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
    
    private String getCall( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ProcedureInterface Proc ) {
        String Call = "";
        
        Call += Proc.getDatabase();
        
        Call += ".";
        
        Call += Proc.getName();
        
        Call += " ( ";
        
        for( int i=0 ;i< Proc.getParameters().length; i++){
            if( i != 0)
                Call += " , ";
            
            Call += " @";
            Call += "param"+i;
        }
        
        Call += " ) ";
        
        return Call;
    }
    
    private java.sql.CallableStatement CallCs = null;
    private java.sql.Connection Conn = null;
    private java.sql.ResultSet rs = null;
}
