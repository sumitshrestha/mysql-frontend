
//                  !! RAM !!

/**
 * TreeSearcher.java
 *
 * Created on October 29, 2007, 11:59 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.Editor;

/***
 *  This is component primarily searches the tree using nodes.
 * @author sumit
 */
public class TreeSearcher {
    
    /*** Creates a new instance of TreeSearcher */
    public TreeSearcher( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.Editor.TreeSearcher.TreeSearchInterface Tree ) {
        this.Tree = Tree;
    }
    
    public boolean recursive_find( javax.swing.tree.TreeNode Node ){
      if( Node == null || Node.toString() == null )
          return true;
                
      if(  this.check( Node.toString() ) ){
          // stop n show                    
            try{
             this.Tree.displayNode(Node);
             return this.Tree.continueSearch();
               }
               catch( java.lang.ClassCastException e){
                     System.out.println("could not be casted");
                     return false;
               }
               }
                
               else{
                   for( int i=0;i< Node.getChildCount(); i++ ){
                       if( this.recursive_find( Node.getChildAt( i )) )
                           continue;
                       else
                           return false;
                   }
                    return true;
                }
            }// recuresive find ends
    
    
    /**
     * This is code is for checking the tree node during search.
     */
     private boolean check( String word ){
                if( this.ignore_case )
                    return word.equalsIgnoreCase( findword ); 
                else
                    return findword.equals( word );
            }
           
    public void setFindCond( String Word, boolean s ){
        this.ignore_case = s;
        this.findword = Word;
    }
    
    private String findword = null;
    private boolean ignore_case = true;     
    private org.edu.gces.s2005.projects.frontendformysql.domain.BackEndComponent.Editor.TreeSearcher.TreeSearchInterface Tree = null;
    
    public interface TreeSearchInterface{
        public void displayNode(javax.swing.tree.TreeNode Node);
        public boolean continueSearch();
    }
    
}


