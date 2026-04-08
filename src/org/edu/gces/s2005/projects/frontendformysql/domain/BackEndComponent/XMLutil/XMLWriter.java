
//                                      !! RAM !!

/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.XMLutil;

import javax.xml.stream.XMLStreamException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/***
 * This Class uses standard XML Writer to write on any XML Document as specified by its user.
 * 
 * @author Sumit Shrestha
 */
public class XMLWriter {
    
    /**
     * Empty constructor
     */
    public XMLWriter(){        
    }
    
    public boolean write( org.w3c.dom.Document XMLDoc, String Path ){
        try{
            if( Path == null ){
                this.setErrorMessage( "Path is null" );
                return false;
            }
                
            java.io.File XMLFile = new java.io.File( Path );
            
            Writer = WriterFact.createXMLStreamWriter( new java.io.FileOutputStream( XMLFile ) );            
            
            this.writeNode( XMLDoc );
            
            Writer.close();
            
            this.Writer = null;
            
            return true;
        }
        catch( Exception e ){
            this.setErrorMessage( e.getMessage() );
            return false;
        }
    }

    private boolean WriteMainDocument(Node Node) throws XMLStreamException {
        org.w3c.dom.Document XMLDoc = (org.w3c.dom.Document) Node;
        Writer.writeStartDocument(XMLDoc.getXmlEncoding(), XMLDoc.getXmlVersion());
        String DTDStr = this.getDTDString( XMLDoc.getDoctype() );
        if( DTDStr != null )
            Writer.writeDTD( DTDStr );
        this.writeNode(((org.w3c.dom.Document) Node).getDocumentElement());
        this.Writer.writeEndDocument();
        return true;
    }
    
    private String getDTDString( org.w3c.dom.DocumentType DTD ){
        try{
            if( DTD == null ){
                this.setErrorMessage( "DTD was null" );
                return null;
            }
            
            String DTDStr = "<!DOCTYPE " + DTD.getName() + " [ ";
            
            String InternalSubset = DTD.getInternalSubset();
            
            DTDStr += InternalSubset;
            
            DTDStr += "]>";
            
            return DTDStr;
        }   
        catch( Exception e ){
            this.setErrorMessage( e.getMessage() );
            return null;
        }
    }
    
    private void addAttributesToElement(Node Node) throws XMLStreamException {

        if (Node.hasAttributes()) {
            org.w3c.dom.NamedNodeMap Attributes = Node.getAttributes();
            for (int i = 0; i < Attributes.getLength(); i++) {
                org.w3c.dom.Attr Attr = (org.w3c.dom.Attr) Attributes.item(i);
                this.Writer.writeAttribute(Attr.getName(), Attr.getValue());
            }
        }
    }

    private void callChildrenNodes(Node Node) {

        if (Node.hasChildNodes()) {
            org.w3c.dom.NodeList ChildNodes = Node.getChildNodes();
            for (int i = 0; i < ChildNodes.getLength(); i++) {
                this.writeNode(ChildNodes.item(i));
            }
        }
    }

    private void writeCData(Node Node) throws DOMException, XMLStreamException {
        String Text = Node.getNodeValue();
        if (Text != null && Text.length() > 0) {
            this.Writer.writeCData(Text);
        }
    }

    private boolean writeElement(Node Node) throws XMLStreamException {
        org.w3c.dom.Element Element = ( org.w3c.dom.Element ) Node;
        this.Writer.writeStartElement( Element.getTagName() );
        addAttributesToElement(Node);
        callChildrenNodes(Node);

        this.Writer.writeEndElement();
        return true;
    }
    
    private boolean writeNode( org.w3c.dom.Node Node ){
        try{
            if( Node == null ){
                this.setErrorMessage( "Node was found null" );
                return false;
            }
            
            final int type = Node.getNodeType();
            
            switch( type ){
                case org.w3c.dom.Node.DOCUMENT_NODE:
                    return WriteMainDocument(Node);
                
                case org.w3c.dom.Node.ELEMENT_NODE:
                    return writeElement(Node);
                
                case org.w3c.dom.Node.TEXT_NODE:
                    writeText( Node );
                    break;
                
                case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:
                    writeProcessingInstruction(Node);
                    break;
                                
                case org.w3c.dom.Node.CDATA_SECTION_NODE:
                    writeCData(Node);
                    break;
                
            }// large switch
            
            return true;
        }
        catch( Exception e ){
            this.setErrorMessage( e.getMessage() );
            return false;
        }
    }
    
    private void setErrorMessage( String message ){
        this.ErrorMessage = message;
    }
    
    public final String getErrorMessage(){
        return this.ErrorMessage;
    }
    
    private String ErrorMessage = null;
    private javax.xml.stream.XMLOutputFactory WriterFact = javax.xml.stream.XMLOutputFactory.newInstance();
    private javax.xml.stream.XMLStreamWriter Writer = null;

    private void writeProcessingInstruction(Node Node) throws XMLStreamException, DOMException {
        String Text = Node.getNodeValue();
        if (Text != null && Text.length() > 0) {
            this.Writer.writeProcessingInstruction(Text);
        }
    }

    private void writeText(Node Node) throws XMLStreamException, DOMException {
        this.Writer.writeCharacters(Node.getNodeValue().trim());
    }
}
