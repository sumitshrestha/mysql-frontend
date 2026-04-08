
//                        !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.ui.Components.NewDriverWizard;

/***
 * This interface is used by next n back button to communicate with the card panels.
 * 
 * @author Sumit Shrestha
 */
public interface WizardInterface {
    
    /**
     * This method is used to handle the tasks when next button is clicked.
     */
    boolean onNext();
    
    /**
     * This method is used to handle the tasks when back button is clicked
     */
    boolean onBack();
    
}
