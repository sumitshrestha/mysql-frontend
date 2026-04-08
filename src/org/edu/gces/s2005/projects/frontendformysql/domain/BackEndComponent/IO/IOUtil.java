
//                      !! RAM !!

/**
 * IOUtil.java
 *
 * Created on October 29, 2007, 9:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO;

/***
 *
 * @author sumit
 */
public class IOUtil {
    
     /**
     * Get the extension of a file.
     */  
    public static String getExtension( java.io.File f) {        
        String ext = getExtension( f.getName() );
        return ext;
    }

    public static String getExtension( final String Path ) {
        String ext = null;
        
        int i = Path.lastIndexOf('.');

        if (i > 0 &&  i < Path.length() - 1) {
            ext = Path.substring(i+1).toLowerCase();
        }
        
        return ext;
    }
}
