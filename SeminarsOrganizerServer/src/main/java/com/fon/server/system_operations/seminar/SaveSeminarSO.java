/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar;

import com.fon.common.domain.Seminar;
import com.fon.common.domain.SeminarTopic;
import com.fon.common.domain.State;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 *
 * @author Aleksa
 */
public class SaveSeminarSO extends AbstractSO {

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof Seminar)) {
            throw new Exception("Послати објекат није одговарајућег типа");
        }
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        Seminar seminar = (Seminar) arg;

        if (seminar.getState().equals(State.CHANGED)) {
            REPOSITORY.update(seminar);
        }

        List<SeminarTopic> seminarTopics = seminar.getSeminarTopics();

        for (SeminarTopic seminarTopic : seminarTopics) {
            switch (seminarTopic.getState()) {
                case UNCHANGED -> {
                }
                case CREATED -> {
                    REPOSITORY.create(seminarTopic);
                }
                case CHANGED -> {
                    REPOSITORY.update(seminarTopic);
                }
                case DELETED -> {
                    String whereSection = "WHERE seminarID = "
                            + seminarTopic.getSeminar().getSeminarID()
                            + " AND seminarTopicID = " 
                            + seminarTopic.getSeminarTopicID();
                    REPOSITORY.delete(seminarTopic, whereSection);
                }
                default ->
                    throw new AssertionError();
            }
        }

    }

}
