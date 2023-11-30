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
 * Thread handling communication with a client on the server side.
 *
 * <p>
 * The class, that extends {@code Thread}, is responsible for handling
 * communication with individual clients connected to the server. It manages
 * communication through the associated socket and performs operations based on
 * received requests.
 * </p>
 *
 * <p>
 * It is terminated when server is closed or when client closes the application.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class ClientHandlerThread extends Thread {

    /**
     * The associated instance of {@code ServerThread} that manages this client
     * handler (read only).
     */
    private final ServerThread server;

    /**
     * The socket for communication with the client as {@code Socket}.
     */
    private Socket socket;

    /**
     * The logged-in admin associated with this client as {@code Admin}.
     */
    private Admin loggedAdmin;

    /**
     * The client number assigned to this client handler as {@code int} (read
     * only).
     */
    private final int clientNumber;

    /**
     * Constructor with all parameters except loggedAdmin.
     *
     * @param server Reference to the server's form as {@code ServerForm}.
     * @param socket The socket for communication with the client as
     * {@code Socket}.
     * @param clientNumber The client number assigned to this client handler as
     * {@code int}.
     */
    public ClientHandlerThread(ServerThread server, Socket socket, int clientNumber) {
        this.socket = socket;
        this.server = server;
        this.clientNumber = clientNumber;
    }

    /**
     * Getter for socket.
     *
     * @return The socket for communication with the client as {@code Socket}.
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Setter for socket.
     *
     * @param socket The socket for communication with the client as
     * {@code Socket}.
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * Getter for loggedAdmin.
     *
     * @return The logged-in admin associated with this client as {@code Admin}.
     */
    public Admin getLoggedAdmin() {
        return loggedAdmin;
    }

    /**
     * Setter for loggedAdmin.
     *
     * @param loggedAdmin The logged-in admin associated with this client as
     * {@code Admin}.
     */
    public void setLoggedAdmin(Admin loggedAdmin) {
        this.loggedAdmin = loggedAdmin;
    }

    /**
     * Overrides the run method from the Thread class to handles client
     * communication.
     *
     * <p>
     * Monitors the client socket for incoming requests, processes them and
     * sends back the appropriate response. Handles disconnection and logout
     * scenarios.
     * </p>
     */
    @Override
    public void run() {
        System.out.println("Client handler thread " + clientNumber + " has started");

        while (!socket.isClosed()) {
            try {
                Request request = (Request) Communication.getInstance().receive(socket);
                Response response = handleRequest(request);
                Communication.getInstance().send(socket, response);
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Client " + clientNumber + " has disconnected");
                logout();
            }
        }

        System.out.println("Client handler thread " + clientNumber + " has finished");
    }

    /**
     * Processes incoming requests, determined by the associated
     * {@code Operation}s and generates appropriate responses.
     *
     * @param request The incoming request as {@code Request}.
     * @return The response as {@code Response} generated based on the operation
     * executed.
     */
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

    /**
     * Logs in the passed admin using the {@code ServerController} instance and
     * returns all data about the admin.
     *
     * @param admin Admin that needs to be checked if he entered correct
     * credentials.
     * @return Logged admin as {@code Admin} with all data.
     * @throws Exception When admin cannot be logged in.
     */
    private Admin login(Admin admin) throws Exception {
        admin = ServerController.getInstance().login(admin);

        checkIfAdminIsAlreadyLoggedIn(admin);

        this.loggedAdmin = admin;
        server.login(this);
        return admin;
    }

    /**
     * Logs out the current client and closes socket.
     */
    public void logout() {
        try {
            server.logout(this);
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates the passed seminar using the {@code ServerController} instance.
     *
     * @param seminar Seminar that needs to be created as {@code Seminar}.
     * @throws Exception When seminar cannot be created.
     */
    private void createSeminar(Seminar seminar) throws Exception {
        ServerController.getInstance().createSeminar(seminar);
    }

    /**
     * Retrieves all seminars using the {@code ServerController} instance.
     *
     * @return All seminars from the database as {@code List<Seminar>}.
     * @throws Exception When all seminars cannot be retrieved.
     */
    private List<Seminar> getAllSeminars() throws Exception {
        List<Seminar> allSeminars = ServerController.getInstance().getAllSeminars();
        return allSeminars;
    }

    /**
     * Retrieves seminars by the passed condition using the
     * {@code ServerController} instance.
     *
     * @param condition Condition used for filtering seminars as {@code String}.
     * @return All seminars by the passed condition from the database as
     * {@code List<Seminar>}.
     * @throws Exception When seminars by the passed condition cannot be
     * retrieved.
     */
    private List<Seminar> getSeminarsByCondition(String condition) throws Exception {
        List<Seminar> seminarsFiltered = ServerController.getInstance().getSeminarsByCondition(condition);
        return seminarsFiltered;
    }

    /**
     * Retrieves seminar by the passed ID using the {@code ServerController}
     * instance.
     *
     * @param id ID used for filtering seminars as {@code int}.
     * @return Seminar found by the passed condition from the database as
     * {@code Seminar}.
     * @throws Exception When the seminar cannot be retrieved.
     */
    private Seminar getSeminarByID(int id) throws Exception {
        Seminar seminar = ServerController.getInstance().getSeminarByID(id);
        return seminar;
    }

    /**
     * Creates the participant using the {@code ServerController} instance.
     *
     * @param participant Participant that needs to be created as
     * {@code Seminar}.
     * @throws Exception When the participant cannot be created.
     */
    private void createParticipant(Participant participant) throws Exception {
        ServerController.getInstance().createParticipant(participant);
    }

    /**
     * Retrieves all participants using the {@code ServerController} instance.
     *
     * @return All participants from the database as {@code List<Participant>}.
     * @throws Exception When all participants cannot be retrieved.
     */
    private List<Participant> getAllParticipants() throws Exception {
        List<Participant> allParticipants = ServerController.getInstance().getAllParticipants();
        return allParticipants;
    }

    /**
     * Checks if the admin is already logged in.
     *
     * @param admin Admin that needs to be checked if he is already logged in.
     * @throws ServerValidationException If the admin is already logged in.
     */
    private void checkIfAdminIsAlreadyLoggedIn(Admin admin) throws ServerValidationException {
        if (server.checkIfAdminIsAlreadyLoggedIn(admin)) {
            throw new ServerValidationException("Већ сте пријављени");
        }
    }

    /**
     * Retrieves participants by the passed condition using the
     * {@code ServerController} instance.
     *
     * @return All participants by the passed condition from the database as
     * {@code List<Participant>}.
     * @param condition Condition used for filtering participants as
     * {@code String}.
     * @throws Exception When participants by the passed condition cannot be
     * retrieved.
     */
    private List<Participant> getParticipantsByCondition(String condition) throws Exception {
        List<Participant> participantsFiltered = ServerController.getInstance().getParticipantsByCondition(condition);
        return participantsFiltered;
    }

    /**
     * Retrieves participant by the passed ID using the {@code ServerController}
     * instance.
     *
     * @param id ID used for filtering participants as {@code int}.
     * @return Participant found by the passed condition from the database as
     * {@code Participant}.
     * @throws Exception When the participant cannot be retrieved.
     */
    private Participant getParticipantByID(int id) throws Exception {
        return ServerController.getInstance().getParticipantByID(id);
    }

    /**
     * Retrieves all educational institutions using the {@code ServerController}
     * instance.
     *
     * @return All educational institutions from the database as
     * {@code List<EducationalInstitution>}.
     * @throws Exception When all educational institutions cannot be retrieved.
     */
    private List<EducationalInstitution> getAllEducationalInstitutions() throws Exception {
        return (List<EducationalInstitution>) ServerController.getInstance().getAllEducationalInstitutions();
    }

    /**
     * Creates the passed seminar schedule using the {@code ServerController}
     * instance.
     *
     * @param seminarSchedule Seminar schedule that needs to be created as
     * {@code SeminarSchedule}.
     * @throws Exception When seminar schedule cannot be created.
     */
    private void createSeminarSchedule(SeminarSchedule seminarSchedule) throws Exception {
        ServerController.getInstance().createSeminarSchedule(seminarSchedule);
    }

    /**
     * Retrieves seminar schedules by the passed condition using the
     * {@code ServerController} instance.
     *
     * @param condition Condition used for filtering seminar schedules as
     * {@code String}.
     * @throws Exception When seminar schedules by the passed condition cannot
     * be retrieved.
     */
    private List<SeminarSchedule> getSeminarSchedulesByCondition(List<Object> condition) throws Exception {
        List<SeminarSchedule> seminarSchedulesFiltered = ServerController.getInstance().getSeminarSchedulesByCondition(condition);
        return seminarSchedulesFiltered;
    }

    /**
     * Retrieves seminar schedule by the passed ID using the
     * {@code ServerController} instance.
     *
     * @param id ID used for filtering seminar schedules as {@code int}.
     * @return All seminar schedules by the passed condition from the database
     * as {@code List<SeminarSchedule>}.
     * @throws Exception When the seminar schedule cannot be retrieved.
     */
    private SeminarSchedule getSeminarScheduleByID(int seminarScheduleID) throws Exception {
        return ServerController.getInstance().getSeminarScheduleByID(seminarScheduleID);
    }

    /**
     * Saves the seminar schedule using the {@code ServerController} instance.
     *
     * @param seminarSchedule Seminar schedule as {@code SeminarSchedule} that
     * needs to be saved.
     * @throws Exception When the seminar schedule cannot be saved.
     */
    private void saveSeminarSchedule(SeminarSchedule seminarSchedule) throws Exception {
        ServerController.getInstance().saveSeminarSchedule(seminarSchedule);
    }

    /**
     * Saves the seminar using the {@code ServerController} instance.
     *
     * @param seminar Seminar as {@code Seminar} that needs to be saved.
     * @throws Exception When the seminar cannot be saved.
     */
    private void saveSeminar(Seminar seminar) throws Exception {
        ServerController.getInstance().saveSeminar(seminar);
    }
}
