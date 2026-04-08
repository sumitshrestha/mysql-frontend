
//                                  !! RAM !!

/**
 * QueryGenerator.java
 *
 * Created on July 21, 2007, 9:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd;

import org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute;


/***
 *
 * @author sumit shrestha
 */
public class QueryGenerator {
    
    /*** Creates a new instance of QueryGenerator */
    public QueryGenerator() {
    }
    
    public String returnDBshowQuery()
    {
       return "show Databases;";
    }
    
    public String generateDatabaseUsageQuery( String DBName )
    {
        return "use "+ DBName+ " ;";
    }// func ends
    
    public String generateDatabaseCreateQuery( String DBName )
    {
        return " create database "+ DBName;
    }// func ends
    
    public String generateDatabaseDeleteQuery( String DbName )
    {
        return " drop database "+DbName;
    }// func ends
    /***
     * This function generates create table query as filled by the form.
     **/    
    public String generateQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DatabaseTableChangeInterface TbCreateEvnt  )
    {
        String Q= "create Table ";
        
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table Tb = TbCreateEvnt.returnDatabaseAffectingTable();
        
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute [] Attr = Tb.getAttributes();
        
        // steps begin 
        // 1.
        Q += Tb.getName() + " ( "; // table name added
        
        // 2. step
        Q = this.addColltoQuery( Q, Attr );
        
        // 3. step
        
        for( int i= 0; i< Attr.length ; i++)
        {
            if( Attr[i].getAttKey() == org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute.PRIMARY_KEY)
            Q += " , " +  "Primary key ( "+ Attr[i].getAttName() + " ) ";
        }
                
        Q += "  );";
       
// System.out.println( Q );
        
        return Q;
    }
    
    private final String addColltoQuery( String Q, org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeInterface[]  Attr)
    {
    
        for( int i=0; i< Attr.length; i++){
            
            if( i != 0 )
        Q += " , " + Attr[ i ].getAttName() + " " + Attr[ i ].getAttType()  ;         
            else
        Q += "  " + Attr[ i ].getAttName() +  " " +  Attr[ i ].getAttType() ;     
            
            // for keeping size if the type is bool
            if( Attr[i].getAttType(). equals( TableAttribute.BOOLEAN )  )
            {
                // do nothing
            }
            else
            {
        Q += " ( " +  Attr[ i ].getAttSize() + " ) ";
            }
            
            
             if( ! Attr[i].isNullable() )
            {
                Q += " NOT "+org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableInterface.NULL + " ";
            }
            
        }
        
        return Q;
    }
    
    public String returnTableShowQuery()
    {
        return "show tables;";
    }// func ends
    
    public String generateTableSelectQuery( String TableName )
    {
        return "select * from "+ TableName + ";";
    }// func ends
    
    public String generateTableInsertQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableValueChange T )
    {
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Tuple[] InsertedValues = T.getInsertedValues();
        
        String Query = " insert into "; // create empty query
        
        Query += T.getTableName();
        
        Query += " ( ";
        for( int i=0;i<InsertedValues.length; i++ )
        {
            if( i!= 0 )
                Query += " , ";
            
            Query += InsertedValues[i].getCollNm( );
			
        }
        
        Query += " ) ";
        Query += " values ( ";
        
        for( int i=0; i<InsertedValues.length ;i++)
        {
            
            if( i != 0 )
                Query += " , ";
            
			
			if( this.isVarchar( T.returnCollType( InsertedValues[i].getCollNm( ) ) ) ){
			Query += " '";
			Query +=  InsertedValues[i].getValue() ;
			Query += "' ";
			}
			else			
            Query +=  InsertedValues[i].getValue() ;
            
        }
        
        Query += " ); ";
        
        return Query ;
    } // func ends
    
    
    // this func returns if the parameter passed is varchar or not 
    public boolean isVarchar( String Type )
    {       
        return Type.equals( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute.VARCHAR ) ;            
    }// func ends
    
    public String generateTableValueDeleteQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableValueChange T )
    {
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Tuple[] delTp = T.getChangedTuple();
       
        String Query = " DELETE FROM "; 
        
        // add table name
        Query += T.getTableName();
        
        // add conditional tuple
        Query += " WHERE ";
        
        for( int i=0; i<delTp.length; i++ ){
            
            if( i != 0 )
                Query += " AND ";
            if( this.isVarchar( T.returnCollType( delTp[i].getCollNm() ) ) )
                Query += " "+ delTp[i].getCollNm()  + " = '"+ delTp[i].getValue() + "'";
            else
                Query += " "+ delTp[i].getCollNm() + " = "+ delTp[i].getValue();            
            
        }
        return Query;
    }// func ends
    
    
     public String generateTableValUpdateQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableValueChange T )
    {
        String Query = " update ";
        
        Query += T.getTableName() + " set ";
        
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Tuple [] Change = T.getUpdatedValues();
        
        Query += Change[0].getCollNm()+ " = ";
        
        Query += ( this.isVarchar( T.returnCollType(Change[0].getCollNm() ) ) ? "'"+Change[0].getValue()+"' " : " "+Change[0].getValue()+ " " );
        
        Query += "where ";
        
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Tuple [] UpdateTp = T.getChangedTuple();
        
         for( int i=0; i<UpdateTp.length; i++ ){
            
            if( i != 0 )
                Query += " AND ";
            if( this.isVarchar( T.returnCollType(  UpdateTp[i].getCollNm() ) ) )
                Query += " "+ UpdateTp[i].getCollNm()  + " = '"+ UpdateTp[i].getValue() + "'";
            else
                Query += " "+ UpdateTp[i].getCollNm() + " = "+ UpdateTp[i].getValue();            
            
        }
       
       Query += " ;";
        
        return Query;
    }// func ends
    
    /**
     *  This code will generate the table attributes addition query.
     */
     public String generateTableAttAddQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeChangedInterface TableChange )
     {
         String Query = " alter table ";
         
         org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table Tb = TableChange.getChangedTable();
                  
         org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeInterface[] AddAtt = TableChange.getNewAttributesValues();
                  
         Query +=" "+ Tb.getName()+" add column ";
         
         Query = this.addColltoQuery( Query , AddAtt );        
         
         Query += " ; ";
         
         return Query;
     }
     
     public String generateTableAttDeleteQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeChangedInterface TableChange )
     {
         String Query = " alter table ";
     
         org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Table Tb  = TableChange.getChangedTable();
         
         org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeInterface[] ChangedAtt = TableChange.getChangedAttributes();
              
         Query +=" "+ Tb.getName()+" drop column ";
         
         Query += ChangedAtt[0].getAttName() + " ; ";
         
         return Query.trim();
     }// func ends
     
     
     public String generateForeignKeyAddQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ForeignKeyChangeInterface ForKeyChangedEvt ){
         
         String Query = "alter table ";
         
         Query += ForKeyChangedEvt.getChangedTable();
         
         Query += " add ";
         
         // IMP:: adding constraints
         Query += " foreign key ( ";
         
         Query += ForKeyChangedEvt.getNewForeignKey().getAttName() + " )";
         
         Query += " references ";
                 
         // IMP:: adding references
         Query += ForKeyChangedEvt.getNewForeignKey().getPk_Table();
         
         Query += " on delete "+ ForKeyChangedEvt.getNewForeignKey().getDelete_Rule();
         
         Query += " on update " + ForKeyChangedEvt.getNewForeignKey().getUpdate_Rule();
         
         Query += " ;";
         
         return Query;
     }// func ends
     
     public String generateForeignKeyDeleteQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ForeignKeyChangeInterface ForKeyDropEvt ){
         
         String Query = "alter table ";
         
         Query += ForKeyDropEvt.getChangedTable();
         
         org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ForeignKeyInterface DelForKey = ForKeyDropEvt.getChangedForeignKey();
         
         Query += " drop foreign key ";
         
         try{
         Query += DelForKey.getAttName() ;
         }
         catch( Exception e ){
             return null; // no query 
         }
         
         Query += ";";
         
         return Query;
         
     }// func ends     
    
    public String generateTableDeleteQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DatabaseTableChangeInterface TbDelEvnt )
    {
        if( TbDelEvnt.returnDatabaseAffectingTable() == null ) 
            return null;
        
        String Query = "drop table " + TbDelEvnt.returnDatabaseAffectingTable().getName() +" ;";        
        return Query;
    }// func ends
    
    public String generateViewDeleteQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DatabaseViewChangeInterface deletedView )
    {
        String Query = "drop view "+ deletedView.getViewName() + ";" ;
        return Query;
    }// func ends
    
    public String generateViewCreateQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DatabaseViewChangeInterface addedView  )
    {
        String Query = " create view " ;
        
        Query += addedView.getViewName() +" as ";
        
        Query +=  addedView.getScript() + ";";
        
        return Query;
    }// func ends
    
    public String gnerateTriggerAdditionQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TriggerChangeInterface TriggerChangeEvt ){
        
        String Query = "CREATE TRIGGER ";
        /**
        Query += "'" + TriggerChangeEvt.getTriggerDatabase() + "'";
                
        Query += ".";
        */
        Query += TriggerChangeEvt.getNewTrigger().getName() ;        
                
        Query += " " + TriggerChangeEvt.getNewTrigger().getTiming();
        
        Query += " " + TriggerChangeEvt.getNewTrigger().getEvent();
        
        Query += " ON ";
        
        Query += TriggerChangeEvt.getChangedTableName();               
        /**
        Query += " REFERENCING ";
        
        Query += " OLD ROW AS ";
        
        Query += TriggerChangeEvt.getNewTrigger().getOldReference();
        
        Query += " , NEW ROW AS ";
        
        Query += TriggerChangeEvt.getNewTrigger().getNewReference();
        */
        Query += " FOR EACH ROW ";
        /**
        Query += " WHEN ";
        
        Query += TriggerChangeEvt.getNewTrigger().getCondition();
        */
        //Query += " BEGIN ";
        
        Query += TriggerChangeEvt.getNewTrigger().getOperation();

        //Query += " END";        
        
        Query += ";";
        
        return Query;
    }// func ends
    
    public String generateTriggerDeleteQuery( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TriggerChangeInterface TriggerdeleteEvt ){
        String Query = "DROP TRIGGER ";
        
        Query += TriggerdeleteEvt.getTriggerDatabase();
        
        Query += ".";
        
        Query += TriggerdeleteEvt.getChangedTrigger().getName();
        
        Query += ";";
        return Query;
    }
    
    public String generateTriggerReadQuery(){
        return "show triggers;";
    }
    
    public final String getProcedureShowQuery( ){
        return "show procedure;";
    }
    
}
