/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.client.table;

import com.fon.common.domain.SeminarEnrollment;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.fon.common.utils.Utility;

/**
 *
 * @author Aleksa
 */
public class SeminarEnrollmentTableModel extends AbstractTableModel {

    String[] columns = {"Име", "Презиме", "Пол", "Старост", "Прибелешка"};
    Class[] classes = {String.class, String.class, String.class, String.class, String.class};

    private List<SeminarEnrollment> list;
    private List<SeminarEnrollment> listOriginal;

    public SeminarEnrollmentTableModel() {
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
        SeminarEnrollment seminarEnrollment = list.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                seminarEnrollment.getParticipant().getName();
            case 1 ->
                seminarEnrollment.getParticipant().getSurname();
            case 2 ->
                seminarEnrollment.getParticipant().getSex().toString();
            case 3 ->
                seminarEnrollment.getParticipant().getAge();
            case 4 -> 
                seminarEnrollment.getNotes();
            default ->
                throw new AssertionError();
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public List<SeminarEnrollment> getList() {
        return list;
    }

    public void setList(List<SeminarEnrollment> list) throws IOException, ClassNotFoundException {
        this.list = list;
        this.listOriginal = Utility.getDeepCopy(this.list);
        fireTableDataChanged();
    }

    public void addRow(SeminarEnrollment se) {
        list.add(se);
        fireTableDataChanged();
    }

    public void addRow() {
        SeminarEnrollment se = new SeminarEnrollment();
        list.add(se);
        fireTableDataChanged();
    }

    public void removeRow(int index) {
        list.remove(index);
        fireTableDataChanged();
    }
    
    public boolean isChangedList() {
        return SeminarEnrollment.equalsAll(listOriginal, list);
    }

    public List<SeminarEnrollment> getListOriginal() {
        return listOriginal;
    }
}
