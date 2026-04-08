
//                         !! RAM !!

/**
 * ProcedureGenerator.java
 *
 * Created on October 17, 2007, 10:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEnd;

/***
 *
 * @author sumit
 */
public class ProcedureGenerator {
    
    /*** Creates a new instance of ProcedureGenerator */
    public ProcedureGenerator() {
    }
    
    public String generateProcedure( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ProcedureInterface DomProc ){
        
        String Proc = "CREATE PROCEDURE ";
        try{
        if( DomProc.getName().equals( "" ) || DomProc.getName() == null )
            return null;
        // else
        Proc += DomProc.getName();
        }
        catch( Exception e ){
            System.out.println( "error!! while adding name "+ e  );
            return null;
        }
        try{
        Proc = this.addParameter( Proc, DomProc.getParameters() );
        }
        catch( Exception e ){
            System.out.println( "error!! while adding parameters "+ e  );
        }
        
        try{
        Proc += " COMMENT '" + DomProc.getComment() + "' ";
        }
        catch( Exception e ){
            System.out.println( "error!! while adding comment "+ e  );
        }
        
        try{
        Proc = this.addOperation( Proc , DomProc );
        }
        catch( Exception e ){
            System.out.println( "error!! while adding operation "+ e  );
        }
        
        return Proc;
    }
    
    private final String addParameter( String Proc, org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeInterface[] Param ){
        try{
        /**
        if( Param == null || Param.length < 1 )
            return Proc;
        */
        //else
        Proc += " ( ";
        
        Proc = this.addParamToProc( Proc , Param );
        
        Proc += " ) ";
        }
        catch( Exception e ){
            System.out.println( "error!! while adding parameters "+ e  );
        }
        return Proc;        
    }
    
    private final String addParamToProc( String Q, org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.TableAttributeInterface[]  Attr)
    {
        try{
        for( int i=0; i< Attr.length; i++){ 
            
            if( i != 0 )
        Q += " , " + Attr[ i ].getAttName() + " " + Attr[ i ].getAttType()  ;         
            else
        Q += "  " + Attr[ i ].getAttName() +  " " +  Attr[ i ].getAttType() ;     
            
            // for keeping size if the type is bool
            if( Attr[i].getAttType(). equals( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.TableAttribute.BOOLEAN )  )
            {
                // do nothing
            }
            else
            {
        Q += " ( " +  Attr[ i ].getAttSize() + " ) ";
            }            
        }
        }
        catch( Exception e ){
            System.out.println( "error!! while adding parameters "+ e  );
        }
        return Q;
    }
    
    private final String addOperation( String Proc, org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces.ProcedureInterface DomProc ){
        
        Proc += " BEGIN " + DomProc.getOperation() + " END " ;
        
        return Proc;
    }
    
}
