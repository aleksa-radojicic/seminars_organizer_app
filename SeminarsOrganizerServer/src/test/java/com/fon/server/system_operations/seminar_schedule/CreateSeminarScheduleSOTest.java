/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar_schedule;

import com.fon.common.domain.SeminarEnrollment;
import com.fon.common.domain.SeminarSchedule;
import com.fon.common.utils.IOJson;
import com.fon.server.repository.db.DbRepository;
import com.fon.server.repository.db.impl.RepositoryDbGeneric;
import java.text.ParseException;
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
public class CreateSeminarScheduleSOTest {

    private CreateSeminarScheduleSO cssSO;
    private SeminarSchedule seminarSchedule;

    private GetSeminarScheduleByIDSO gssbidSO;

    @BeforeEach
    void setUp() throws ParseException {
        cssSO = new CreateSeminarScheduleSO();

        seminarSchedule = (SeminarSchedule) IOJson.deserializeJson("seminar_schedule", SeminarSchedule.class);
        //in seminar_schedule.json is seminarScheduleID of seminar schedule that already exists
        //in db, thus it needs to be changed
        seminarSchedule.setSeminarScheduleID(1001);
        seminarSchedule.getSeminarEnrollments().forEach(x -> x.setSeminarSchedule(seminarSchedule));
    
        gssbidSO = new GetSeminarScheduleByIDSO();
    }

    @AfterEach
    void tearDown() {
        cssSO = null;
        seminarSchedule = null;
        gssbidSO = null;
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> cssSO.preconditions(seminarSchedule));
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> cssSO.preconditions(new Exception()));
    }

    @Test

    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> cssSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            //1) Create new seminar schedule
            assertDoesNotThrow(() -> cssSO.execute(seminarSchedule));

            //2) Make sure seminar schedule is created
            assertDoesNotThrow(() -> gssbidSO.execute(seminarSchedule.getID()));
            SeminarSchedule ssFromDb = gssbidSO.getSeminarSchedule();
            assertEquals(true, ssFromDb.equalsAll(seminarSchedule));

            //3) Delete created seminar schedule within db transaction
            deleteCreatedSeminarSchedule();
        } catch (Exception ex) {
            Logger.getLogger(CreateSeminarScheduleSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    private void deleteCreatedSeminarSchedule() throws Exception {
        DbRepository repository = new RepositoryDbGeneric();

        try {
            repository.connect();

            //Delete seminar enrollments
            for (SeminarEnrollment se : seminarSchedule.getSeminarEnrollments()) {
                String whereSectionSeminarEnrollment = String.format("WHERE seminarScheduleID = %d AND participantID = %d",
                        se.getSeminarSchedule().getSeminarScheduleID(), se.getParticipant().getParticipantID());

                repository.delete(se, whereSectionSeminarEnrollment);
            }

            //Delete seminar schedule
            String whereSectionSeminarSchedule = "WHERE " + seminarSchedule.getQueryCondition();
            repository.delete(seminarSchedule, whereSectionSeminarSchedule);

            repository.commit();
        } catch (Exception e) {
            repository.rollback();
            throw e;
        } finally {
            repository.disconnect();
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_alreadyExists() {
        seminarSchedule.setSeminarScheduleID(1);

        assertThrowsExactly(java.sql.SQLIntegrityConstraintViolationException.class,
                () -> cssSO.execute(seminarSchedule));
    }
}
