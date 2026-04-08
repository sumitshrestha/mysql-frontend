
//                         !! RAM !!


/**
 * Main.java
 *
 * Created on July 21, 2007, 2:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 *
 * @author Sumit Shrestha
 */
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    
    /*** Creates a new instance of Main  */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String mess = org.edu.gces.s2005.projects.frontendformysql.splash.init( );        
        if( mess != splash.INITIALIZED ){
            LOG.warn( "Splash init failed: {}. Initializing without splash screen...", mess );
            Factory.initializeSystemStatus();
            org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.BackEnd Engine = Factory.createEngine();
            javax.swing.JFrame FrontEnd = new org.edu.gces.s2005.projects.frontendformysql.ui.parent.MainFrame(Engine);
            FrontEnd.setVisible( true );
        }// if ends
    }
    
}
