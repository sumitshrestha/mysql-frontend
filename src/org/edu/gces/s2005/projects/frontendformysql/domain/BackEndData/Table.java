
//                          !! RAM !!

/**
 * Table.java
 *
 * Created on July 21, 2007, 3:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData;

/***
 *
 * @author sumit
 */
public class Table  {
    
    /*** Creates a new instance of Table */
    public Table(String Nm) {
        Name= Nm;
    }
    
    public Table()
    { } // do nothing
    
    /**
     * These are 
     *            MUTATORS
     */
    
    public void setName( String Nm )
    {
        Name = Nm ;
    }// func ends
    
    public void setComment( String Com )
    {
        Comment = Com;
    }// func ends
    
    public void setType( String T )
    {
        Type = T;
    }// func ends
    
    public  void setRowFormat( String F)
    {
        RowFormat = F;
    }// func ends
    
    public boolean addAttribute( TableAttribute att )
    {
        return Attributes.add( att );
    }// func ends
    
    public void addAttribute( TableAttribute att, int pos )
    {
        Attributes  .add( pos, att );
    }// func ends
    
    public boolean removeAttribute( TableAttribute att )
    {
        return Attributes  .remove( att );
    }// func ends
    
    public boolean removeAttribute( int pos )
    {
        return (  Attributes  .remove( pos ) != null ) ;
    }// func ends
    
    public boolean insertRow( String[] row )
    {
        if( row.length < 1 || row.length != Attributes.size() )
        return false;
        
        for( int i =0; i< row.length; i++ )
        {
           org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute Att =  ( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute ) Attributes.get( i );
           Att.add( row[i] );
        }
        
        TotData ++;
        
        return true;
    }
    
    public void setForeignKeys( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ForeignKeyInterface[] forkey){
        this.ForeignKey = forkey;
    }
    
    public void setTriggers( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableTriggerInterface[] Trigg ){
        this.Triggers = Trigg;
    }
    
    
    /**
     * These are 
     *           NON MUTATORS
     */
    
    public String getName()
    {
        return Name;
    }// func ends
    
    public String getComment()
    {
        return Comment;
    }// func ends
    
    public String getType()
    {
        return Type;
    }// func ends
    
    public String getRowFormat()
    {
        return RowFormat;
    }// func ends
    
    public TableAttribute getAttribute( int pos )
    {
        return ( TableAttribute ) Attributes .get( pos ) ;
    }// func ends
    
    public TableAttribute[] getAttributes( )
    {
       Object[] temp =  Attributes.toArray();
       TableAttribute [] AttArray = new TableAttribute [ temp.length ] ;
       
       // conversion
       for( int i=0; i< temp.length; i++ )
           AttArray[ i ] = ( TableAttribute ) temp[ i ];
       
       return AttArray;
    }// func ends
    
    public String[] returnRow( int index )
    {
        if( index < 0 )
        return null;
        
        String[] row = new String[ Attributes.size() ];
        
        for( int i=0; i< row.length; i++ )
        {
            row[ i ] = ( String ) ( ( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute ) Attributes.get( i ) ).get( index ); // getting ith collumn value of row no index            
        }
        
        return row;
    }
    
    public int getRowCount()
    {
       return TotData;    
    }
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ForeignKeyInterface[] getForeignKeys(){
        return this.ForeignKey;
    }
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableTriggerInterface[] getTriggers(){
        return this.Triggers;
    }
    
    
    
    private String Name=null;
    private String Comment;
    private String Type;
    private String RowFormat;
    private java.util.ArrayList<org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute> Attributes = new java.util.ArrayList<org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute>();
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ForeignKeyInterface[] ForeignKey = null;
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableTriggerInterface[] Triggers = null;
    private int TotData = 0; 
}
