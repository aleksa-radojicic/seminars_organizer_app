/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar;

import com.fon.common.domain.Seminar;
import com.fon.common.domain.SeminarTopic;
import com.fon.server.system_operations.AbstractSO;

/**
 *
 * @author Aleksa
 */
public class CreateSeminarSO extends AbstractSO {

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof Seminar)) {
            throw new Exception("Послати објекат није одговарајућег типа");
        }
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        Seminar seminar = (Seminar) arg;
        REPOSITORY.create(seminar);

        for (SeminarTopic seminarTopic : seminar.getSeminarTopics()) {
            REPOSITORY.create(seminarTopic);
        }
    }
}
