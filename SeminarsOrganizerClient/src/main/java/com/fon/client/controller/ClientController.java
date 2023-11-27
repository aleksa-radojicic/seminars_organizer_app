/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.client.controller;

import com.fon.client.comunication.ClientCommunicator;
import com.fon.common.intercomm.*;
import com.fon.common.domain.*;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Aleksa
 */
public class ClientController {

    private static ClientController instance;
    private Admin loggedAdmin;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    private Object sendObjectReceiveResponse(Object argument, Operation operation) throws Exception {
        try {
            Request request = new Request(argument, operation);
            Response response = ClientCommunicator.getInstance().sendRequestReceiveResponse(request);

            if (response.getException() != null) {
                throw response.getException();
            }
            return response.getResult();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Пукла је веза или се сервер угасио", "Сервер недоступан", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        return null;
    }

    public Admin login(Admin admin) throws Exception {
        admin = (Admin) sendObjectReceiveResponse(admin, Operation.LOGIN);
        return admin;
    }

    public void createSeminar(Seminar seminar) throws Exception {
        sendObjectReceiveResponse(seminar, Operation.CREATE_SEMINAR);
    }

    public List<Seminar> getAllSeminars() throws Exception {
        List<Seminar> allSeminars = (List<Seminar>) sendObjectReceiveResponse(null, Operation.GET_ALL_SEMINARS);
        return allSeminars;
    }

    public Seminar getSeminarByID(int condition) throws Exception {
        Seminar seminar = (Seminar) sendObjectReceiveResponse(condition, Operation.GET_SEMINAR_BY_ID);
        return seminar;
    }

    public List<Seminar> getSeminarsByConditition(String condition) throws Exception {
        List<Seminar> filteredSeminars = (List<Seminar>) sendObjectReceiveResponse(condition, Operation.GET_SEMINARS_BY_CONDITION);
        return filteredSeminars;
    }

    public void createParticipant(Participant participant) throws Exception {
        sendObjectReceiveResponse(participant, Operation.CREATE_PARTICIPANT);
    }

    public List<Participant> getAllParticipants() throws Exception {
        List<Participant> participants = (List<Participant>) sendObjectReceiveResponse(null, Operation.GET_ALL_PARTICIPANTS);
        return participants;
    }

    public List<Participant> getParticipantsByConditition(String condition) throws Exception {
        List<Participant> filteredParticipants = (List<Participant>) sendObjectReceiveResponse(condition, Operation.GET_PARTICIPANTS_BY_CONDITION);
        return filteredParticipants;
    }

    public Participant getParticipantByID(int condition) throws Exception {
        return (Participant) sendObjectReceiveResponse(condition, Operation.GET_PARTICIPANT_BY_ID);
    }

    public List<EducationalInstitution> getAllEducationalInstitutions() throws Exception {
        return (List<EducationalInstitution>) sendObjectReceiveResponse(null, Operation.GET_ALL_EDUCATIONAL_INSTITUTIONS);
    }

    public void createSeminarSchedule(SeminarSchedule seminarSchedule) throws Exception {
        sendObjectReceiveResponse(seminarSchedule, Operation.CREATE_SEMINAR_SCHEDULE);
    }

    public List<SeminarSchedule> getSeminarSchedulesByCondition(List<Object> condition) throws Exception {
        List<SeminarSchedule> filteredSeminarSchedules = (List<SeminarSchedule>) sendObjectReceiveResponse(condition, Operation.GET_SEMINAR_SCHEDULES_BY_CONDITION);
        return filteredSeminarSchedules;
    }

    public SeminarSchedule getSeminarScheduleByID(int seminarScheduleID) throws Exception {
        return (SeminarSchedule) sendObjectReceiveResponse(seminarScheduleID, Operation.GET_SEMINAR_SCHEDULE_BY_ID);
    }

    public void saveSeminarSchedule(SeminarSchedule seminarSchedule) throws Exception {
        sendObjectReceiveResponse(seminarSchedule, Operation.SAVE_SEMINAR_SCHEDULE);
    }

    public void saveSeminar(Seminar seminar) throws Exception {
        sendObjectReceiveResponse(seminar, Operation.SAVE_SEMINAR);
    }

}
