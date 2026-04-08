
//                         !! RAM !!

/**
 * DataTableModel.java
 *
 * Created on October 21, 2007, 7:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edu.gces.s2005.projects.frontendformysql.ui.Components;

/***
 *
 * @author sumit
 */
public class DataTableModel extends javax.swing.table.DefaultTableModel {
    
    /*** Creates a new instance of DataTableModel */
    public DataTableModel() {
    }
    
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

}
