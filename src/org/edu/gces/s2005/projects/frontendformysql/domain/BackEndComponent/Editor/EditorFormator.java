
//                      !! RAM !!

/**
 * EditorFormator.java
 *
 * Created on October 27, 2007, 4:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.Editor;


/***
 *
 * @author sumit
 */
public class EditorFormator extends javax.swing.text.DocumentFilter {
    
    /*** Creates a new instance of EditorFormator */
    public EditorFormator() {
        
    }
    
    public void insertString ( javax.swing.text.DocumentFilter.FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException{
        //String s = fb.getDocument().getText( offset, string.length() );
        //System.out.println( s );
        
    }
    
    public void replace( javax.swing.text.DocumentFilter.FilterBypass fb, int offset, int length, String str, javax.swing.text.AttributeSet a) throws javax.swing.text.BadLocationException {
        try{
            String past = fb.getDocument().getText( offset -1 ,1);
            if( ( str.equals(" ") || str.equals("\n")|| str.equals("\t")) && !( past.equals(" ")|| past.equals("\t") || past.equals("\n"))  )
            {
            System.out.println("word typed");            
            }
            //javax.swing.text.DefaultStyledDocument stydoc = (javax.swing.text.DefaultStyledDocument)fb.getDocument();
            
        }
        catch( Exception e){
            System.out.println(e);
        }
        super.replace(fb, offset, length, str, a);
    }
    
}
