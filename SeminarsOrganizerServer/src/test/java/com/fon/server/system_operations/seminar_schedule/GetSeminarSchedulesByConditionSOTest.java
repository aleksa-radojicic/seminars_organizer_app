/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.seminar_schedule;

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
public class GetSeminarSchedulesByConditionSOTest {

    private GetSeminarSchedulesByConditionSO gssbcSO;
    private List filter;

    @BeforeEach
    void setUp() throws ParseException {
        gssbcSO = new GetSeminarSchedulesByConditionSO();
        String seminarName = "ops";
        Date date = Utility.DATE_FORMAT.parse("23.12.2023");
        filter = new LinkedList(List.of(seminarName, date));
    }

    @AfterEach
    void tearDown() {
        gssbcSO = null;
    }

    @Test
    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> gssbcSO.preconditions(new Exception()));
    }

    @Test
    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> gssbcSO.preconditions(null));
    }

    @Test
    void test_preconditions_largerList() {
        filter.add("yada");
        assertThrowsExactly(Exception.class,
                () -> gssbcSO.preconditions(filter));
    }

    @Test
    void test_preconditions_smallerList() {
        filter.remove(0);
        assertThrowsExactly(Exception.class,
                () -> gssbcSO.preconditions(filter));
    }

    @Test
    void test_preconditions_firstEl_null() {
        filter.set(0, null);
        assertThrowsExactly(Exception.class,
                () -> gssbcSO.preconditions(filter));
    }

    @Test
    void test_preconditions_firstEl_dClass() {
        filter.set(0, new Exception());
        assertThrowsExactly(Exception.class,
                () -> gssbcSO.preconditions(filter));
    }

    @Test
    void test_preconditions_secondEl_dClass() {
        filter.set(1, new Exception());
        assertThrowsExactly(Exception.class,
                () -> gssbcSO.preconditions(filter));
    }

    @Test
    void test_preconditions_secondEl_null() {
        filter.set(1, null);
        assertDoesNotThrow(() -> gssbcSO.preconditions(filter));
    }

    @Test
    void test_preconditions_secondEl_date() {
        assertDoesNotThrow(() -> gssbcSO.preconditions(filter));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_doesntExist() {
        try {
            filter.set(0, "noSeminarSchedulesWillBeFound");
            filter.set(1, null);

            assertDoesNotThrow(() -> gssbcSO.execute(filter));

            assertEquals(gssbcSO.getSeminarSchedules().isEmpty(), true);
        } catch (Exception ex) {
            Logger.getLogger(GetSeminarSchedulesByConditionSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            assertDoesNotThrow(() -> gssbcSO.execute(filter));

            List<SeminarSchedule> seminarSchedules = gssbcSO.getSeminarSchedules();

            assertEquals(seminarSchedules.isEmpty(), false);
            assertEquals(true, isValidParticipants(seminarSchedules, filter));
        } catch (Exception ex) {
            Logger.getLogger(GetSeminarSchedulesByConditionSOTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    private boolean isValidParticipants(List<SeminarSchedule> list, List filter) {
        String seminarName = (String) filter.get(0);
        Date date = (Date) filter.get(1);

        for (SeminarSchedule ss : list) {
            Date datetimeBegins = ss.getDatetimeBegins();
            datetimeBegins.setHours(0);
            datetimeBegins.setMinutes(0);
            Date datetimeEnds = ss.getDatetimeEnds();
            datetimeEnds.setHours(0);
            datetimeEnds.setMinutes(0);

            if (!ss.getSeminar().getName().contains(seminarName)
                    && !ss.getDatetimeBegins().equals(date)
                    && !ss.getDatetimeEnds().equals(date)) {
                return false;
            }
        }
        return true;
    }
}
