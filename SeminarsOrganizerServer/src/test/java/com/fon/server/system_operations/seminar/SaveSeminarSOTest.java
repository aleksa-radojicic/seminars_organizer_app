/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar;

import com.fon.common.domain.Admin;
import com.fon.common.domain.Seminar;
import com.fon.common.domain.SeminarTopic;
import com.fon.common.domain.State;
import com.fon.server.repository.db.DbRepository;
import com.fon.server.repository.db.impl.RepositoryDbGeneric;
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
public class SaveSeminarSOTest {

    private SaveSeminarSO ssSO;
    private Seminar seminar;

    GetSeminarByIDSO gsbidSO;
    CreateSeminarSO csSO;

    @BeforeEach
    void setUp() {
        ssSO = new SaveSeminarSO();

        int seminarID = 1001;
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
        csSO = new CreateSeminarSO();
    }

    @AfterEach
    void tearDown() {
        ssSO = null;
        seminar = null;

        gsbidSO = null;
        csSO = null;
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> ssSO.preconditions(seminar));
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> ssSO.preconditions(new Exception()));
    }

    @Test
    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> ssSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_unchanged() {
        try {
            //1) Create new seminar
            assertDoesNotThrow(() -> csSO.execute(seminar));

            //2) Update created seminar
            assertDoesNotThrow(() -> ssSO.execute(seminar));

            //3) Make sure seminar is updated
            assertDoesNotThrow(() -> gsbidSO.execute(seminar.getID()));
            Seminar sFromDb = gsbidSO.getSeminar();
            assertEquals(true, sFromDb.equalsAll(seminar));

            //4) Delete created seminar within db transaction
            deleteCreatedSeminar();
        } catch (Exception ex) {
            Logger.getLogger(SaveSeminarSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_seminarChanged() {
        try {
            //1) Create new seminar
            assertDoesNotThrow(() -> csSO.execute(seminar));

            //2) Update created seminar
            seminar.setName(seminar.getName() + "Other");
            seminar.setDescription(seminar.getDescription() + "Other");
            seminar.setState(State.CHANGED);
            assertDoesNotThrow(() -> ssSO.execute(seminar));

            //3) Make sure seminar is updated
            assertDoesNotThrow(() -> gsbidSO.execute(seminar.getID()));
            Seminar sFromDb = gsbidSO.getSeminar();
            assertEquals(true, sFromDb.equalsAll(seminar));

            //4) Delete created seminar within db transaction
            deleteCreatedSeminar();
        } catch (Exception ex) {
            Logger.getLogger(SaveSeminarSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_dTopics_addedTopic() {
        try {
            //1) Create new seminar
            assertDoesNotThrow(() -> csSO.execute(seminar));

            //2) Update created seminar
            SeminarTopic st4Added = new SeminarTopic(seminar, 4, "st4", "st name 4");
            st4Added.setState(State.CREATED);
            seminar.getSeminarTopics().add(st4Added);
            assertDoesNotThrow(() -> ssSO.execute(seminar));

            //3) Make sure seminar is updated
            assertDoesNotThrow(() -> gsbidSO.execute(seminar.getID()));
            Seminar sFromDb = gsbidSO.getSeminar();
            assertEquals(true, sFromDb.equalsAll(seminar));

            //4) Delete created seminar within db transaction
            deleteCreatedSeminar();
        } catch (Exception ex) {
            Logger.getLogger(SaveSeminarSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_dTopics_changedTopic() {
        try {
            //1) Create new seminar
            assertDoesNotThrow(() -> csSO.execute(seminar));

            //2) Update created seminar
            SeminarTopic st3Changed = seminar.getSeminarTopics().get(2);
            st3Changed.setName(st3Changed.getName() + "Other");
            st3Changed.setPresenter(st3Changed.getPresenter() + "Other");
            st3Changed.setState(State.CHANGED);
            assertDoesNotThrow(() -> ssSO.execute(seminar));

            //3) Make sure seminar is updated
            assertDoesNotThrow(() -> gsbidSO.execute(seminar.getID()));
            Seminar sFromDb = gsbidSO.getSeminar();
            assertEquals(true, sFromDb.equalsAll(seminar));

            //4) Delete created seminar within db transaction
            deleteCreatedSeminar();
        } catch (Exception ex) {
            Logger.getLogger(SaveSeminarSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_dTopics_deletedTopic() {
        try {
            //1) Create new seminar
            assertDoesNotThrow(() -> csSO.execute(seminar));

            //2) Update created seminar
            SeminarTopic st3Deleted = seminar.getSeminarTopics().get(2);
            st3Deleted.setState(State.DELETED);
            assertDoesNotThrow(() -> ssSO.execute(seminar));

            //3) Make sure seminar is updated
            assertDoesNotThrow(() -> gsbidSO.execute(seminar.getID()));
            Seminar sFromDb = gsbidSO.getSeminar();
            seminar.getSeminarTopics().remove(st3Deleted);
            assertEquals(true, sFromDb.equalsAll(seminar));

            //4) Delete created seminar within db transaction
            deleteCreatedSeminar();
        } catch (Exception ex) {
            Logger.getLogger(SaveSeminarSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_doesntExist() throws Exception {
        seminar.setSeminarID(-seminar.getSeminarID());
        assertDoesNotThrow(() -> ssSO.execute(seminar));
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
}
