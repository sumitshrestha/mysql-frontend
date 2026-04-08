
//                                      !! RAM !!

/**
 * TableInterface.java
 *
 * Created on August 18, 2007, 6:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 *
 * @author sumit
 */
public interface TableInterface {
   
    public String getTableName();
    
    public String[] returnCollumnNames();
  
    public String[] returnCollumnTypes();
    
    public Object[][] returnTableData();
    
    public String[] returnPrimaryKeys();
    
    public String[] returnForeignKeys();
    
    public String returnCollType( String CollNm );
	
    public String returnCollSize( String CollNm );
	
    public boolean isPrimaryKey( String CollNm );
	
    public boolean isForeignKey( String CollNm );
	
    public boolean isNullSupported( String CollNm );	
	
    public final String NULL = "NULL";
}
