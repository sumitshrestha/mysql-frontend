
//                         !! RAM !!

/**
 * TransactionManager_UI_Interface.java
 *
 * Created on November 1, 2007, 8:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.domain.BackEndInterfaces;

/***
 * This is the interface implemented by the transaction UI to get notify on any changes in transaction.
 * @author sumit shrestha
 */
public interface TransactionListener {
    
    void onTransactionAdded( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction Transac );
    
    void onTransactionCommited( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction[] CommittedTransac, java.sql.Savepoint Sp );
    
    void onTranactionRollbacked( org.edu.gces.s2005.projects.frontendformysql.domain.BackEndData.Transaction[] RollbackedTransac, java.sql.Savepoint Sp );
    
    void onSavePointAdded( java.sql.Savepoint AddedSp );
    
    void onSavePointRealeased( java.sql.Savepoint ReleasedSp );
    
}
