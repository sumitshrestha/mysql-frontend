
//                     !! RAM !!

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO;

/**
 *
 * @author sumit
 */
public class IOManager {
    
    /*** Creates a new instance of IOManager */
    public IOManager() {
    }
    
    
    /**
     * This method gets the file name of absolute path supplied as parameter.
     */
    public static String getFileName( String absPath ){
        try{
            char pathSeparator = java.io.File.separatorChar;
            int index = absPath.lastIndexOf( pathSeparator );
            if( index != -1 )
                return absPath.substring( index + 1 );
            else
                return null;
        }
        catch( Exception e ){
            return null;
        }
    }
    
    public String read( String Path ){
        try{
    
        if( this.Buffer == null )
            this.Buffer = new java.lang.StringBuffer();
        
        java.io.BufferedReader in = new java.io.BufferedReader( new java.io.FileReader( Path ) );
    
        char[] buffer = new char[ MaxBufferLen ];
    
        int pos=0; // pointer to current file position
    
        int readed = -1;
        
        do{       
            readed = in.read( buffer, 0, buffer.length );
    
            if( readed != -1)
            this.Buffer.append( buffer, 0, readed );
            
            pos += readed;
            
            in.mark( pos );    
            }
            while( readed != -1 );
   
            in.close();
            
            return DONE;
        }
         catch( Exception except ){               
             return except.getMessage();            
         }
    }
            
    
    
    public String save( String Path ){
        try{
            String ext = org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOUtil.getExtension( Path );
            
            if( ext == null || !( ext.equalsIgnoreCase(this.DefaultExtension ) ) ){
                Path = this.addDefaultExtension( Path, ext );
            }
            
            java.io.FileOutputStream op = new java.io.FileOutputStream( Path );
            
            op.write( this.Buffer.toString().getBytes() );
            
            op.close();
            
            return this.DONE;
        }
        catch( Exception e ){
            return e.getMessage();
        }
    }
    
    /**
     * This method will save the contents of source file to destination file.
     * If Destination file exists then it will overwrite it.
     * it uses buffered technology to save multiple access n hence avoids slowness
     * 
     * @param SourceFile source file to copied
     * @param DestinationFile Destination file where the file is to copied.
     * 
     * @return DONE if file copied else error message
     */
    public final String save( java.io.File SourceFile, java.io.File DestinationFile ){
        try{
	 byte[] buffer = new byte[ this.MaxBufferLen ];
         
	 java.io.BufferedInputStream buffIp = new java.io.BufferedInputStream( new java.io.FileInputStream( SourceFile )  );
	 
	 java.io.BufferedOutputStream op = new java.io.BufferedOutputStream(  new java.io.FileOutputStream( DestinationFile ) );
	 
         int read = 0;
	 while( ( read = buffIp.read( buffer,0, buffer.length ) ) != -1 ){
	 op.write( buffer,0, read );
	 }
         
	 op.close();
	 buffIp.close();
         
         return DONE;
        }
        catch( Exception e ){
            return e.getMessage();
        }
    }
    
    public java.lang.StringBuffer getBuffer(){
        return this.Buffer;
    }
        
    public void setBuffer( java.lang.StringBuffer Buffer ){
        this.Buffer = Buffer;
    }
    
    public void setDefaultExtension( final String Ext ){
        this.DefaultExtension = Ext;
    }
    
    private final String addDefaultExtension( String Path, String ext ){
        if( ext == null )
        return Path + '.' + this.DefaultExtension;
        else{
            int i = Path.lastIndexOf( '.' );
            return Path.substring( 0, i) + "." + this.DefaultExtension;
        }
    }
    
    public final boolean deleteFile( String Path ){
        try{
            java.io.File delFile = new java.io.File( Path );
            if( delFile.exists() && delFile.isFile() ){
                return delFile.delete();                                
            }
            return false;
        }
        catch ( Exception e ){
            System.out.println("Cannot Delete file..." + e.getMessage() );
            return false;
        }
    }
        
    public final static String DONE = "Done";
    private java.lang.StringBuffer Buffer = new java.lang.StringBuffer("") ;
    private int MaxBufferLen = 4096;
    
    private String DefaultExtension = "sql";
}
