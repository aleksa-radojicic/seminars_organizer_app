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
 *
 * @author Aleksa
 */
public class ServerController {

    private static ServerController instance;

    private ServerController() {
    }

    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    public Admin login(Admin admin) throws Exception {
        LoginSO loginSO = new LoginSO();
        loginSO.execute(admin);
        Admin loggedAdmin = loginSO.getLoggedAdmin();
        return loggedAdmin;
    }

    public void createSeminar(Seminar seminar) throws Exception {
        CreateSeminarSO createSeminarSO = new CreateSeminarSO();
        createSeminarSO.execute(seminar);
    }

    public List<Seminar> getAllSeminars() throws Exception {
        GetAllSeminarsSO gas = new GetAllSeminarsSO();
        gas.execute(null);
        List<Seminar> allSeminars = gas.getAllSeminars();
        return allSeminars;
    }

    public List<Seminar> getSeminarsByCondition(String condition) throws Exception {
        GetSeminarsByConditionSO gsbc = new GetSeminarsByConditionSO();
        gsbc.execute(condition);
        List<Seminar> allSeminars = gsbc.getSeminars();
        return allSeminars;
    }

    public Seminar getSeminarByID(int id) throws Exception {
        GetSeminarByIDSO gsbid = new GetSeminarByIDSO();
        gsbid.execute(id);
        Seminar seminar = gsbid.getSeminar();
        return seminar;
    }

    public void createParticipant(Participant participant) throws Exception {
        CreateParticipantSO cp = new CreateParticipantSO();
        cp.execute(participant);
    }

    public List<Participant> getAllParticipants() throws Exception {
        GetAllParticipantsSO gap = new GetAllParticipantsSO();
        gap.execute(null);
        List<Participant> allParticipants = gap.getAllParticipants();
        return allParticipants;
    }

    public List<Participant> getParticipantsByCondition(String condition) throws Exception {
        GetParticipantsByConditionSO gsbc = new GetParticipantsByConditionSO();
        gsbc.execute(condition);
        List<Participant> all = gsbc.getList();
        return all;
    }

    public Participant getParticipantByID(int id) throws Exception {
        GetParticipantByIDSO gsbid = new GetParticipantByIDSO();
        gsbid.execute(id);
        return gsbid.getElement();
    }

    public List<EducationalInstitution> getAllEducationalInstitutions() throws Exception {
        GetAllEducationalInstitutionsSO gas = new GetAllEducationalInstitutionsSO();
        gas.execute(null);
        return gas.getElement();
    }

    public void createSeminarSchedule(SeminarSchedule seminarSchedule) throws Exception {
        CreateSeminarScheduleSO css = new CreateSeminarScheduleSO();
        css.execute(seminarSchedule);
    }

    public List<SeminarSchedule> getSeminarSchedulesByCondition(List<Object> condition) throws Exception {
        GetSeminarSchedulesByConditionSO gssbc = new GetSeminarSchedulesByConditionSO();
        gssbc.execute(condition);
        List<SeminarSchedule> filteredSeminarSchedules = gssbc.getSeminarSchedules();
        return filteredSeminarSchedules;
    }

    public SeminarSchedule getSeminarScheduleByID(int seminarScheduleID) throws Exception {
        GetSeminarScheduleByIDSO gssbid = new GetSeminarScheduleByIDSO();
        gssbid.execute(seminarScheduleID);
        SeminarSchedule seminarSchedule = gssbid.getSeminarSchedule();
        return seminarSchedule;
    }

    public void saveSeminarSchedule(SeminarSchedule seminarSchedule) throws Exception {
        SaveSeminarScheduleSO sss = new SaveSeminarScheduleSO();
        sss.execute(seminarSchedule);
    }

    public void saveSeminar(Seminar seminar) throws Exception {
        SaveSeminarSO ss = new SaveSeminarSO();
        ss.execute(seminar);
    }
}
