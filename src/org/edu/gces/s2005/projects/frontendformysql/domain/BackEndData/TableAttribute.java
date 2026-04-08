
//                                   !! RAM !!

/**
 * TableAttribute.java
 *
 * Created on July 27, 2007, 9:36 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData;

/***
 *
 * @author user
 */
public class TableAttribute implements org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeInterface  {
    
    /*** Creates a new instance of TableAttribute */
    public TableAttribute() {
    }
    
    /**
     *  THESE ARE 
     *             MUTATORS
     */
    
    public void setName( String Nm )
    {        
         Name = Nm;        
    }// func ends
    
    public void setSize( int l )
    {
        Length = l;
    }// func ends
    
    
    public void setType( String Tp )
    {
         Type = Tp;
    }// func ends

    public void setKey( String K )
    {
        Key = K ;
    }// func ends
    
    public void add( Object val )
    {
        Values.add( val );
    }
    
    public void remove( int i )
    {
        Values.remove( i );
    }
    
    public Object get( int i )
    {
        return Values.get( i );
    }
    
    public void setNullable( boolean n )
    {
        Nullable = n;
    }

    public void setDefaultValue( String val )
    {
        DefaultVal = val;
    }
    /**
     * THESE ARE 
     *             NON MUTATORS
     */
        
    public String getAttName()
    {
        return Name;
    }// func ends
    
    public String getAttType()
    {
        return Type;
    }// func ends
    
    public int getAttSize()
    {
        return Length;
    }// func ends
    
    public String getAttKey()
    {
        return Key;
    }// func ends
    
    
    public boolean isNullable(  )
    {
        return Nullable; 
    }
    
    public String getAttDefaultVal()
    {
        return DefaultVal;
    }
    /**
     * These variables are fine for describing an attribute of a table. But Still i m not sure of their type.
     */
    
    private String Name;
    private String Type;
    private int Length;
    private String Key; 
    private boolean Nullable;    
    private java.util.Vector Values = new java.util.Vector();
    private String DefaultVal = null;
    
    /**
     * CONSTANT ATTRIBUTES
     */
    public final static String VARCHAR = "VARCHAR" ;
    public final static String BOOLEAN = "BOOL" ;
    public final static String CHAR = "CHAR" ;
    public final static String INTEGER = "INT" ;
    public final static String NUMERIC = "NUMERIC" ;
    public final static String DATE = "DATE";
    public final static String TIME = "TIME";
    public final static String TIMESTAMP = "TIMESTAMP";
    public final static String CLOB = "CLOB" ;
    public final static String BLOB = "BLOB" ;   
    
    /**
     * KEY TYPES
     */
    public final static String PRIMARY_KEY = "PRIM";
    public final static String FOREIGN_KEY = "FOREIGN";
    public final static String ORDINARY_KEY = "ORD";
    
}
