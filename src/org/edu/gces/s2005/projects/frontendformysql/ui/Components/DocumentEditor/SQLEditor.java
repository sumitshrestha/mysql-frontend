
//                        !! RAM !!

/**
 * SQLEditor.java
 *
 * Created on October 30, 2007, 1:17 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor;

/***
 *
 * @author sumit
 */
public class SQLEditor extends javax.swing.JEditorPane {
    
    /*** Creates a new instance of SQLEditor */
    public SQLEditor( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOManager IOManager ) {        
        super.setFont( new java.awt.Font( java.awt.Font.MONOSPACED,java.awt.Font.PLAIN,18) );
        this.ActionProvider = new org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.Edit.DocEditActionProvider( this );
        this.FileManager = new org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.File.FileManager( IOManager, this );
    }
    
    public org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.Edit.DocEditActionProvider getActionProvider(){
        return this.ActionProvider;
    } 
    
    public org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.File.FileManager getFileManager(){
        return this.FileManager;
    }
    
    private final void addEditorFilter(){
        javax.swing.text.StyledDocument styledDoc = ( javax.swing.text.StyledDocument ) this.getDocument();
        if ( styledDoc instanceof javax.swing.text.AbstractDocument) {
            javax.swing.text.AbstractDocument doc = ( javax.swing.text.AbstractDocument )styledDoc;
            doc.setDocumentFilter( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.Editor.EditorFormator() );
            System.out.println("fileter added");
        } 
        else
            System.out.println("fileter not added");
    }
    
    private org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.File.FileManager FileManager = null;
    private org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.Edit.DocEditActionProvider ActionProvider = null;    
}
