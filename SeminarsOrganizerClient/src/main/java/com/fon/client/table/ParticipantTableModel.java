/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.client.table;

import com.fon.common.domain.Participant;
import com.fon.common.domain.Sex;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.fon.common.utils.Utility;

/**
 *
 * @author Aleksa
 */
public class ParticipantTableModel extends AbstractTableModel {

    String[] columns = {"Име", "Презиме", "Пол", "Датум рођења"};
    Class[] classes = {String.class, String.class, String.class, String.class};

    private List<Participant> list;

    public ParticipantTableModel() {
        list = new LinkedList();
    }

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
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Participant p = list.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                p.getName();
            case 1 ->
                p.getSurname();
            case 2 ->
                p.getSex().toString();
            case 3 ->
                Utility.DATE_FORMAT.format(p.getDateBirth());
            default ->
                throw new AssertionError();
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public List<Participant> getList() {
        return list;
    }

    public void setList(List<Participant> list) {
        this.list = list;
        fireTableDataChanged();
    }

    public void addRow(Participant p) {
        list.add(p);
        fireTableDataChanged();
    }

    public void addRow() {
        Participant p = new Participant();
        list.add(p);
        fireTableDataChanged();
    }

    public void removeRow(int index) {
        list.remove(index);
        fireTableDataChanged();
    }
}
