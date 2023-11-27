/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar;

import com.fon.common.domain.Seminar;
import com.fon.common.domain.SeminarTopic;
import java.util.List;
import com.fon.server.system_operations.AbstractSO;

/**
 *
 * @author Aleksa
 */
public class GetSeminarByIDSO extends AbstractSO {

    private Seminar seminar;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    public Seminar getSeminar() {
        return seminar;
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        int id = (int) arg;
        seminar = null;
        List<Seminar> seminars = REPOSITORY.getByCondition(new Seminar(), "WHERE seminarID =" + id);

        seminar = seminars.get(0);

        List<SeminarTopic> seminarTopics = REPOSITORY.getByCondition(new SeminarTopic(), " WHERE seminarID = " + seminar.getSeminarID());
        seminar.setSeminarTopics(seminarTopics);

        for (SeminarTopic seminarTopic : seminarTopics) {
            seminarTopic.setSeminar(seminar);
        }
    }
}
