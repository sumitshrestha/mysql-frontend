
//                              !! RAM !!

/**
 * Foreign_key.java
 *
 * Created on October 15, 2007, 9:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData;

/***
 *
 * @author sumit
 */
public class Foreign_key extends org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute implements org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ForeignKeyInterface {
    
    /*** Creates a new instance of Foreign_key */
    public Foreign_key( String Name ) {
        super.setName( Name );
    }
    /**
     * mutators
     */
    public void setPK_Name( String Pk ){
        this.Pk_Name = Pk;
    }
    
    public void setPk_Database( String db ){
        this.Pk_Db = db;
    }
    
    public void setPk_Table( String Tb ){
        this.Pk_Tb  = Tb;
    }
    
    public void setUpdate_Rule( String r ){
        this.Update_Rule = r;
    }
    
    public void setDelete_Rule( String r ){
        this.Delete_Rule = r;
    }
    
    public void setDeferability_Rule( String r){
        this.Deferability = r;
    }
    
    /**
     * non mutators
     */
    public String getAttKey()
    {
        return FOREIGN_KEY;
    }
    
    public String getPK_Name(){
        return Pk_Name;
    }
    
    public String getPk_Database(){
        return this.Pk_Db;
    }
    
    public String getPk_Table(){
        return this.Pk_Tb;
    }
    
    public String getUpdate_Rule(){
        return this.Update_Rule;
    }
    
    public String getDelete_Rule(){
        return this.Delete_Rule;
    }
    
    public String getDeferability_Rule(){
        return this.Deferability;
    }
    
    private String Pk_Name = null;
    private String Pk_Db = null;
    private String Pk_Tb = null;
    private String Update_Rule = null;
    private String Delete_Rule = null;
    private String Deferability = null;
    
    // some foreing key constants    
    public final static String importedKeyCascade ="CASCADE";
    public final static String importedKeySetNull = "SET NULL";
    public final static String importedKeySetDefault = "SET DEFAULT"; 
    public final static String importedKeyRestrict = "RESTRICT";
    public final static String importedKeyNoAction = "NO ACTION";
    public final static String importedKeyInitiallyDeferred = "INITIALLY DEFERRED";
    public final static String importedKeyInitiallyImmediate = "INITIALLY IMMEDIATE";
    public final static String importedKeyNotDeferrable = "NOT DEFERRABLE";
            
}
