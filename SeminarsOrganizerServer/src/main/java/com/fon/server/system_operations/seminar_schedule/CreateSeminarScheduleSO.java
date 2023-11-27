/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar_schedule;

import com.fon.common.domain.SeminarEnrollment;
import com.fon.common.domain.SeminarSchedule;
import com.fon.server.system_operations.AbstractSO;

/**
 *
 * @author Aleksa
 */
public class CreateSeminarScheduleSO extends AbstractSO {

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof SeminarSchedule)) {
            throw new Exception("Послати објекат није одговарајућег типа");
        }
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        SeminarSchedule seminarSchedule = (SeminarSchedule) arg;
        REPOSITORY.create(seminarSchedule);
        
        for (SeminarEnrollment seminarEnrollment : seminarSchedule.getSeminarEnrollments()) {
            REPOSITORY.create(seminarEnrollment);
        }
        
    }
}
