/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.client.table;

import com.fon.common.domain.Seminar;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Aleksa
 */
public class SeminarTableModel extends AbstractTableModel {

    private List<Seminar> seminars;

    public SeminarTableModel() {
        seminars = new LinkedList();
    }

    String[] columns = {"Назив", "Опис"};
    Class[] classes = {String.class, String.class};

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classes[columnIndex];
    }

    @Override
    public int getRowCount() {
        return seminars.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Seminar seminar = seminars.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                seminar.getName();
            case 1 ->
                seminar.getDescription();
            default ->
                throw new AssertionError();
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Seminar seminar = seminars.get(rowIndex);

        switch (columnIndex) {
            case 0 ->
                seminar.setName((String) aValue);
            case 1 ->
                seminar.setDescription((String) aValue);
            default ->
                throw new AssertionError();
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    public List<Seminar> getSeminars() {
        return seminars;
    }

    public void setSeminars(List<Seminar> seminars) {
        this.seminars = seminars;
        fireTableDataChanged();
    }
}
