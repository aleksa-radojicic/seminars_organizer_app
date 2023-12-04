/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.participant;

import com.fon.common.domain.Admin;
import com.fon.common.domain.Participant;
import com.fon.common.domain.Sex;
import com.fon.common.utils.Utility;
import com.fon.server.repository.db.DbRepository;
import com.fon.server.repository.db.impl.RepositoryDbGeneric;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 *
 * @author Aleksa
 */
public class CreateParticipantSOTest {

    private CreateParticipantSO cpSO;
    private Participant participant;

    private GetParticipantByIDSO gpbidSO;

    @BeforeEach
    void setUp() {
        cpSO = new CreateParticipantSO();

        final int participantID = 1000;
        final String name = "testName";
        final String surname = "testSurname";
        Date dateBirth = new Date();
        final int createdByAdminID = 2;
        final Admin createdByAdmin = new Admin(createdByAdminID);
        participant = new Participant(participantID, name, surname, Sex.MALE, dateBirth, createdByAdmin);

        gpbidSO = new GetParticipantByIDSO();
    }

    @AfterEach
    void tearDown() {
        cpSO = null;
        participant = null;
        gpbidSO = null;
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> cpSO.preconditions(participant));
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> cpSO.preconditions(new Exception()));
    }

    @Test
    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> cpSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            //1) Create new participant
            assertDoesNotThrow(() -> cpSO.execute(participant));

            //2) Make sure participant is created
            assertDoesNotThrow(() -> gpbidSO.execute(participant.getID()));

            Participant pFromDb = gpbidSO.getElement();

            assertEquals(participant.getParticipantID(), pFromDb.getParticipantID());
            assertEquals(participant.getName(), pFromDb.getName());
            assertEquals(participant.getSurname(), pFromDb.getSurname());
            assertEquals(participant.getSex(), pFromDb.getSex());
            assertEquals(Utility.DATE_FORMAT.format(participant.getDateBirth()),
                    Utility.DATE_FORMAT.format(pFromDb.getDateBirth()));

            //3) Delete created participant within db transaction
            deleteCreatedParticipant();
        } catch (Exception e) {
            Logger.getLogger(CreateParticipantSOTest.class.getName()).log(Level.SEVERE, null, e);
            throw new AssertionError(e.getMessage());
        }
    }

    private void deleteCreatedParticipant() throws Exception {
        DbRepository repository = new RepositoryDbGeneric();

        try {
            String whereQuerySection = "WHERE " + participant.getQueryCondition();

            repository.connect();
            repository.delete(participant, whereQuerySection);
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
        participant.setParticipantID(1);

        assertThrowsExactly(java.sql.SQLIntegrityConstraintViolationException.class,
                () -> cpSO.execute(participant));
    }
}
