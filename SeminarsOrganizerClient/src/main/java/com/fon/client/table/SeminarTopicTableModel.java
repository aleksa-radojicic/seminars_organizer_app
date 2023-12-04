/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.client.table;

import com.fon.common.domain.SeminarTopic;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.fon.common.utils.Utility;

/**
 *
 * @author Aleksa
 */
public class SeminarTopicTableModel extends AbstractTableModel {

    private List<SeminarTopic> seminarTopics;
    private List<SeminarTopic> seminarTopicsOriginal;

    
    public SeminarTopicTableModel() {
        seminarTopics = new LinkedList();
    }

    String[] columns = {"Назив", "Предавач"};
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
        return seminarTopics.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SeminarTopic seminarTopic = seminarTopics.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                seminarTopic.getName();
            case 1 ->
                seminarTopic.getPresenter();
            default ->
                throw new AssertionError();
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        SeminarTopic seminarTopic = seminarTopics.get(rowIndex);

        switch (columnIndex) {
            case 0 ->
                seminarTopic.setName((String) aValue);
            case 1 ->
                seminarTopic.setPresenter((String) aValue);
            default ->
                throw new AssertionError();
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public void addRow() {
        SeminarTopic st = new SeminarTopic();
        seminarTopics.add(st);
        fireTableDataChanged();
    }

    public void removeRow(int index) {
        seminarTopics.remove(index);
        fireTableDataChanged();
    }

    public List<SeminarTopic> getSeminarTopics() {
        return seminarTopics;
    }

    public void setSeminarTopics(List<SeminarTopic> seminarTopics) throws IOException, ClassNotFoundException {
        this.seminarTopics = seminarTopics;
        this.seminarTopicsOriginal = Utility.clone(this.seminarTopics);
    }

    public List<SeminarTopic> getSeminarTopicsOriginal() {
        return seminarTopicsOriginal;
    }

    public void setSeminarTopicsOriginal(List<SeminarTopic> seminarTopicsOriginal) {
        this.seminarTopicsOriginal = seminarTopicsOriginal;
    }
    
    
}
