
//                         !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 *
 * @author Sumit Shrestha
 */
public class splash {
    private static final Logger LOG = LoggerFactory.getLogger(splash.class);

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
            if (splash == null) {
                LOG.info("AWT splash is unavailable; using Swing fallback splash");
                return initWithFallbackSplash();
            }
            LOG.info("AWT splash obtained");
            
            java.awt.Graphics2D g = splash.createGraphics();
            if (g == null) {                
                LOG.info("AWT splash graphics is unavailable; using Swing fallback splash");
                return initWithFallbackSplash();
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
            LOG.error("Error during splash initialization", e);
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

    private static String initWithFallbackSplash(){
        FallbackSplash fallback = null;
        try{
            fallback = new FallbackSplash();
            fallback.show();

            fallback.update( "initializing System..." );
            java.lang.Thread.sleep(999);
            Factory.initializeSystemStatus();

            fallback.update( "initializing BackEnd..." );
            java.lang.Thread.sleep(999);
            org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.BackEnd Engine = Factory.createEngine();

            fallback.update( "initializing FrontEnd..." );
            java.lang.Thread.sleep(999);
            javax.swing.JFrame FrontEnd = new org.edu.gces.s2005.projects.frontendformysql.ui.parent.MainFrame(Engine);

            fallback.close();
            FrontEnd.setVisible( true );
            return INITIALIZED;
        }
        catch( Exception e ){
            if( fallback != null )
                fallback.close();
            LOG.error("Error during fallback splash initialization", e);
            return e.getMessage();
        }
    }

    private static class FallbackSplash{
        private final javax.swing.JWindow window;
        private final javax.swing.JLabel status;

        private FallbackSplash(){
            this.window = new javax.swing.JWindow();
            javax.swing.JPanel panel = new javax.swing.JPanel( new java.awt.BorderLayout() );
            panel.setBorder( javax.swing.BorderFactory.createEmptyBorder( 12, 14, 12, 14 ) );
            panel.setBackground( java.awt.Color.white );

            javax.swing.JLabel title = new javax.swing.JLabel( "Front End For MySQL" );
            title.setFont( new java.awt.Font( java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 16 ) );

            this.status = new javax.swing.JLabel( "starting..." );
            this.status.setFont( new java.awt.Font( java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 12 ) );

            panel.add( title, java.awt.BorderLayout.NORTH );
            panel.add( this.status, java.awt.BorderLayout.SOUTH );
            this.window.getContentPane().add( panel );
            this.window.setSize( 420, 120 );
            this.window.setLocationRelativeTo( null );
        }

        private void show(){
            this.window.setVisible( true );
        }

        private void update( String message ){
            this.status.setText( message );
            this.window.repaint();
        }

        private void close(){
            this.window.setVisible( false );
            this.window.dispose();
        }
    }
    
    
    public static String INITIALIZED = "initialized";
}
