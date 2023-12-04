/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar;

import com.fon.common.domain.Admin;
import com.fon.common.domain.Seminar;
import com.fon.common.domain.SeminarTopic;
import com.fon.server.repository.db.DbRepository;
import com.fon.server.repository.db.impl.RepositoryDbGeneric;
import java.util.LinkedList;
import java.util.List;
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
public class CreateSeminarSOTest {

    private CreateSeminarSO csSO;
    private Seminar seminar;

    private GetSeminarByIDSO gsbidSO;

    @BeforeEach
    void setUp() {
        csSO = new CreateSeminarSO();

        int seminarID = 1000;
        String name = "name";
        String description = "description";
        Admin createdByAdmin = new Admin(2);

        SeminarTopic st11 = new SeminarTopic(null, 1, "st1", "st name 1");
        SeminarTopic st12 = new SeminarTopic(null, 2, "st2", "st name 2");
        SeminarTopic st13 = new SeminarTopic(null, 3, "st3", "st name 3");
        List<SeminarTopic> seminarTopics = new LinkedList(List.of(st11, st12, st13));

        seminar = new Seminar(seminarID, name, description, createdByAdmin, seminarTopics);

        seminarTopics.forEach(x -> x.setSeminar(seminar));

        gsbidSO = new GetSeminarByIDSO();
    }

    @AfterEach
    void tearDown() {
        csSO = null;
        seminar = null;
        gsbidSO = null;
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> csSO.preconditions(seminar));
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> csSO.preconditions(new Exception()));
    }

    @Test

    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> csSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            //1) Create new seminar
            assertDoesNotThrow(() -> csSO.execute(seminar));

            //2) Make sure seminar is created
            assertDoesNotThrow(() -> gsbidSO.execute(seminar.getID()));
            Seminar sFromDb = gsbidSO.getSeminar();
            assertEquals(true, sFromDb.equalsAll(seminar));

            //3) Delete created seminar within db transaction
            deleteCreatedSeminar();
        } catch (Exception ex) {
            Logger.getLogger(CreateSeminarSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    private void deleteCreatedSeminar() throws Exception {
        DbRepository repository = new RepositoryDbGeneric();

        try {
            repository.connect();

            //Delete seminar topics
            for (SeminarTopic seminarTopic : seminar.getSeminarTopics()) {
                String whereSectionSeminarTopic = "WHERE " + seminar.getQueryCondition() + " AND " + seminarTopic.getQueryCondition();

                repository.delete(seminarTopic, whereSectionSeminarTopic);
            }

            //Delete seminar
            String whereSectionSeminar = "WHERE " + seminar.getQueryCondition();
            repository.delete(seminar, whereSectionSeminar);

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
        seminar.setSeminarID(1);

        assertThrowsExactly(java.sql.SQLIntegrityConstraintViolationException.class,
                () -> csSO.execute(seminar));
    }
}
