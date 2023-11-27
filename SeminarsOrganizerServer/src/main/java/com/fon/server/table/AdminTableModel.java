/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.table;

import com.fon.common.domain.Admin;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Aleksa
 */
public class AdminTableModel extends AbstractTableModel {

    String[] columns = {"Корисничко име", "Име и презиме"};
    Class[] classes = {String.class, String.class};
    
    private List<Admin> admins;

    public AdminTableModel() {
        admins = new ArrayList<>();
    }

    public AdminTableModel(List<Admin> admins) {
        this.admins = admins;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        return admins.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Admin a = admins.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> a.getUsername();
            case 1 -> a.getName() +" "+ a.getSurname();
            default -> throw new AssertionError();
        };
    }

    public void addAdmin(Admin admin) {
        admins.add(admin);
        fireTableDataChanged();
    }
    
    public void removeAdmin(Admin admin) {
        admins.remove(admin);
        fireTableDataChanged();
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }
}
