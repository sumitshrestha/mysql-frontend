
//                         !! RAM !!

/**
 * DocumentEditor.java
 *
 * Created on October 26, 2007, 8:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.Editor;

/***
 *
 * @author sumit
 */
public class DocumentEditor {
    
    /*** Creates a new instance of DocumentEditor */
    public DocumentEditor( javax.swing.JEditorPane Editor ) {
        this.EditText = Editor;
    }
    
    
    /***
    This function is for initializing the pointers for the find. 
    <br>  
    There is a pointer that will scan down the text as it finds the word in the text. The ptr will move down ie. increase the value till it reaches the last occurrence of the word where it will output that it has reached. This func will be called then to move back to the begginning.
    */
    
    public void initialize()
    {
      Pointer = 0;
    }
    
    /***
      This is the engine func for finding the word in the text.
      <br>  
      <br>  
       7-3-2007 12:00am
        <br>  
        here is a bug. i had a problem that the text field should be highlighted when the word to be find doesn't exists on the word. i did selected the word as a implementation here in this module but it worked for keystroke only and not for the button( find). i have got the solution that the textfield or the textarea to be highlighted should be in focus for that. so i m fixing this now here.
        <br>  
        <br>  
        <strong>
        note: BUG The func is not able to find the word that is in the 0th loc of the text. It has to be removed.
        </strong>
    */
    public String findText( String temp )
    {
     try{
          
          String buffer = EditText.getText();
          
          if( buffer.equals("") || temp.equals("")){
            // to do when the find is null
            return TEXT_NULL;
          }
          else{// begin finding
          buffer = "@"+ buffer;
          // adding @ at start of buffer since pointer doesnt starts at 0 but 1. see below.
          
          int position = buffer. indexOf( temp, Pointer + 1);
          
          if( position != -1 )
          {
            Pointer = position;
            return FOUND;
          }
          else
          {          
          if( Pointer == 0 )// text to find doesnt exists in the text
          {
                this.initialize();
                return NOT_FOUND;
          }
          else{ 
                this.initialize();
                return REACHED_LAST; 
          }     
          }// else ends
          }
          }
          catch( Exception except )
          {
            return "ERROR! "+except.getMessage();
          }
     
    }// func ends
    
    public int getPosition(){
        return this.Pointer;
    }
    
    
    
    /***    
    *This func will replace the word from selected text file the first word from Source field by that of the word from Destination field. ( as in 10-3-2007 4:46pm )
    
    @param Source source word to be replace
    @param Destination Destination word to be replace with
    @return bool true if the word is removed else false.
    */
    
    public final String replaceWord( String Source, String Destination )
    {       
      final String State = findText( Source );      
      if( State == this.FOUND ){
          // actually this selection is a technical problem since it can only replace selected word
         selectFoundWord(Source);
         EditText.replaceSelection( Destination );                  
      }      
          return State;      
    }// func ends

    private final void selectFoundWord(final String Source) {
        int position = getPosition();
        EditText.select( -- position , position + Source.length() );
    }
    
    
    /**
     * Func for replacing all the words by given word.
     * uses repalceword
     */
    public void replaceAll( String Source, String Destination ){
        while( this.replaceWord( Source, Destination ) == this.FOUND )
        { }
    }
    
     // field that holds the file
    private javax.swing.JEditorPane EditText;   
    private int Pointer;
    
    /**
     * some communication constants
     */
    public final static String REACHED_LAST = "The Pointer has reached last";
    public final static String NOT_FOUND = "The word to find doesnt exists";
    public final static String FOUND = "The word was found";
    public final static String TEXT_NULL ="text is null";
    
}
