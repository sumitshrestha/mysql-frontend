
//                         !! RAM !!


/**
 * QueryExecuter.java
 *
 * Created on July 21, 2007, 3:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
/***
 *
 * @author sumit
 */
public class QueryExecuter {
    
    /*** Creates a new instance of QueryExecuter */
    public QueryExecuter() {
    }


public String getMessage()
{
  return Message;
}

public String executeQuery( String q )
{
    String[] QueryArray = null;
    
    try{
    QueryArray = q.split( " " );  
    }
    catch( Exception e )
    {
        return e.toString();        
    }
            
   boolean Update=false;
   
   // is the query for update
   for( int i = 0; i< QueryArray.length; i++)
   {
     if( isDML ( QueryArray[i] ) )
	 {
	 Update = true;
	 break;
	 }
   }
   
   try{
   
   // executing the query
   if( Update ) 
   {
     st.executeUpdate( q );    
     // for update query that returns null result i m putting it null to explicitly specify that previous query was upate.
     rs = null;
     return EXECUTE_SUCCESS; // update query executed
   }
   else
   {
   	 rs=st.executeQuery( q );
         java.sql.SQLWarning warn = rs.getWarnings();
         printWarning(warn);
	 rmd = rs.getMetaData();
         return EXECUTE_SUCCESS; // query executed
   }
   
   }
   catch( Exception e )
   {
   Message = e.toString();   
   return Message;// query cannot be executed
   }
   
}// func ends

    private void printWarning(java.sql.SQLWarning warn) {
        
        if (warn != null) {
		System.out.println("\n---Warning---\n");
		while (warn != null) {
			System.out.println("Message: "
                                          + warn.getMessage());
			System.out.println("SQLState: "
                                          + warn.getSQLState());
			System.out.print("Vendor error code: ");
			System.out.println(warn.getErrorCode());
			System.out.println("");
			warn = warn.getNextWarning();
		}
        }// if ends
        else
        {} // do nothing
    }

public void displayResult()
{
  try{
  int coll= rmd.getColumnCount();
  while(rs.next()){
  
	      for( int i=1; i<= coll; i++){
	      System.out.print( rs.getString( i )+"  " );
		  }
		  System.out.println( "\n" );
    }
	}
	catch( Exception e )
	{
	 System.out.println( e );
	}
}

// this is dummy test
public java.sql.ResultSet returnResult()
{
     return rs;
}// func ends


public final static boolean isDML( String Query)
{
  for( int i =0; i<DML.length; i++) 
  {
  
  if( DML[i].equalsIgnoreCase( Query ) )
  {
    return true;
  }
  
  }
  return false;
}

public void onConnection( java.sql.Connection conn ){
    this.conn = conn;
    
    try{
        this.st = this.conn.createStatement();
    }
    catch( java.sql.SQLException e ){
        System.out.println("exception while initializing connection for query executer "+ e );
    }
}
 
 private boolean ConnectState= false;// initilally not connected
 private Connection  conn = null;// initially connection is not done  
 private String Message=null;// initially no message
 private Statement st = null;
 
   /*** these variables are subjected to change*/
   
   ResultSet         rs = null;
   java.sql.ResultSetMetaData rmd ;
   
   private int Row;
  
   /**
  Fields storing the attributes of the database query
*/
  private static String[] DML = { "update" , "delete", "insert", "use", "create" , "drop" , "alter" };
  
  private static String[] DDL = { "select"};
  
  // for this checking success in the state of execution
  public static final String EXECUTE_SUCCESS = "OK";
}
