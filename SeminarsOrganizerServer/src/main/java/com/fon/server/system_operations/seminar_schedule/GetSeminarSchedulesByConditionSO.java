/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar_schedule;

import com.fon.common.domain.SeminarEnrollment;
import com.fon.common.domain.SeminarSchedule;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;
import com.fon.common.utils.Utility;

/**
 *
 * @author Aleksa
 */
public class GetSeminarSchedulesByConditionSO extends AbstractSO {

    private List<SeminarSchedule> seminarSchedules;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    public List<SeminarSchedule> getSeminarSchedules() {
        return seminarSchedules;
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        seminarSchedules = new LinkedList();

        String whereQuerySeminarScheduleSection = getWhereQuerySeminarScheduleSection(arg);

        List<SeminarSchedule> seminarSchedules_ = REPOSITORY.getByCondition(new SeminarSchedule(), whereQuerySeminarScheduleSection);

        if (seminarSchedules_ != null) {
            for (SeminarSchedule seminarSchedule : seminarSchedules_) {
                String whereQuerySeminarEnrollmentSection = "WHERE seminarScheduleID = " + seminarSchedule.getSeminarScheduleID();
                
                List<SeminarEnrollment> seminarEnrollments = REPOSITORY.getByCondition(new SeminarEnrollment(), whereQuerySeminarEnrollmentSection);
                seminarSchedule.setSeminarEnrollments(seminarEnrollments);

                for (SeminarEnrollment seminarEnrollment : seminarEnrollments) {
                    seminarEnrollment.setSeminarSchedule(seminarSchedule);
                }
            }
            seminarSchedules = seminarSchedules_;
        }
    }

    private String getWhereQuerySeminarScheduleSection(Object arg) {
        List<Object> condition = (List<Object>) arg;

        String seminarName = (String) condition.get(0);
        Date dateSeminarSchedule = (Date) condition.get(1);

        String whereQuerySection = "";

        if ((!Utility.isStringNullOrBlank(seminarName))
                && (dateSeminarSchedule != null)) {
            whereQuerySection = new StringBuilder()
                    .append("WHERE ")
                    .append(getSeminarNameQueryCondition(seminarName))
                    .append(" and ")
                    .append("(")
                    .append(getDatetimeQueryCondition(dateSeminarSchedule))
                    .append(" or ")
                    .append(")")
                    .toString();
        }

        if (!Utility.isStringNullOrBlank(seminarName)) {
            whereQuerySection = "WHERE " + getSeminarNameQueryCondition(seminarName);
        }

        if (dateSeminarSchedule != null) {
            whereQuerySection = "WHERE " + getDatetimeQueryCondition(dateSeminarSchedule);
        }
        return whereQuerySection;
    }

    private String getSeminarNameQueryCondition(String seminarName) {
        return "s.name LIKE " + Utility.encloseWithSingleQuotes("%" + seminarName + "%");
    }

    private String getDatetimeQueryCondition(Date date) {
        String dateString = new java.sql.Date(date.getTime()).toString();

        return Utility.encloseWithSingleQuotes(dateString)+ " BETWEEN DATE(datetimeBegins) and  DATE(datetimeEnds)";
    }
}
