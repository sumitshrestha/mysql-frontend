
//                     !! RAM !!

/**
 * FileChooser.java
 *
 * Created on October 29, 2007, 4:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.File;

/***
 *
 * @author Sumit Shrestha
 */
public class FileChooser extends javax.swing.JFileChooser {
    
    /*** Creates a new instance of FileChooser */
    public FileChooser( final String Type ) {        
        super.setMultiSelectionEnabled( false );
        super.setFileFilter( new org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.File.FileChooserFilter( Type ) );
    }
    
    public java.io.File showOpenDialog(){
        int State =  super.showOpenDialog( null );
        if( State == super.APPROVE_OPTION )
        {
            return super.getSelectedFile();
        }
        else
            return null;
    }
    
    public java.io.File showSaveDialog(){
        int State = super.showSaveDialog( null );
        if( State == super.APPROVE_OPTION ){
            return super.getSelectedFile();
        }
        else
            return null;
    }
        
}

class FileChooserFilter extends javax.swing.filechooser.FileFilter {
    
    public FileChooserFilter( final String Type ){
        this.FilterType = Type;
    }
    
    public boolean accept( java.io.File File ){
        if( File.isDirectory() )
        return true;
        else{
            final String ext = org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOUtil.getExtension( File );
            return this.isValidExtension( ext );
        }
    }
    
    public final boolean isValidExtension( String ext ){
        if( ext != null )
        return ext.equalsIgnoreCase( this.FilterType );    
        return false;
    }
    
    public String getDescription(){
        return this.FilterType + " Files";
    }
    
    private String FilterType = null;
}// filter class ends
