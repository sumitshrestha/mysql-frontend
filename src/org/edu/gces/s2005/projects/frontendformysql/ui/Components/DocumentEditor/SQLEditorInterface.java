
//                      !! RAM !!

/**
 * SQLEditorInterface.java
 *
 * Created on October 30, 2007, 7:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor;

import org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ProcedureInterface;
import org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.Edit.DocEditActionProvider;
import org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.File.FileManager;

/***
 *
 * @author sumit
 */
public interface SQLEditorInterface {    

    FileManager getManager();

    DocEditActionProvider getActionProvider();
    
}
