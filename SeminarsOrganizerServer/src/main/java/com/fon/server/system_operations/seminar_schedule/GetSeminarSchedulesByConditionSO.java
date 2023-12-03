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
import com.fon.server.constants.ServerConstants;

/**
 * System operation used for retrieving seminar schedules from the database that
 * meet a certain condition.
 *
 * <p>
 * Extends abstract system operation class {@code AbstractSO}.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class GetSeminarSchedulesByConditionSO extends AbstractSO {

    /**
     * All seminar schedules retrieved from the database that meet a certain
     * condition as {@code List<SeminarSchedule>}.
     */
    private List<SeminarSchedule> seminarSchedules;

    /**
     * Checks if the sent object is having the required type and structure.
     *
     * @param arg Instance of {@code List<Object>} class having seminar name
     * condition ({@code String}) as the first element and date and time
     * condition ({@code Date}) as the second element.
     * @throws Exception When the sent object doesn't meet the required type and
     * structure.
     */
    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof List)) {
            throw new Exception(ServerConstants.INCORRECT_TYPE_ERROR_MESSAGE);
        }

        List list = (List<Object>) arg;
        if (list.size() != 2) {
            throw new Exception(ServerConstants.INCORRECT_TYPE_ERROR_MESSAGE);
        }

        Object firstElement = list.get(0);
        if (firstElement == null || !(firstElement instanceof String)) {
            throw new Exception(ServerConstants.INCORRECT_TYPE_ERROR_MESSAGE);
        }

        Object secondElement = list.get(1);
        if (secondElement != null && !(secondElement instanceof Date)) {
            throw new Exception(ServerConstants.INCORRECT_TYPE_ERROR_MESSAGE);
        }
    }

    /**
     * Getter for seminarSchedules.
     *
     * @return All seminar schedules retrieved from the database that meet a
     * certain condition as {@code List<SeminarSchedule>}.
     */
    public List<SeminarSchedule> getSeminarSchedules() {
        return seminarSchedules;
    }

    /**
     * Retrieves all seminar schedules from the database that meet a certain
     * condition and stores it in {@code seminarSchedules} attribute.
     *
     * @param arg Not used.
     * @throws Exception When an error happened while retrieving all seminar
     * schedules that meet a certain condition.
     */
    @Override
    protected void executeOperation(Object arg) throws Exception {
        seminarSchedules = new LinkedList();

        String whereQuerySeminarScheduleSection = getWhereQuerySeminarScheduleSection(arg);

        List<SeminarSchedule> seminarSchedules_ = REPOSITORY.getByCondition(new SeminarSchedule(), whereQuerySeminarScheduleSection);

        if (seminarSchedules_.isEmpty()) {
            return;
        }

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

    /**
     * Creates the entire WHERE query section for retrieving all seminar
     * schedules by condition.
     *
     * @param arg A condition that will be used in WHERE query section.
     * @return Entire WHERE query section necessary for the execution of the
     * system operation.
     */
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

    /**
     * Creates the WHERE query section regarding seminar name condition.
     *
     * @param seminarName Seminar name condition as {@code String}.
     * @return WHERE query section regarding seminar name condition.
     */
    private String getSeminarNameQueryCondition(String seminarName) {
        return "s.name LIKE " + Utility.encloseWithSingleQuotes("%" + seminarName + "%");
    }

    /**
     * Creates the WHERE query section regarding seminar schedule date and time
     * condition.
     *
     * @param date Date and time seminar schedule condition as {@code Date}.
     * @return WHERE query section regarding seminar schedule date and time
     * condition.
     */
    private String getDatetimeQueryCondition(Date date) {
        String dateString = new java.sql.Date(date.getTime()).toString();

        return Utility.encloseWithSingleQuotes(dateString) + " BETWEEN DATE(datetimeBegins) and DATE(datetimeEnds)";
    }
}
