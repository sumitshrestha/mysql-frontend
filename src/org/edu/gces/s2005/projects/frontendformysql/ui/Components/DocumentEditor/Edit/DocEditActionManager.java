
//                          !! RAM !!

/**
 * Temp.java
 *
 * Created on October 27, 2007, 1:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.Edit;


/***
 *
 * @author sumit
 */
public class DocEditActionManager {
    
    /*** Creates a new instance of Temp */
    public DocEditActionManager( javax.swing.JEditorPane Editor ) {
        this.Editor = Editor;
        this.createActionTable();
        
        this.undoAction = new org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.Edit.DocEditActionManager.UndoAction();        
        this.redoAction = new org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.Edit.DocEditActionManager.RedoAction();        
        this.Editor.getDocument().addUndoableEditListener( new MyUndoableEditListener() );
    }
    
    //The following two methods allow us to find an
    //action provided by the editor kit by its name.
    private void createActionTable( ){
        Actions = new java.util.HashMap<Object, javax.swing.Action>();
        javax.swing.Action[] actionsArray = this.Editor.getActions();
        for (int i = 0; i < actionsArray.length; i++) {
            javax.swing.Action a = actionsArray[i];            
            Actions.put(a.getValue( javax.swing.Action.NAME), a);
        }
    }

    public javax.swing.Action getActionByName(String name) {
        if( name == this.Redo )
            return this.redoAction;
        else
            if( name == this.Undo )
                return this.undoAction;
            else                
                return Actions.get(name);
    }

        class UndoAction extends javax.swing.AbstractAction {
        public UndoAction() {
            super("Undo");
            setEnabled(false);
        }

        public void actionPerformed( java.awt.event.ActionEvent e) {
            try {
                undo.undo();
            } catch ( javax.swing.undo.CannotUndoException ex) {
                System.out.println("Unable to undo: " + ex);
                ex.printStackTrace();
            }
            updateUndoState();
            redoAction.updateRedoState();
        }

        protected void updateUndoState() {
            if (undo.canUndo()) {
                setEnabled(true);
                putValue( javax.swing.Action.NAME, undo.getUndoPresentationName());
            } else {
                setEnabled(false);
                putValue( javax.swing.Action.NAME, "Undo");
            }
        }
    }

    class RedoAction extends javax.swing.AbstractAction {
        public RedoAction() {
            super("Redo");
            setEnabled(false);
        }

        public void actionPerformed( java.awt.event.ActionEvent e) {
            try {
                undo.redo();
            } catch (  javax.swing.undo.CannotRedoException ex) {
                System.out.println("Unable to redo: " + ex);
                ex.printStackTrace();
            }
            updateRedoState();
            undoAction.updateUndoState();
        }

        protected void updateRedoState() {
            if (undo.canRedo()) {
                setEnabled(true);
                putValue( javax.swing.Action.NAME, undo.getRedoPresentationName());
            } else {
                setEnabled(false);
                putValue( javax.swing.Action.NAME, "Redo");
            }
        }
    }

    
    //This one listens for edits that can be undone.
    protected class MyUndoableEditListener
                    implements javax.swing.event.UndoableEditListener {
        public void undoableEditHappened( javax.swing.event.UndoableEditEvent e) {
            //Remember the edit and update the menus.
            undo.addEdit(e.getEdit());
            undoAction.updateUndoState();
            redoAction.updateRedoState();
        }
    }

    
    private javax.swing.JEditorPane Editor = null;
    private java.util.HashMap<Object, javax.swing.Action> Actions;
        
        //undo helpers
    protected UndoAction undoAction;
    protected RedoAction redoAction;
    protected javax.swing.undo.UndoManager undo = new javax.swing.undo.UndoManager();
    
    
    /**
     * some edit constants
     */
    public static final String Undo = "Undo";
    public static final String Redo = "Redo";
}
