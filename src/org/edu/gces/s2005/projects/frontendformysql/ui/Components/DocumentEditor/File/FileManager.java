
//                       !! RAM !!


package org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.File;


/**
 * @author sumit shrestha
 */
public class FileManager{
        
        public FileManager( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOManager IOManager, javax.swing.JEditorPane EditorText ){
            this.EditorText = EditorText;
            this.IOManager = IOManager;
        }
        
        public void open(){
            
            java.io.File F = this.View.showOpenDialog();
            
            if( F != null && F.isFile() ){
                this.Path = F.getPath();                
                this.ReaderThread.execute();                 
            }
        }
        
        public void save(){
            java.io.File F = this.View.showSaveDialog();
            
            if( F != null ){
                this.Path = F.getPath();                
                this.WriterThread.execute();
            }            
            else
                System.out.println("problem in Dialog" );
        }       
        
        private org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.File.FileChooser View = new org.edu.gces.s2005.projects.frontendformysql.ui.Components.DocumentEditor.File.FileChooser( "sql" );
        private String Path = null;
        private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOManager IOManager = null; 
        private javax.swing.JEditorPane EditorText = null;
        /** 
         * Background worker for Opening file
         */
        javax.swing.SwingWorker ReaderThread = new javax.swing.SwingWorker< String , java.lang.Void >() {    
            
            public java.lang.String doInBackground() {          
                return IOManager.read( Path );            
            }
             
            public void done() {                   
                try{
                    java.lang.String State = get();
                    
                    if( State  == IOManager.DONE ){                    
                        java.lang.StringBuffer Buffer = IOManager.getBuffer();                        
                        try{
                        java.io.StringReader Reader = new java.io.StringReader( Buffer.toString() ) ;                         
                        EditorText.read( Reader, null );
                        }
                        catch( Exception e ){
                            System.out.println( e );
                        }
                    }
                    else
                        javax.swing.JOptionPane.showMessageDialog( null, State ,"Reading Error", javax.swing.JOptionPane.ERROR_MESSAGE );
                    
                }
                catch( Exception e ){
                    
                }
            }            
            };
        
        /** 
         * Background worker for Saving file
         */
        javax.swing.SwingWorker WriterThread = new javax.swing.SwingWorker< String , java.lang.Void >() {    
            
            public java.lang.String doInBackground() {          
                IOManager.setBuffer( new java.lang.StringBuffer ( EditorText.getText() ) );
                return IOManager.save( Path );
            }
            
            public void done() {                   
                try{
                    java.lang.String State = get();
                    if( State != IOManager.DONE )
                    javax.swing.JOptionPane.showMessageDialog( null, org.edu.gces.s2005.projects.frontendformysql.ui.Components.DisplayMessageParser.returnParsedMessage( State ), "Writing Error", javax.swing.JOptionPane.ERROR_MESSAGE );                                            
                }
                catch( Exception e ){
                    
                }
            }            
            };
    }
