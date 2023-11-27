/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.client.table;

import com.fon.common.domain.SeminarSchedule;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import com.fon.common.utils.Utility;

/**
 *
 * @author Aleksa
 */
public class SeminarScheduleTableModel extends AbstractTableModel {

    private List<SeminarSchedule> seminarSchedules;

    public SeminarScheduleTableModel() {
        seminarSchedules = new LinkedList();
    }

    String[] columns = {"Семинар", "Датум почетка", "Датум завршетка", "Локација", "Број учесника"};
    Class[] classes = {String.class, String.class, String.class, String.class, String.class};

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
        return seminarSchedules.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SeminarSchedule seminarSchedule = seminarSchedules.get(rowIndex);

        return switch (columnIndex) {
            case 0 ->
                seminarSchedule.getSeminar().getName();
            case 1 ->
                Utility.DATETIME_FORMAT.format(seminarSchedule.getDatetimeBegins());
            case 2 ->
                Utility.DATETIME_FORMAT.format(seminarSchedule.getDatetimeEnds());
            case 3 ->
                seminarSchedule.getEducationalInstitution();
            case 4 ->
                seminarSchedule.getSeminarEnrollments().size();
            default ->
                throw new AssertionError();
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public List<SeminarSchedule> getSeminarSchedules() {
        return seminarSchedules;
    }

    public void setSeminarSchedules(List<SeminarSchedule> seminarSchedules) {
        this.seminarSchedules = seminarSchedules;
        fireTableDataChanged();
    }
}
