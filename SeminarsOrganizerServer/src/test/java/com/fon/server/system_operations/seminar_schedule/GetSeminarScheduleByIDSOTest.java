/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar_schedule;

import com.fon.common.domain.EducationalInstitution;
import com.fon.common.domain.Participant;
import com.fon.common.domain.Seminar;
import com.fon.common.domain.SeminarEnrollment;
import com.fon.common.domain.SeminarSchedule;
import com.fon.common.utils.Utility;
import java.text.ParseException;
import java.util.Date;
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
public class GetSeminarScheduleByIDSOTest {

    private GetSeminarScheduleByIDSO gssbidSO;
    private SeminarSchedule seminarSchedule;

    private List<SeminarEnrollment> seminarEnrollments;

    private int filterID;

    @BeforeEach
    void setUp() throws ParseException {
        SeminarEnrollment se1 = new SeminarEnrollment(null, new Participant(1), "test");
        SeminarEnrollment se2 = new SeminarEnrollment(null, new Participant(2), "");

        seminarEnrollments = new LinkedList(List.of(se1, se2));

        int seminarScheduleID = 1;
        Date datetimeBegins = Utility.DATETIME_FORMAT.parse("23.12.2023 | 10:00");
        Date datetimeEnds = Utility.DATETIME_FORMAT.parse("23.12.2023 | 18:01");
        Seminar seminar = new Seminar(3, "MLOps: From zero to hero", "Embark on a journey from novice to expert in MLOps using latest solutions and practices", null, null);
        EducationalInstitution educationalInstitution = new EducationalInstitution(3, "IT Centre", "Oakwood 23");
        seminarSchedule = new SeminarSchedule(seminarScheduleID, datetimeBegins, datetimeEnds, null, seminar, educationalInstitution, seminarEnrollments);
        seminarEnrollments.forEach(x -> x.setSeminarSchedule(seminarSchedule));

        gssbidSO = new GetSeminarScheduleByIDSO();

        filterID = seminarScheduleID;
    }

    @AfterEach
    void tearDown() {
        seminarEnrollments = null;
        seminarSchedule = null;
        gssbidSO = null;
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> gssbidSO.preconditions(filterID));
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> gssbidSO.preconditions(new Exception()));
    }

    @Test
    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> gssbidSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_doesntExist() {
        try {
            assertDoesNotThrow(() -> gssbidSO.execute(-filterID));

            assertEquals(gssbidSO.getSeminarSchedule(), null);
        } catch (Exception ex) {
            Logger.getLogger(GetSeminarScheduleByIDSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            assertDoesNotThrow(() -> gssbidSO.execute(filterID));

            SeminarSchedule ssOther = gssbidSO.getSeminarSchedule();

            assertEquals(true, seminarSchedule.equalsAll(ssOther));
        } catch (Exception ex) {
            Logger.getLogger(GetSeminarScheduleByIDSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }
}
