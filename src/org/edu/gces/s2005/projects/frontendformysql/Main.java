
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

/***
 *
 * @author Sumit Shrestha
 */
public class Main {
    
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
            System.out.println( "Error!!\n" + mess +" \n so initizlizing without splash screen...");
            Factory.initializeSystemStatus();
            org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.BackEnd Engine = Factory.createEngine();
            javax.swing.JFrame FrontEnd = new org.edu.gces.s2005.projects.frontendformysql.ui.parent.MainFrame(Engine);
            FrontEnd.setVisible( true );
        }// if ends
    }
    
}
