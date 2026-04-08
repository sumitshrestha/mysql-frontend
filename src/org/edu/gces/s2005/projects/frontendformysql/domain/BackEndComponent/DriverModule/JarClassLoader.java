
//                               !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule;

import java.net.URL;
import java.net.URLClassLoader;

/**
 *  This is a test jar class loader. i m testing for getting the driver name from any driver that user supplies.
 *  A class loader for loading jar files, both local and remote.
 * 
 *  @author Sumit
 */
public class JarClassLoader extends URLClassLoader{
    
    /**
     * Creates a new JarClassLoader for the specified url.
     */
    public JarClassLoader( ) {        
        
        super(new URL[] {});        
        
    }
        
    
    public static String getJarFileName( java.net.URL JarURL ){
        String path = JarURL.getPath();
        
        int index = path.lastIndexOf( "/", path.length()- 2 );
        
        if( index == -1 )
            return null;
        
        return path.substring( index + 1, path.length()- 2);
        
    }// class ends
    
    
    public Class loadClass( String ClassName, java.net.URL JarURL ){
        
        this.setJarURL( JarURL );
        
        if( ! isJar( JarURL ) ){
            org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.InvalidJAR.ErrorMessage = "Not a Jar File";
            return org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.InvalidJAR.class;
        }
        
        try{
            return super.loadClass( ClassName );        
        }
        catch( Exception e ){
            org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.InvalidJAR.ErrorMessage = e.getMessage();
            return org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.InvalidJAR.class;
        }
    }// loadClass ends
    
    public void setJarURL( java.net.URL JarURL ){
        
        super.addURL( JarURL  );        
        
    }
    
    /**
     * This method is used to check if the url points to a jar file only.
     */
    public static boolean isJar( java.net.URL JarURL ){
        try{
        java.net.JarURLConnection jarCon = ( java.net.JarURLConnection ) JarURL.openConnection() ;
        
        return jarCon.getJarFile() != null;
        }
        catch( Exception e ){
            System.out.println( "excpetion while checking jar" + e.getMessage() );
            return false;
        }
    }
    
    public static void main( String[] a ){
        try{
        JarClassLoader ld = new JarClassLoader();
        String drnm = "com.mysql.jdbc.Driver";
        java.sql.Driver d = ( java.sql.Driver ) ld.loadClass( drnm, new java.net.URL ( "jar:file://localhost/C:/mysql-connector-java-5.0.6-bin.jar!/" ) ).newInstance();
        
        System.out.println( d.toString().startsWith(drnm) );
        }
        catch( Exception e ){
            System.out.println( "exception in url making " + e.getMessage() );
        }
    }
    
}
