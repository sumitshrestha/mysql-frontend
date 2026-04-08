
//                                    !! RAM !!

/**
 * temp.java
 *
 * Created on October 30, 2007, 12:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.Edit;

/**
 * @author sumit shrestha
 */
public class DocEditActionProvider{
       
       public DocEditActionProvider( javax.swing.JEditorPane EditorText ){
           
           this.EditorText = EditorText;
           
           this.DocEditMan = new org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.Edit.DocEditActionManager( EditorText );
           Edit = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.Editor.DocumentEditor( EditorText );
           
           Findform = new org.edu.gces.s2005.projects.frontendformysql.ui.Forms.DocumentFindForm( null , Edit, EditorText );
           ReplaceForm = new org.edu.gces.s2005.projects.frontendformysql.ui.Forms.DocumentReplaceForm( null, Edit, EditorText );
        
      }
       
        private javax.swing.Action FindAction = new javax.swing.AbstractAction( "Find" ) {
            public void actionPerformed( java.awt.event.ActionEvent e) {            
                EditorText.requestFocusInWindow();
                Findform.setVisible( true );    
            }
        };
        
        private javax.swing.Action ReplaceAction = new javax.swing.AbstractAction( "Replace" ) {
            public void actionPerformed( java.awt.event.ActionEvent e) {                                            
                EditorText.requestFocusInWindow();
                ReplaceForm.setReplaceType( ReplaceForm.REPLACE_SINGLE );
                ReplaceForm.setVisible( true );
            }
        };
        
        private javax.swing.Action ReplaceAllAction = new javax.swing.AbstractAction( "Replace All" ) {
            public void actionPerformed( java.awt.event.ActionEvent e) {                            
                EditorText.requestFocusInWindow();
                ReplaceForm.setReplaceType( ReplaceForm.REPLACE_ALL );
                ReplaceForm.setVisible( true );
            }
        };
        
        
        public javax.swing.Action getFindAction(){         
            return this.FindAction;
        }
        
        public javax.swing.Action getReplaceAction(){
            return this.ReplaceAction;
        }
        
        public javax.swing.Action getReplaceAllAction(){
            return this.ReplaceAllAction;
        }
        
        public javax.swing.Action getCutAction( ){
           return DocEditMan.getActionByName( javax.swing.text.DefaultEditorKit.cutAction );
        }
        
        public javax.swing.Action getCopyAction(){
            return DocEditMan.getActionByName( javax.swing.text.DefaultEditorKit.copyAction );
        }
        
        public javax.swing.Action getPasteAction(){
            return DocEditMan.getActionByName( javax.swing.text.DefaultEditorKit.pasteAction );
        }
        
        public javax.swing.Action getDeleteAction(){
            return DocEditMan.getActionByName( javax.swing.text.DefaultEditorKit.deleteNextWordAction );
        }
        
        public javax.swing.Action getUndoAction(){
            return DocEditMan.getActionByName( DocEditMan.Undo );
        }
        
        public javax.swing.Action getRedoAction(){
            return DocEditMan.getActionByName( DocEditMan.Redo );
        }
        
        
        private javax.swing.JEditorPane EditorText = null;
        private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.Editor.DocumentEditor Edit = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.Editor.DocumentEditor( EditorText );
        private org.edu.gces.s2005.projects.frontendformysql.ui.Forms.DocumentFindForm Findform = new org.edu.gces.s2005.projects.frontendformysql.ui.Forms.DocumentFindForm( null , Edit, EditorText );
        private org.edu.gces.s2005.projects.frontendformysql.ui.Forms.DocumentReplaceForm ReplaceForm = new org.edu.gces.s2005.projects.frontendformysql.ui.Forms.DocumentReplaceForm( null, Edit, EditorText );
        private org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.Edit.DocEditActionManager DocEditMan = null;        
    }

