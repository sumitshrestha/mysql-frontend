
//                                   !! RAM !!


package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule;

import java.sql.Driver;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * This class is 
 * @author Sumit Shrestha
 */
public class DriverManager implements org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.DriverManagerInterface {

    private static final Logger LOG = LoggerFactory.getLogger(DriverManager.class);
    
    public DriverManager( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOManager IOMan ){
        
        this.Downloader = IOMan;
        
        this.initializeDriverLibrary();
                 
        this.DriverLoader = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.JDBCDriverLoader( DriverLibrary );
                
        this.initializeDriverInfo();
        
        // xml database is made the listener of driver manager after all the drivers in database is loaded by system.
        this.registerDriverListener( Database );
    }
    
    /**
     * This method will perform the initialization tasks of the driver information. It includes initializing driver library.
     */
    private void initializeDriverLibrary(){
        try{
        // initialize driver library by current path in absolute form            
            final String absPath = org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInformationProvider.getAbsolutePathOfDefaultFolder();
            if( absPath != null )
            this.DriverLibrary = absPath + java.io.File.separatorChar + org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInformationDatabase.SystemLibraryName + java.io.File.separatorChar + org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInformationDatabase.DriverLibraryName + java.io.File.separatorChar ;
            else
                this.DriverLibrary = "";
        }
        catch( Exception e ){
            this.DriverLibrary = "";            
        }
    }
    
    /**
     * This is temp code for initialzizing driver info. it uses dom method for parsing xml conf file of driver.
     */
    private void initializeDriverInfo(){
        try{
        final String absDriverInfoPath = org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInformationProvider.getAbsolutePathOfDefaultFolder() + java.io.File.separatorChar +org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.SystemInformationProvider.getDriverFilePath();

        java.io.File driverInfoFile = new java.io.File( absDriverInfoPath );
        if( !driverInfoFile.exists() || driverInfoFile.length() == 0 ){
            org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOManager man = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOManager();
            man.setDefaultExtension( "xml" );
            man.setBuffer( new java.lang.StringBuffer( org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd.System.InitialDriverInfo.Info ) );
            man.save( absDriverInfoPath );
        }
        
        java.io.InputStream ip = new java.io.FileInputStream( absDriverInfoPath );
        
        org.xml.sax.helpers.DefaultHandler SaxHandler = new SAXInitializer();
        
        javax.xml.parsers.SAXParserFactory SaxFactory = javax.xml.parsers.SAXParserFactory.newInstance();
        
        javax.xml.parsers.SAXParser SaxParser = SaxFactory.newSAXParser();
        
        SaxParser.parse(ip, SaxHandler );
        ip.close();
        
        }
        catch( Exception e ){
            LOG.error( "message while reading sax file...{}", e.getMessage(), e );
        }
    }
            
    public boolean closeConnection(){
        
        try{
            
            this.onSuccess();
            
            if( this.Conn == null )// not even connected once ie. at start
                return true;
            else{
                this.Conn.close();
                return true;
            }
        }
        catch( java.sql.SQLException e ){
            this.setErrorMessage( e.getMessage() );
            return false;
        }
    }
    
    public boolean isConnected(){
        try{
            this.onSuccess();
            return ! Conn.isClosed();
        }
        catch( java.sql.SQLException e ){
            this.setErrorMessage( e.getMessage() );
            return false;
        }
    }

    public java.sql.DatabaseMetaData makeConnection( String username, char[] password ){
        try{
            
            if( username == null || password == null ){ // usernamne or password is null
                this.setErrorMessage( "Either username or password is null" );
                return null;
            }
            
            if( username.equals("") ){
                this.setErrorMessage( "username is left empty" );
                return null;
            }
            
            if( this.DefaultDriver == null ){
                this.setErrorMessage( "Default Driver is not selected or not yet loaded. please go to driver manager to select any" );
                return null;
            }
            
            String DbURL = this.DefaultDriver.getDatabaseServerURL();// + this.DefaultDriver.getDatabaseServerPortNo() + "/";
            DbURL = this.normalizeMySqlJdbcUrl( DbURL );
            
            if( DbURL == null ){
                this.setErrorMessage( "Database Server URL or Port No is null. \n Pleanse Use Driver Manage to Set empty value." );
                return null; // URL was null
            }

            if( !this.isValidMySqlJdbcUrl( DbURL ) ){
                this.setErrorMessage( "Invalid JDBC URL. Use format: jdbc:mysql://host:3306/database" );
                LOG.warn( "Rejected invalid JDBC URL '{}'", DbURL );
                return null;
            }

            this.DefaultDriver.setDatabaseServerURL( DbURL );
                        
            if( this.DefaultDriver.getDriver() == null ){
                this.setErrorMessage( "Drivar is not yet loaded. \n please load any." );
                return null;
            }   

            LOG.info(
                "Attempting DB connection: driver='{}', url='{}', user='{}', passwordLength={}",
                this.DefaultDriver.getDriverName(),
                DbURL,
                username,
                Integer.valueOf( password.length )
            );

            if( !this.preflightSocketCheck( DbURL ) ){
                this.setErrorMessage( "Cannot reach MySQL server. Check host/port and server status." );
                return null;
            }
            
            if( DefaultDriver.getDriver().acceptsURL(DbURL) ){
                
                java.util.Properties DbProp = new java.util.Properties();
                DbProp.setProperty("user", username );
                DbProp.setProperty("password", String.valueOf( password) );
                
                this.Conn = this.DefaultDriver.getDriver().connect(DbURL, DbProp);                                        
            
                this.onSuccess();
            
                return this.Conn.getMetaData();
            }
            else{
                this.setErrorMessage( "Driver did not accepted Database Server URL" );
                LOG.warn( "Driver '{}' rejected URL '{}'", this.DefaultDriver.getDriverName(), DbURL );
                return null;
            }                
        }
        catch( Exception e ){
            this.setErrorMessage( e.getMessage() );
            LOG.error( "Error while making database connection", e );
            return null;
        }
    }

    private String normalizeMySqlJdbcUrl( String DbURL ){
        if( DbURL == null )
            return null;

        String normalized = DbURL.trim();

        if( normalized.equals( "jdbc:mysql://localhost" ) )
            return "jdbc:mysql://localhost:3306/mysql";

        if( normalized.matches( "^jdbc:mysql://[^/:?]+$" ) )
            return normalized + ":3306/mysql";

        if( normalized.matches( "^jdbc:mysql://[^/:?]+:[0-9]+$" ) )
            return normalized + "/mysql";

        return normalized;
    }

    private boolean isValidMySqlJdbcUrl( String DbURL ){
        if( DbURL == null )
            return false;

        return DbURL.matches( "^jdbc:mysql://[^/:?]+:[0-9]+/[^?]+(\\?.*)?$" );
    }

    private boolean preflightSocketCheck( String DbURL ){
        try{
            java.net.URI uri = new java.net.URI( DbURL.substring( 5 ) ); // strip "jdbc:"
            String host = uri.getHost();
            int port = uri.getPort() == -1 ? 3306 : uri.getPort();

            if( host == null || host.equals( "" ) ){
                LOG.warn( "Cannot preflight socket: host not found in URL '{}'", DbURL );
                return false;
            }

            Socket socket = new Socket();
            try{
                socket.connect( new InetSocketAddress( host, port ), 2000 );
                LOG.info( "Socket preflight succeeded: {}:{}", host, Integer.valueOf( port ) );
                return true;
            }
            finally{
                try{
                    socket.close();
                }
                catch( Exception e ){
                    // ignore close failure
                }
            }
        }
        catch( Exception e ){
            LOG.warn( "Socket preflight failed for URL '{}': {}", DbURL, e.getMessage() );
            return false;
        }
    }
        
        
    public boolean setDefaultDriver( String DriverID  ){
        
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver drv = this.getDriverByID(DriverID);
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver ReplaceDrv = this.getDefaultDriver();
               
        if( drv == null ){
        this.setErrorMessage("driver doesnt exists");
        return false;
        }
        
        this.onSuccess();
        this.DefaultDriver = drv;

        this.notifyDefaultDriverSetting( ReplaceDrv );
        return true;
    }
    
    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver getDefaultDriver(){
        return this.DefaultDriver;
    }
    
    private final String getDriverFileAbsPath( final String DriverJarFileName ){
        return this.DriverLibrary + DriverJarFileName;
    }
    
    /**
     * This method will add driver as specified in its interface specificaion.
     * The method will go through following procedures
     * 1. download the jar file using DriverURL to local directory
     * 2. test the validity of driver name
     * 3. update driver database so that it will be used later to connect using it
     */
    public java.sql.Driver addDriver( String DriverName, String absPath ){
        try{
            
            final String DriverJarFileName = this.Downloader.getFileName(absPath);
        // 1. download                        
        final String mess = this.Downloader.save( new java.io.File( absPath ), new java.io.File ( this.getDriverFileAbsPath(DriverJarFileName) ) );        
        
        if( mess != this.Downloader.DONE ){
            this.setErrorMessage( mess );
            return null;
        }
            return loadDriver(DriverJarFileName, DriverName);
        }
        catch( Exception e ){
            this.setErrorMessage( e.getMessage() );
            return null;
        }
    }
    
    public final String getRecentlyAddedDriverID(){
        return this.RecentlyAddedDriver.getDriverID();
    }

    public org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver[] getLoadedDriver(){
        return this.DriverList.toArray( new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver[]{} );
    }
    
    /**
     * This is the private method for loading driver from the driver directory. 
     * 
     * @param DriverJarFileName name of driver jar file from where to load
     * @param DriverName name of driver class to be loaded 
     * 
     * @return Diver object being loaded
     */
    private Driver loadDriver(final String DriverJarFileName, String DriverName) {
        try{
        
        // Prefer the current MySQL driver class if the legacy class name is provided.
        if( "com.mysql.jdbc.Driver".equals( DriverName ) )
            DriverName = "com.mysql.cj.jdbc.Driver";

        java.sql.Driver d = this.DriverLoader.loadDriver(DriverName, DriverJarFileName);

        if (d == null) {
            // driver was invalid
            this.setErrorMessage(this.DriverLoader.getErrorMessage());
            return null;
        }

        if (this.addDrivertoDatabase(d, DriverName, DriverJarFileName)) {
            return d;
        } else {
            return null;
        }
        
        }
        catch( Exception e ){
            this.setErrorMessage( e.getMessage() );
            return null;
        }
    }
    
    private boolean setRecentlyAddedDriver( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver RecentDrv ){
        if( RecentDrv == null ){
            this.setErrorMessage( "Recently Added Driver was found to be null" );
            return false;
        }
        else{
            this.RecentlyAddedDriver = RecentDrv;
            return true;
        }
    }
    
    public boolean deleteDriver( String DriverID  ){
        try{
            org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver driver = this.getDriverByID(DriverID);
            
            if( driver == null ) // driver not present
                return false;
            
            if( this.DriverList.remove(driver) ){                
                                
                if( this.DefaultDriver == driver )// check if this driver is also default then remove
                    this.DefaultDriver = null;
                
                // remove the loaded driver obj
                driver.setDriver( null );
                
                this.RecentlyAddedDriver = null;
                
                //System.out.println("The recently added driver is " + this.RecentlyAddedDriver.getDriverName());
                
                // garbage collect
                System.gc();
                                        
                // delete the driver file 
                if( ! this.Downloader.deleteFile( this.getDriverFileAbsPath( driver.getDriverJarFileName() ) ) )
                    System.out.println( "cannot delete file..." );
                
                this.notifyDriverDeletion( driver );
                return true;
            }               
            else
                return false;
        }
        catch( Exception e ){
            this.setErrorMessage( e.getMessage() );
            return false;
        }
    }
    
    /**
     * This method will add driver to the Database.
     * 
     * 
     * @param d name of driver to be added
     * 
     * @return true if added else false
     */
    private boolean addDrivertoDatabase( java.sql.Driver d, String DriverName, String DriverJarFileName ){
        
        if( DriverName == null || DriverJarFileName == null ){
            this.setErrorMessage( "Either DriverName or Driver File Name are empty" );
            return false;
        }
        
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver newDrv = new org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver( DriverName, DriverJarFileName );
        
        newDrv.setDriver( d );
        
        if( this.isAlreadyLoaded(newDrv)){
            this.setErrorMessage( "Driver is already added. Please specify another Driver" );
            return false;
        }
        
        this.setDriverID(newDrv);
        
        this.DriverList.add( newDrv );
        
        this.onSuccess();
        
        if( ! this.setRecentlyAddedDriver( newDrv ) )
            return false;
        
        this.notifyDriverAddition();
        
        return true;
    }
    
    
    /**
     * This method sets driver ID of newly created driver to be added to dataabase. This method must be called after creating driver n before any other driver is created and added. so, it can be called after or before adding to database list. it checks if it exists in library or not.
     * 
     * @param newDrv Driver object whose ID is to be set.
     * 
     * @return true if drivets ID is set else false.
     */
    private boolean setDriverID( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver newDrv ){
        if( newDrv == null || newDrv.getDriverID() != null ){            
            return false;
        }
        
        if( this.DriverList.contains( newDrv ))
            newDrv.setDriverID( String.valueOf( this.DriverList.size() - 1 ) );
        else
            newDrv.setDriverID( String.valueOf( this.DriverList.size() ) );
        
        return true;
    }
    
    /**
     * This is an internal method for checking if the driver to added already present or not.
     * 
     * @param Drv driver object to be checked.
     * @returns true if present else false.
     */
    private boolean isAlreadyLoaded( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver Drv ){
       for( int i=0;i< this.DriverList.size();i++){
           if( this.equals(Drv, this.DriverList.get(i) ) )
               return true;
       } 
       return false;
    }
    
    /**
     * This is an internal method for checking equality of two driver object
     * 
     * @param Drv1 First driver to be checked
     * @param Drv2 Second driver to be checked
     * 
     * @returns true if they are equal else false
     */
    private boolean equals( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver Drv1, org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver Drv2 ){
        
        if( Drv1 == Drv2) // check mem
            return true;
        
        java.sql.Driver Drv11 = Drv1.getDriver();
        java.sql.Driver Drv22 = Drv2.getDriver();
        
        if( ! this.equals( Drv11, Drv22 ) )
            return false;
        
        if( ! Drv1.getDriverName().equals( Drv2.getDriverName() ))
            return false;
        
        if( ! Drv1.getDriverJarFileName().equals( Drv2.getDriverJarFileName() ))
            return false;        
        
        return true;
    }
    
    
    /**
     * This method will check if the two drivers are logically equivalent or not.
     * 
     * @param Drv1 Driver 1 to checked
     * @param Drv2 driver 2 to checked
     * 
     * @returns true if equal else false
     */
    private boolean equals( java.sql.Driver Drv1, java.sql.Driver Drv2 ){
        
        if( Drv1 == null || Drv2 == null ) // any one of driver is null
            return false;
        
        if( Drv1 == Drv2) // check mem
            return true;
        
        // check if both jdbc compliantiancy is equal or not
        if( Drv1.jdbcCompliant() != Drv2.jdbcCompliant() )
            return false;
        
        // check major version
        if( Drv1.getMajorVersion() != Drv2.getMajorVersion() )
            return false;
        
        // check minor version
        if( Drv1.getMinorVersion() != Drv2.getMinorVersion() )
            return false;
        
        return true;
    }
    
    public String getErrorMessage(){
        return this.errorMessage;
    }
    
    private void onSuccess(){
        this.errorMessage = null;
    }
    
    private void setErrorMessage( String Mess ){
        this.errorMessage = Mess;
    }
    
    /**
     * This method returs driver object for corressponding driver naem else null
     */
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver getDriver( String DriverName ){
        for( int i=0; i< this.DriverList.size(); i++)
            if( this.DriverList.get(i).getDriverName().equals( DriverName ) )
                return this.DriverList.get(i);
        return null;
    }
    
    
    /**
     * This method returns the Driver object of ID as sent as specified by parameter
     * 
     * @param DriverID ID of Driver as set interanlly
     * 
     * @returns Driver Object of ID if ID is proper else null
     */
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver getDriverByID( String DriverID ){        
        if( DriverID == null )
            return null;
        
        if( DriverID.startsWith("_") ){
            DriverID = DriverID.substring( 1 );
        }        
            try{
                int index = java.lang.Integer.parseInt(DriverID);
                if( index == -1 )
                    return null;
                else
                    return this.DriverList.get(index);
            }
            catch( Exception e ){
                return null;
            }        
    }
    
    public boolean registerDriverListener( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DriverListener DrvList ){
        if( DrvList == null ){
            this.setErrorMessage( " Listener Object is null" );
            return false;
        }            
        else{
            this.ListenerList.add( DrvList );
            this.onSuccess();
            return true;
        }
    }
    
    
    /**
     * This method will used to notify the listeners about any driver additon that happens.
     * ir will implicitly take added driver object from recentlyaddedDriver so it must be called after drivers are added to database.
     */
    private void notifyDriverAddition(){
        for( int i=0; i< this.ListenerList.size(); i++ )
            this.ListenerList.get(i).onDriverAddition( RecentlyAddedDriver );
    }
    
    private void notifyDriverServerNameRename( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver renamedDriver ){
        for( int i=0; i< this.ListenerList.size(); i++ )
            this.ListenerList.get(i).onDriverServerNameRename(renamedDriver);
    }
    
    private void notifyDriverServerURLRename( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver renamedDriver ){
        for( int i=0; i< this.ListenerList.size(); i++ )
            this.ListenerList.get(i).onDriverServerURLRename(renamedDriver);
    }
    
    private void notifyDriverDeletion( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver deletedDriver ){
        for( int i=0; i< this.ListenerList.size(); i++ )
            this.ListenerList.get(i).onDriverDelete( deletedDriver );
    }
    
    private void notifyDefaultDriverSetting( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver OldDriver ){
        for( int i = 0;i< this.ListenerList.size(); i++ )
            this.ListenerList.get(i).onDefaultDriverChanged( this.getDefaultDriver(), OldDriver );
    }
    
    /////////////// DRIVER MODIFICATOIN METHODS STARTS //////////////////////////////////////////////////////
     
    public boolean renameDriverServerName( String DriverID, String DriverServerName ){
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver drv = this.getDriverByID(DriverID);
        
        if( drv == null ){
        this.setErrorMessage("driver doesnt exists");
        return false;
        }
        
        this.onSuccess();
        drv.setDatabaseServerName( DriverServerName );
        this.notifyDriverServerNameRename( drv );
        return true;
    }
    
    public boolean renameDriverServerURL( String DriverID, String URL ){
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver drv = this.getDriverByID(DriverID);
        
        if( drv == null ){
        this.setErrorMessage("driver doesnt exists");
        return false;
        }
        
        this.onSuccess();
        drv.setDatabaseServerURL(URL);
        this.notifyDriverServerURLRename( drv );
        return true;
    }
    
    public boolean setDriver( String DriverName, java.sql.Driver Drv ){
        org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver drv = this.getDriver(DriverName);
        
        if( drv == null ){
        this.setErrorMessage("driver doesnt exists");
        return false;
        }
        
        this.onSuccess();
        drv.setDriver( Drv );
        return true;
    }
    
    /////////////// DRIVER MODIFICATOIN METHODS ENDS //////////////////////////////////////////////////////
    
    
    /**
     * FIELDS
     */
    private java.sql.Connection Conn = null;    
    
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.DriverModule.JDBCDriverLoader DriverLoader = null;
    
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver DefaultDriver = null; // initially null
    
    private java.util.ArrayList < org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver > DriverList = new java.util.ArrayList < org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver >();
    
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.IO.IOManager Downloader = null; // initially null gets from backend
    
    // field for holding loacation of drivera    
    private String DriverLibrary = null;
    
    // fields for holding error message
    private String errorMessage = null;// initially no error
    
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Driver RecentlyAddedDriver = null;
    
    private java.util.ArrayList < org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DriverListener > ListenerList = new java.util.ArrayList < org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.DriverListener >();
    
    // database 
    private DriverXMLDatabase Database = new DriverXMLDatabase();
    
    
    /**
     * This is the inner class to initialize the driver manager class. It is innner class so it is made private to disallow access to outside world.
     * It uses SAX API for reading from the driver info xml file n initializing the driver manager. 
     */
    private class SAXInitializer extends org.xml.sax.helpers.DefaultHandler {
        
            public SAXInitializer(){            
            }
            
            public void startElement( String URI, String LocalName, String QualifiedName, org.xml.sax.Attributes Att ){
                if( QualifiedName.equals( this.DriverInfoName )){
                    if( Att == null ){
                        System.out.println( "attributes are null in driverinfo" );
                        return;
                    }          
                    this.DefaultDriver = Att.getValue( this.DefaultDriver );                    
                }
                else
                    if( QualifiedName.equals( this.DriverNode )){
                        if( Att == null ){
                        System.out.println( "attributes are null in driver" );
                        return;
                        }
                        
                        String DriverID = Att.getValue( "DriverID" );
                        if( DriverID.equals( "_-1" ))
                            return;
                        
                        String DriverName = Att.getValue( this.DriverName );
                        String DriverJarFileName = Att.getValue( this.DriverJarFileName );
                                                
                        loadDriver( DriverJarFileName, DriverName );
                        renameDriverServerName( DriverID, Att.getValue( this.DatabaseServerName ));
                        renameDriverServerURL( DriverID, Att.getValue( this.DatabaseServerURL ));
                    }
            }
            
            
            /** 
             * this method will be called when all the drivers have been loaded so default driver is to be set
             */
            public void endDocument(){
                int index = java.lang.Integer.parseInt( this.DefaultDriver.substring( 1 ) );
                if( index != -1 )
                    setDefaultDriver( DriverList.get( index ).getDriverID() );
            }
            
            private String DriverNode = "Driver";
            private String DriverName = "DriverName";
            private String DriverInfoName = "DriverInfo";
            private String DefaultDriver = "DefaultDriver";
            private String DriverJarFileName = "DriverJarFileName";
            private String DatabaseServerName = "DatabaseServerName";
            private String DatabaseServerURL = "DatabaseServerURL";
        }

}
