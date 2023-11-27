/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.threads;

import com.fon.common.intercomm.Operation;
import com.fon.common.intercomm.Request;
import com.fon.common.intercomm.Response;
import com.fon.common.intercomm.Communication;
import com.fon.server.controller.ServerController;
import com.fon.common.domain.*;
import com.fon.common.exceptions.ServerValidationException;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ClientHandlerThread extends Thread {

    private final ServerThread server;
    private Socket socket;
    private Admin loggedAdmin;
    private final int clientNumber;

    public ClientHandlerThread(ServerThread server, Socket socket, int clientNumber) {
        this.socket = socket;
        this.server = server;
        this.clientNumber = clientNumber;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Admin getLoggedAdmin() {
        return loggedAdmin;
    }

    public void setLoggedAdmin(Admin loggedAdmin) {
        this.loggedAdmin = loggedAdmin;
    }

    @Override
    public void run() {
        System.out.println("Client handler thread " + clientNumber + " has started");

        while (!socket.isClosed()) {
            try {
                Request request = (Request) Communication.getInstance().receive(socket);
                Response response = handleRequest(request);
                Communication.getInstance().send(socket, response);
            } catch (Exception ex) {
                System.out.println("Client " + clientNumber + " has disconnected");
                logout();
            }
        }

        System.out.println("Client handler thread " + clientNumber + " has finished");
    }

    private Response handleRequest(Request request) {
        Response response = new Response();

        Object argument = request.getArgument();
        Operation operation = request.getOperation();
        System.out.println("operation = " + operation);
        Object result = null;

        try {
            switch (operation) {
                case LOGIN -> {
                    result = login((Admin) argument);
                }
                case CREATE_SEMINAR -> {
                    createSeminar((Seminar) argument);
                }
                case GET_ALL_SEMINARS -> {
                    result = (List<Seminar>) getAllSeminars();
                }
                case GET_SEMINAR_BY_ID -> {
                    result = (Seminar) getSeminarByID((int) argument);
                }
                case GET_SEMINARS_BY_CONDITION -> {
                    result = (List<Seminar>) getSeminarsByCondition((String) argument);
                }
                case CREATE_PARTICIPANT -> {
                    createParticipant((Participant) argument);
                }
                case GET_ALL_PARTICIPANTS -> {
                    result = (List<Participant>) getAllParticipants();
                }
                case GET_PARTICIPANTS_BY_CONDITION -> {
                    result = (List<Participant>) getParticipantsByCondition((String) argument);
                }
                case GET_PARTICIPANT_BY_ID -> {
                    result = (Participant) getParticipantByID((int) argument);
                }
                case GET_ALL_EDUCATIONAL_INSTITUTIONS -> {
                    result = (List<EducationalInstitution>) getAllEducationalInstitutions();
                }
                case CREATE_SEMINAR_SCHEDULE -> {
                    createSeminarSchedule((SeminarSchedule) argument);
                }
                case GET_SEMINAR_SCHEDULES_BY_CONDITION -> {
                    result = (List<SeminarSchedule>) getSeminarSchedulesByCondition((List<Object>) argument);
                }
                case GET_SEMINAR_SCHEDULE_BY_ID -> {
                    result = (SeminarSchedule) getSeminarScheduleByID((int) argument);
                }
                case SAVE_SEMINAR_SCHEDULE -> {
                    saveSeminarSchedule((SeminarSchedule) argument);
                }
                case SAVE_SEMINAR -> {
                    saveSeminar((Seminar) argument);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setException(ex);
        }
        response.setResult(result);
        return response;
    }

    private Admin login(Admin admin) throws Exception {
        admin = ServerController.getInstance().login(admin);

        checkIfAdminIsAlreadyLoggedIn(admin);

        this.loggedAdmin = admin;
        server.login(this);
        return admin;
    }

    public void logout() {
        try {
            server.logout(this);
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createSeminar(Seminar seminar) throws Exception {
        ServerController.getInstance().createSeminar(seminar);
    }

    private List<Seminar> getAllSeminars() throws Exception {
        List<Seminar> allSeminars = ServerController.getInstance().getAllSeminars();
        return allSeminars;
    }

    private List<Seminar> getSeminarsByCondition(String condition) throws Exception {
        List<Seminar> seminarsFiltered = ServerController.getInstance().getSeminarsByCondition(condition);
        return seminarsFiltered;
    }

    private Seminar getSeminarByID(int id) throws Exception {
        Seminar seminar = ServerController.getInstance().getSeminarByID(id);
        return seminar;
    }

    private void createParticipant(Participant participant) throws Exception {
        ServerController.getInstance().createParticipant(participant);
    }

    private List<Participant> getAllParticipants() throws Exception {
        List<Participant> allParticipants = ServerController.getInstance().getAllParticipants();
        return allParticipants;
    }

    private void checkIfAdminIsAlreadyLoggedIn(Admin admin) throws ServerValidationException {
        if (server.checkIfAdminIsAlreadyLoggedIn(admin)) {
            throw new ServerValidationException("Већ сте пријављени");
        }
    }

    private List<Participant> getParticipantsByCondition(String condition) throws Exception {
        List<Participant> participantsFiltered = ServerController.getInstance().getParticipantsByCondition(condition);
        return participantsFiltered;
    }

    private Participant getParticipantByID(int id) throws Exception {
        return ServerController.getInstance().getParticipantByID(id);
    }

    private List<EducationalInstitution> getAllEducationalInstitutions() throws Exception {
        return (List<EducationalInstitution>) ServerController.getInstance().getAllEducationalInstitutions();
    }

    private void createSeminarSchedule(SeminarSchedule seminarSchedule) throws Exception {
        ServerController.getInstance().createSeminarSchedule(seminarSchedule);
    }

    private List<SeminarSchedule> getSeminarSchedulesByCondition(List<Object> condition) throws Exception {
        List<SeminarSchedule> seminarSchedulesFiltered = ServerController.getInstance().getSeminarSchedulesByCondition(condition);
        return seminarSchedulesFiltered;
    }

    private SeminarSchedule getSeminarScheduleByID(int seminarScheduleID) throws Exception {
        return ServerController.getInstance().getSeminarScheduleByID(seminarScheduleID);
    }

    private void saveSeminarSchedule(SeminarSchedule seminarSchedule) throws Exception {
        ServerController.getInstance().saveSeminarSchedule(seminarSchedule);
    }

    private void saveSeminar(Seminar seminar) throws Exception {
        ServerController.getInstance().saveSeminar(seminar);
    }
}