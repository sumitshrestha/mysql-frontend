
//                       !! RAM !!

/**
 * DisplayMessageParser.java
 *
 * Created on October 25, 2007, 6:28 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.ui.Components;


/***
 *
 * @author sumit
 */
public class DisplayMessageParser {
    
    /*** Creates a new instance of DisplayMessageParser */
    public DisplayMessageParser() {
    }
    
    public static String[] Parse( String Mess ){
        java.util.ArrayList<String> Array = new java.util.ArrayList< String >();
        
        if( Mess.length() <= len ){
            Array.add( Mess );
        }
        else{
            int i = 0;
            while( Mess.length() > i){
                
                try{
                String token = Mess.substring( i, i += len );                
                Array.add( analyze( token ) );
                }
                catch( java.lang.IndexOutOfBoundsException e ){
                    String token = Mess.substring( i - len );
                    Array.add( token );
                }
                
            }// while ends
        }
        
        return Array.toArray( new String[Array.size() ] );
    }
    
    private final static String analyze( String mess ){
        if( ! mess.endsWith( " " ))
        {
            return mess += "-";
        }
        else
            return mess;
    }
    
    public final static String returnParsedMessage( String Mess ){
        String[] Array = Parse( Mess );
        String parsedString = "";
        
        for( int i=0; i< Array.length; i++){
            parsedString += Array[i] + "\n";
        }
        
        return parsedString;
    }
    
    public static void main( String[] ar ){
        String a = org.edu.gces.s2005.projects.frontendformysql.ui.Components.DisplayMessageParser.returnParsedMessage( "jai shree ramjai               shree ramjai shree ramjai shree ramjai shree ram" );
        
            System.out.println( a );
        
    }
    
    public static int len = 50;
}
