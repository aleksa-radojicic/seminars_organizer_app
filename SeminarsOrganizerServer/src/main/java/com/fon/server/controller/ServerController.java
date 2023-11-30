package com.fon.server.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.fon.common.domain.*;
import java.util.List;
import com.fon.server.system_operations.participant.CreateParticipantSO;
import com.fon.server.system_operations.seminar.CreateSeminarSO;
import com.fon.server.system_operations.seminar_schedule.CreateSeminarScheduleSO;
import com.fon.server.system_operations.educational_institution.GetAllEducationalInstitutionsSO;
import com.fon.server.system_operations.participant.GetAllParticipantsSO;
import com.fon.server.system_operations.seminar.GetAllSeminarsSO;
import com.fon.server.system_operations.participant.GetParticipantByIDSO;
import com.fon.server.system_operations.participant.GetParticipantsByConditionSO;
import com.fon.server.system_operations.seminar.GetSeminarByIDSO;
import com.fon.server.system_operations.seminar_schedule.GetSeminarScheduleByIDSO;
import com.fon.server.system_operations.seminar_schedule.GetSeminarSchedulesByConditionSO;
import com.fon.server.system_operations.seminar.GetSeminarsByConditionSO;
import com.fon.server.system_operations.admin.LoginSO;
import com.fon.server.system_operations.seminar.SaveSeminarSO;
import com.fon.server.system_operations.seminar_schedule.SaveSeminarScheduleSO;

/**
 * Singleton class that manages server-side operations by executing specified
 * system operations.
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class ServerController {

    /**
     * The singleton instance of the {@code ServerController} class.
     */
    private static ServerController instance;

    /**
     * Private non-parametric constructor.
     */
    private ServerController() {
    }

    /**
     * Getter of instance (Singleton pattern).
     *
     * @return The singleton instance of the {@code ServerController} class.
     */
    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    /**
     * Responsible for executing {@code LoginSO}.
     *
     * @param admin Admin that needs to be checked if he entered correct
     * credentials as {@code Admin}.
     * @return Logged admin as {@code Admin} with all data.
     * @throws Exception When admin cannot be logged in.
     */
    public Admin login(Admin admin) throws Exception {
        LoginSO loginSO = new LoginSO();
        loginSO.execute(admin);
        Admin loggedAdmin = loginSO.getLoggedAdmin();
        return loggedAdmin;
    }

    /**
     * Responsible for executing {@code CreateSeminarSO}.
     *
     * @param seminar Seminar that needs to be created as {@code Seminar}.
     * @throws Exception When the seminar cannot be created.
     */
    public void createSeminar(Seminar seminar) throws Exception {
        CreateSeminarSO createSeminarSO = new CreateSeminarSO();
        createSeminarSO.execute(seminar);
    }

    /**
     * Responsible for executing {@code GetAllSeminarsSO}.
     *
     * @return All seminars from the database as {@code List<Seminar>}.
     * @throws Exception When all seminars cannot be retrieved.
     */
    public List<Seminar> getAllSeminars() throws Exception {
        GetAllSeminarsSO gas = new GetAllSeminarsSO();
        gas.execute(null);
        List<Seminar> allSeminars = gas.getAllSeminars();
        return allSeminars;
    }

    /**
     * Responsible for executing {@code GetSeminarsByConditionSO}.
     *
     * @param condition Condition used for filtering seminars as {@code String}.
     * @return All seminars by the passed condition from the database as
     * {@code List<Seminar>}.
     * @throws Exception Exception When seminars by the passed condition cannot
     * be retrieved.
     */
    public List<Seminar> getSeminarsByCondition(String condition) throws Exception {
        GetSeminarsByConditionSO gsbc = new GetSeminarsByConditionSO();
        gsbc.execute(condition);
        List<Seminar> allSeminars = gsbc.getSeminars();
        return allSeminars;
    }

    /**
     * Responsible for executing {@code GetSeminarByIDSO}.
     *
     * @param id ID used for filtering seminars as {@code int}.
     * @return Seminar found by the passed condition from the database as
     * {@code Seminar}.
     * @throws Exception When the seminar cannot be retrieved.
     */
    public Seminar getSeminarByID(int id) throws Exception {
        GetSeminarByIDSO gsbid = new GetSeminarByIDSO();
        gsbid.execute(id);
        Seminar seminar = gsbid.getSeminar();
        return seminar;
    }

    /**
     * Responsible for executing {@code CreateParticipantSO}.
     *
     * @param participant Participant that needs to be created as
     * {@code Participant}.
     * @throws Exception When the participant cannot be created.
     */
    public void createParticipant(Participant participant) throws Exception {
        CreateParticipantSO cp = new CreateParticipantSO();
        cp.execute(participant);
    }

    /**
     * Responsible for executing {@code GetAllParticipantsSO}.
     *
     * @return All participants from the database as {@code List<Participant>}.
     * @throws Exception When all participants cannot be retrieved.
     */
    public List<Participant> getAllParticipants() throws Exception {
        GetAllParticipantsSO gap = new GetAllParticipantsSO();
        gap.execute(null);
        List<Participant> allParticipants = gap.getAllParticipants();
        return allParticipants;
    }

    /**
     * Responsible for executing {@code GetParticipantsByConditionSO}.
     *
     * @param condition Condition used for filtering participants as
     * {@code String}.
     * @return All participants by the passed condition from the database as
     * {@code List<Participant>}.
     * @throws Exception When participants by the passed condition cannot be
     * retrieved.
     */
    public List<Participant> getParticipantsByCondition(String condition) throws Exception {
        GetParticipantsByConditionSO gsbc = new GetParticipantsByConditionSO();
        gsbc.execute(condition);
        List<Participant> all = gsbc.getList();
        return all;
    }

    /**
     * Responsible for executing {@code GetParticipantByIDSO}.
     *
     * @param id ID used for filtering participants as {@code int}.
     * @return Participant found by the passed condition from the database as
     * {@code Participant}.
     * @throws Exception When the participant cannot be retrieved.
     */
    public Participant getParticipantByID(int id) throws Exception {
        GetParticipantByIDSO gsbid = new GetParticipantByIDSO();
        gsbid.execute(id);
        return gsbid.getElement();
    }

    /**
     * Responsible for executing {@code GetAllEducationalInstitutionsSO}.
     *
     * @return All educational institutions from the database as
     * {@code List<EducationalInstitution>}.
     * @throws Exception When all educational institutions cannot be retrieved.
     */
    public List<EducationalInstitution> getAllEducationalInstitutions() throws Exception {
        GetAllEducationalInstitutionsSO gas = new GetAllEducationalInstitutionsSO();
        gas.execute(null);
        return gas.getElement();
    }

    /**
     * Responsible for executing {@code CreateSeminarScheduleSO}.
     *
     * @param seminarSchedule Seminar schedule that needs to be created as
     * {@code SeminarSchedule}.
     * @throws Exception When the seminar schedule cannot be created.
     */
    public void createSeminarSchedule(SeminarSchedule seminarSchedule) throws Exception {
        CreateSeminarScheduleSO css = new CreateSeminarScheduleSO();
        css.execute(seminarSchedule);
    }

    /**
     * Responsible for executing {@code GetSeminarSchedulesByConditionSO}.
     *
     * @param condition Condition used for filtering seminar schedules as
     * {@code String}.
     * @return All seminar schedules by the passed condition from the database
     * as {@code List<SeminarSchedule>}.
     * @throws Exception When the seminar schedule cannot be retrieved.
     */
    public List<SeminarSchedule> getSeminarSchedulesByCondition(List<Object> condition) throws Exception {
        GetSeminarSchedulesByConditionSO gssbc = new GetSeminarSchedulesByConditionSO();
        gssbc.execute(condition);
        List<SeminarSchedule> filteredSeminarSchedules = gssbc.getSeminarSchedules();
        return filteredSeminarSchedules;
    }

    /**
     * Responsible for executing {@code GetSeminarScheduleByIDSO}.
     *
     * @param id ID used for filtering seminar schedules as {@code int}.
     * @return Seminar schedule found by the passed condition from the database
     * as {@code SeminarSchedule}.
     * @throws Exception When the seminar schedule cannot be retrieved.
     */
    public SeminarSchedule getSeminarScheduleByID(int id) throws Exception {
        GetSeminarScheduleByIDSO gssbid = new GetSeminarScheduleByIDSO();
        gssbid.execute(id);
        SeminarSchedule seminarSchedule = gssbid.getSeminarSchedule();
        return seminarSchedule;
    }

    /**
     * Responsible for executing {@code SaveSeminarScheduleSO}.
     *
     * @param seminarSchedule Seminar schedule as {@code SeminarSchedule} that
     * needs to be saved.
     * @throws Exception When the seminar schedule cannot be saved.
     */
    public void saveSeminarSchedule(SeminarSchedule seminarSchedule) throws Exception {
        SaveSeminarScheduleSO sss = new SaveSeminarScheduleSO();
        sss.execute(seminarSchedule);
    }

    /**
     * Responsible for executing {@code SaveSeminarSO}.
     *
     * @param seminar Seminar as {@code Seminar} that needs to be saved.
     * @throws Exception When the seminar cannot be saved.
     */
    public void saveSeminar(Seminar seminar) throws Exception {
        SaveSeminarSO ss = new SaveSeminarSO();
        ss.execute(seminar);
    }
}
