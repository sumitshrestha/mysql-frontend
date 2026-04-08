
//                      !! RAM !!


package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule;

/**
 * This class will be database of drivers. it will use xml file as specified in system to maintain info on drivers.
 *
 * @author Sumit Shrestha
 */
public class DriverXMLDatabase implements org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DriverListener{
    
    public DriverXMLDatabase(){        
            try{
            javax.xml.parsers.DocumentBuilderFactory Fact = javax.xml.parsers.DocumentBuilderFactory.newInstance();            
            javax.xml.parsers.DocumentBuilder Builder = Fact.newDocumentBuilder();            
            DriverInfo = Builder.parse( new java.io.File( absDriverInfoPath ) );  
            }
            catch( Exception e ){
                // do nothing
            }
    }
    
    public void onDriverLosd(){        
    }
    
    public void onDriverAddition( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver newDrv ){        
        try{
            org.w3c.dom.Element AddedDriverNode = DriverInfo.createElement( "Driver" );
            
            AddedDriverNode.setAttribute( "DriverName", newDrv.getDriverName());
            
            AddedDriverNode.setAttribute( "DriverJarFileName", newDrv.getDriverJarFileName() );
            
            AddedDriverNode.setAttribute( "DriverID", "_"+newDrv.getDriverID());
            
            AddedDriverNode.setAttribute( "DatabaseServerName", newDrv.getDatabaseServerName() );
            
            AddedDriverNode.setAttribute( "DatabaseServerURL", newDrv.getDatabaseServerURL() );
            
            DriverInfo.getDocumentElement().appendChild( AddedDriverNode );
            
            updateXMLDatabase( );            
        }
        catch( Exception e ){
            // do nothing
        }
    }
    
    public void onDriverServerNameRename( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver renamedDriver ){
        try{
            org.w3c.dom.Element Driver = this.getDriverNode( renamedDriver.getDriverID() );            
            if( Driver != null ){
                Driver.setAttribute( "DatabaseServerName", renamedDriver.getDatabaseServerName() );
                updateXMLDatabase( );            
            }
        }
        catch( Exception e ){
            // co nothing
        }
    }
    
    public void onDriverServerURLRename( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver renamedDriver ){
        try{
            org.w3c.dom.Element Driver = this.getDriverNode( renamedDriver.getDriverID() );            
            if( Driver != null ){
                Driver.setAttribute( "DatabaseServerURL", renamedDriver.getDatabaseServerURL() );
                updateXMLDatabase( );            
            }
        }
        catch( Exception e ){
            // co nothing
            //System.out.println( e.getMessage() );
        }
    }
    
    public void onDriverDelete( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver DeletedDriver){
        try{
            org.w3c.dom.Element Driver = this.getDriverNode( DeletedDriver.getDriverID() );            
            if( Driver != null ){
                org.w3c.dom.Node parent = Driver.getParentNode();
                parent.removeChild( Driver );
                updateXMLDatabase( );            
            }
        }
        catch( Exception e ){
            // co nothing
            //System.out.println( e.getMessage() );
        }
    }
     
    public void onDefaultDriverChanged( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver NewDefaultDriver, org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver OldDefaultDriver ){
        try{
            if( NewDefaultDriver == null )
                return;
            
            String DriverID = NewDefaultDriver.getDriverID();
            if( ! DriverID.startsWith("_") )
                DriverID = "_" + DriverID;
            
            this.DriverInfo.getDocumentElement().setAttribute( "DefaultDriver", DriverID);
            
            this.updateXMLDatabase();
        }
        catch( Exception e ){
            System.out.println( e.getMessage() );
        }
    }
    
    /**
     * This method will give the driver node of corressponding driver id sent from parma.
     * 
     * @param DriverID Driver ID to be found
     * 
     * @return Driver Node as element if it is found else null
     */
    private org.w3c.dom.Element getDriverNode( String DriverID ){
        if( this.DriverInfo == null || DriverID == null )
            return null;
        
        if( ! DriverID.startsWith("_"))
            DriverID = "_" + DriverID;
        
        if( this.DriverInfo.getDocumentElement().hasChildNodes() ){
            org.w3c.dom.Node temp = this.DriverInfo.getDocumentElement().getFirstChild();
            
            while( temp != null ){
                try{
                    if( this.isDriver(temp, DriverID ) ){
                        return (org.w3c.dom.Element) temp;
                    }                                            
                }
                catch( Exception e ){
              //      System.out.println( e.getMessage() ); // temp code
                }
                finally{
                    temp = temp.getNextSibling();
                }
            }
            return null;
        }
        else
            return null; // no drivers yet
    }
    
    private boolean isDriver( org.w3c.dom.Node temp, final String DriverID ){
        org.w3c.dom.Element Driver = (org.w3c.dom.Element) temp;
        return Driver.getAttribute( "DriverID" ).equals( DriverID );                
    }
    
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.XMLutil.XMLWriter DriverFileWriter = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.XMLutil.XMLWriter();    
    private final String absDriverInfoPath = org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInformationProvider.getAbsolutePathOfDefaultFolder() + java.io.File.separatorChar +org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInformationProvider.getDriverFilePath();
    org.w3c.dom.Document DriverInfo = null;

    private void updateXMLDatabase() {

        this.DriverFileWriter.write(DriverInfo, absDriverInfoPath);
    }
}
