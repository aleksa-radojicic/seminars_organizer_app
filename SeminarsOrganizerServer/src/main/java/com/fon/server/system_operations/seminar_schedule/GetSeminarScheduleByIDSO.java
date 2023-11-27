/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar_schedule;

import com.fon.common.domain.SeminarEnrollment;
import com.fon.common.domain.SeminarSchedule;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 *
 * @author Aleksa
 */
public class GetSeminarScheduleByIDSO extends AbstractSO {

    private SeminarSchedule seminarSchedule;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    public SeminarSchedule getSeminarSchedule() {
        return seminarSchedule;
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        int seminarScheduleID = (int) arg;

        String whereSeminarScheduleQuerySection = " WHERE seminarScheduleID = " + seminarScheduleID;

        seminarSchedule = null;
        List<SeminarSchedule> seminarSchedules = (List<SeminarSchedule>) REPOSITORY.getByCondition(new SeminarSchedule(), whereSeminarScheduleQuerySection);

        if (seminarSchedules != null) {
            seminarSchedule = seminarSchedules.get(0);
            
            String whereSeminarEnrollmentQuerySection = "WHERE seminarScheduleID = "+seminarSchedule.getSeminarScheduleID();
            
            List<SeminarEnrollment> seminarEnrollments = REPOSITORY.getByCondition(new SeminarEnrollment(), whereSeminarEnrollmentQuerySection);
            seminarSchedule.setSeminarEnrollments(seminarEnrollments);
            
            for (SeminarEnrollment seminarEnrollment : seminarEnrollments) {
                seminarEnrollment.setSeminarSchedule(seminarSchedule);
            }
        }
    }
}
