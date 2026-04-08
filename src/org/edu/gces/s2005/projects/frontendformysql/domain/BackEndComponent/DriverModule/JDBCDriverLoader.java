
//                            !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule;

/**
 * This class will handle the task of loading drivers.
 * 
 * @author Sumit Shrestha
 */
public class JDBCDriverLoader {
    
    /**
     * This will initialize its instance with path of driver library from which it loads driver as requested by driver manager.
     * 
     * @param DriverLibrary path of folders where drivers are kept
     */
    public JDBCDriverLoader( String DriverLibrary ){    
        this.DriverLibrary = DriverLibrary;
    }
    
    public void setDriverLibrary( String DriverLibrary ){
        this.DriverLibrary = DriverLibrary;
    }
    
    public java.sql.Driver loadDriver( String DriverName, final String DriverJarFileName ){
        try{
        
            java.net.URL url = new java.net.URL( "jar","", "file:///" + this.DriverLibrary.replace( '\\', '/' ) +  DriverJarFileName + "!/" );
            
        Class temp = this.DriverJarLoader.loadClass(DriverName, url );
        
        if( temp == org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.InvalidJAR.class ){// jar file was invalid then
            this.setErrorMessage( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.InvalidJAR.ErrorMessage );
            return null;
        }
        
        return ( java.sql.Driver ) temp.newInstance();
        }
        catch( Exception e ){
            this.setErrorMessage( e.getMessage() );
            return null;
        }
    }
    
    public final String getErrorMessage(){
        return this.ErrorMessage;
    }
    
    private void setErrorMessage( final String ErrorMess ){
        this.ErrorMessage = ErrorMess;
    }
        
    // field for holding loacation of drivera
    private String DriverLibrary = null;// defined by driver manager
    
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.JarClassLoader DriverJarLoader = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.JarClassLoader();
    
    private String ErrorMessage = null;
}
