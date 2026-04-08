
//                         !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql;

/***
 *
 * @author Sumit Shrestha
 */
public class splash {
    private splash(){
        
    }
    
    /**
     * This method will initialize the system including displaying splash screen to user.
     * 
     * @return true if successfully initialized else returns false.
     */
    public static String init( ){
        try{
            final java.awt.SplashScreen splash = java.awt.SplashScreen.getSplashScreen();
            System.out.println("splash obtained");
            if (splash == null) {                
                return "SplashScreen.getSplashScreen() returned null";
            }
            
            java.awt.Graphics2D g = splash.createGraphics();
            if (g == null) {                
                return "g is null";
            }
            
            // 1. initialize system
            renderSplashFrame( g, "initializing System..." );
            splash.update();
            java.lang.Thread.sleep(999);
            Factory.initializeSystemStatus();            
            
            renderSplashFrame( g, "initializing BackEnd..." );            
            splash.update();
            java.lang.Thread.sleep(999);
            org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.BackEnd Engine = Factory.createEngine();
            
            
            renderSplashFrame( g, "initializing FrontEnd..." );
            splash.update();
            java.lang.Thread.sleep(999);
            javax.swing.JFrame FrontEnd = new org.edu.gces.s2005.projects.frontendformysql.ui.parent.MainFrame(Engine);
            
            splash.close();
            
            FrontEnd.setVisible( true );
            
            return INITIALIZED;
        }
        catch( Exception e ){
            return e.getMessage();
        }
    }
    
    private static void renderSplashFrame( java.awt.Graphics2D g, String Text ) {
        
        g.setComposite( java.awt.AlphaComposite.Clear);
        g.setColor( java.awt.Color.black );
        g.setFont( new java.awt.Font(java.awt.Font.MONOSPACED,java.awt.Font.PLAIN,14) );
        g.fillRect(50,300,199,45);
        g.setPaintMode();               
        g.drawString( Text + "...", 50, 340);        
    }
    
    
    public static String INITIALIZED = "initialized";
}
