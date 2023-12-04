/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar_schedule;

import com.fon.common.domain.Admin;
import com.fon.common.domain.EducationalInstitution;
import com.fon.common.domain.Participant;
import com.fon.common.domain.Seminar;
import com.fon.common.domain.SeminarEnrollment;
import com.fon.common.domain.SeminarSchedule;
import com.fon.common.domain.State;
import com.fon.common.utils.Utility;
import com.fon.server.repository.db.DbRepository;
import com.fon.server.repository.db.impl.RepositoryDbGeneric;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 *
 * @author Aleksa
 */
public class SaveSeminarScheduleSOTest {

    private SaveSeminarScheduleSO sssSO;
    private SeminarSchedule seminarSchedule;

    GetSeminarScheduleByIDSO gssbidSO;
    CreateSeminarScheduleSO cssSO;

    @BeforeEach
    void setUp() throws ParseException {
        sssSO = new SaveSeminarScheduleSO();

        final int seminarScheduleID = 1001;
        final Date datetimeBegins = Utility.DATETIME_FORMAT.parse("01.01.1980 | 13:01");
        final Date datetimeEnds = Utility.DATETIME_FORMAT.parse("01.01.1980 | 14:01");
        final int createdByAdminID = 1;
        final Admin createdByAdmin = new Admin(createdByAdminID);

        final int seminarID = 1;
        final Seminar seminar = new Seminar(seminarID);
        final int eiID = 1;
        final EducationalInstitution educationalInstitution = new EducationalInstitution(eiID);

        SeminarEnrollment se1 = new SeminarEnrollment(null, new Participant(5), "notes1");
        SeminarEnrollment se2 = new SeminarEnrollment(null, new Participant(6), "notes2");
        List<SeminarEnrollment> seminarEnrollments = new LinkedList(Arrays.asList(se1, se2));

        seminarSchedule = new SeminarSchedule(seminarScheduleID, datetimeBegins, datetimeEnds, createdByAdmin, seminar, educationalInstitution, seminarEnrollments);

        seminarEnrollments.forEach(x -> x.setSeminarSchedule(seminarSchedule));

        gssbidSO = new GetSeminarScheduleByIDSO();
        cssSO = new CreateSeminarScheduleSO();
    }

    @AfterEach
    void tearDown() {
        sssSO = null;
        seminarSchedule = null;

        gssbidSO = null;
        cssSO = null;
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> sssSO.preconditions(seminarSchedule));
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> sssSO.preconditions(new Exception()));
    }

    @Test
    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> sssSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_unchanged() {
        try {
            //1) Create new seminar schedule
            assertDoesNotThrow(() -> cssSO.execute(seminarSchedule));

            //2) Update created seminar schedule
            assertDoesNotThrow(() -> sssSO.execute(seminarSchedule));

            //3) Make sure seminar schedule is updated
            assertDoesNotThrow(() -> gssbidSO.execute(seminarSchedule.getID()));
            SeminarSchedule ssFromDb = gssbidSO.getSeminarSchedule();
            assertEquals(true, ssFromDb.equalsAll(seminarSchedule));

            //4) Delete created seminar schedule within db transaction
            deleteCreatedSeminarSchedule();
        } catch (Exception ex) {
            Logger.getLogger(SaveSeminarScheduleSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_seminarScheduleChanged() {
        try {
            //1) Create new seminar schedule
            assertDoesNotThrow(() -> cssSO.execute(seminarSchedule));

            //2) Update created seminar schedule
            final Date datetimeBeginsOther = Utility.DATETIME_FORMAT.parse("01.01.1981 | 13:01");
            final Date datetimeEndsOther = Utility.DATETIME_FORMAT.parse("01.01.1981 | 14:01");
            seminarSchedule.setDatetimeBegins(datetimeBeginsOther);
            seminarSchedule.setDatetimeEnds(datetimeEndsOther);
            seminarSchedule.setState(State.CHANGED);
            assertDoesNotThrow(() -> sssSO.execute(seminarSchedule));

            //3) Make sure seminar schedule is updated
            assertDoesNotThrow(() -> gssbidSO.execute(seminarSchedule.getID()));
            SeminarSchedule ssFromDb = gssbidSO.getSeminarSchedule();
            assertEquals(true, ssFromDb.equalsAll(seminarSchedule));

            //4) Delete created seminar schedule within db transaction
            deleteCreatedSeminarSchedule();
        } catch (Exception ex) {
            Logger.getLogger(SaveSeminarScheduleSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_dEnrollments_addedEnrollment() {
        try {
            //1) Create new seminar schedule
            assertDoesNotThrow(() -> cssSO.execute(seminarSchedule));

            //2) Update created seminar schedule
            SeminarEnrollment se4Added = new SeminarEnrollment(seminarSchedule, new Participant(7), "");
            se4Added.setState(State.CREATED);
            seminarSchedule.getSeminarEnrollments().add(se4Added);
            assertDoesNotThrow(() -> sssSO.execute(seminarSchedule));

            //3) Make sure seminar schedule is updated
            assertDoesNotThrow(() -> gssbidSO.execute(seminarSchedule.getID()));
            SeminarSchedule ssFromDb = gssbidSO.getSeminarSchedule();
            assertEquals(true, ssFromDb.equalsAll(seminarSchedule));

            //4) Delete created seminar schedule within db transaction
            deleteCreatedSeminarSchedule();
        } catch (Exception ex) {
            Logger.getLogger(SaveSeminarScheduleSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_dEnrollments_deletedEnrollment() {
        try {
            //1) Create new seminar schedule
            assertDoesNotThrow(() -> cssSO.execute(seminarSchedule));

            //2) Update created seminar schedule
            SeminarEnrollment se1Deleted = seminarSchedule.getSeminarEnrollments().get(0);
            se1Deleted.setState(State.DELETED);
            assertDoesNotThrow(() -> sssSO.execute(seminarSchedule));

            //3) Make sure seminar schedule is updated
            assertDoesNotThrow(() -> gssbidSO.execute(seminarSchedule.getID()));
            SeminarSchedule ssFromDb = gssbidSO.getSeminarSchedule();
            seminarSchedule.getSeminarEnrollments().remove(se1Deleted);
            assertEquals(true, ssFromDb.equalsAll(seminarSchedule));

            //4) Delete created seminar schedule within db transaction
            deleteCreatedSeminarSchedule();
        } catch (Exception ex) {
            Logger.getLogger(SaveSeminarScheduleSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_doesntExist() throws Exception {
        seminarSchedule.setSeminarScheduleID(-seminarSchedule.getSeminarScheduleID());
        assertDoesNotThrow(() -> sssSO.execute(seminarSchedule));
    }

    private void deleteCreatedSeminarSchedule() throws Exception {
        DbRepository repository = new RepositoryDbGeneric();

        try {
            repository.connect();

            //Delete seminar enrollments
            for (SeminarEnrollment seminarEnrollment : seminarSchedule.getSeminarEnrollments()) {
                String whereSectionSeminarEnrollment = String.format("WHERE seminarScheduleID = %d AND participantID = %d",
                        seminarEnrollment.getSeminarSchedule().getSeminarScheduleID(), seminarEnrollment.getParticipant().getParticipantID());

                repository.delete(seminarEnrollment, whereSectionSeminarEnrollment);
            }

            //Delete seminar schedule
            String whereSectionSeminar = "WHERE " + seminarSchedule.getQueryCondition();
            repository.delete(seminarSchedule, whereSectionSeminar);

            repository.commit();
        } catch (Exception e) {
            repository.rollback();
            throw e;
        } finally {
            repository.disconnect();
        }
    }
}
