/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar;

import com.fon.common.domain.Seminar;
import com.fon.common.domain.SeminarTopic;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 *
 * @author Aleksa
 */
public class GetSeminarByIDSOTest {

    private GetSeminarByIDSO gsbidSO;
    private Seminar seminar;
    private final int seminarID = 1;
    private final String name = "ML model data integration";
    private final String description = "Delve into the craft of blending varied data origins to construct formidable machine learning model";
    private List<SeminarTopic> seminarTopics;

    private final int filterID = seminarID;

    @BeforeEach
    void setUp() throws ParseException {
        SeminarTopic st1 = new SeminarTopic(null, 1, "Topic1", "Presenter1");
        SeminarTopic st2 = new SeminarTopic(null, 2, "Topic2", "Presenter2");
        SeminarTopic st3 = new SeminarTopic(null, 3, "Topic3", "Presenter3");
        seminarTopics = new LinkedList(List.of(st1, st2, st3));
        seminar = new Seminar(seminarID, name, description, null, seminarTopics);
        seminarTopics.forEach(x -> x.setSeminar(seminar));

        gsbidSO = new GetSeminarByIDSO();
    }

    @AfterEach
    void tearDown() {
        seminarTopics = null;
        seminar = null;
        gsbidSO = null;
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> gsbidSO.preconditions(filterID));
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> gsbidSO.preconditions(new Exception()));
    }

    @Test
    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> gsbidSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_doesntExist() {
        try {
            assertDoesNotThrow(() -> gsbidSO.executeOperation(-filterID));

            assertEquals(gsbidSO.getSeminar(), null);
        } catch (Exception ex) {
            Logger.getLogger(GetSeminarByIDSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            assertDoesNotThrow(() -> gsbidSO.executeOperation(filterID));

            Seminar sOther = gsbidSO.getSeminar();

            assertEquals(true, seminar.equalsAll(sOther));
        } catch (Exception ex) {
            Logger.getLogger(GetSeminarByIDSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }
}
