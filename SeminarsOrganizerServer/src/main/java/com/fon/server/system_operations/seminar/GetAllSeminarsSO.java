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
public class GetAllSeminarsSO extends AbstractSO {

    private List<Seminar> allSeminars;

    @Override
    protected void preconditions(Object arg) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        allSeminars = REPOSITORY.getByCondition(new Seminar(), "");

        for (Seminar seminar : allSeminars) {
            List<SeminarTopic> seminarTopics = REPOSITORY.getByCondition(new SeminarTopic(), "WHERE seminarID = " + seminar.getSeminarID());
            seminar.setSeminarTopics(seminarTopics);

            for (SeminarTopic seminarTopic : seminarTopics) {
                seminarTopic.setSeminar(seminar);
            }
        }
    }

    public List<Seminar> getAllSeminars() {
        return allSeminars;
    }
}
