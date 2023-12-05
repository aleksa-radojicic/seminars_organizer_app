/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import com.fon.common.utils.IOJson;
import com.fon.common.utils.Utility;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Aleksa
 */
public class SeminarScheduleTest extends GenericEntityTest {

    private static SeminarSchedule seminarSchedule;

    private static int IDOther;
    private static Date datetimeBeginsOther;
    private static Date datetimeEndsOther;
    private static Admin createdByAdminOther;
    private static Seminar seminarOther;
    private static EducationalInstitution educationalInstitutionOther;

    private static void initializeSeminarSchedule() throws ParseException {
        seminarSchedule = (SeminarSchedule) IOJson.deserializeJson("seminar_schedule", SeminarSchedule.class);
        seminarSchedule.getSeminarEnrollments().forEach(x -> x.setSeminarSchedule(seminarSchedule));

        IDOther = seminarSchedule.getSeminarScheduleID() + 1;
        datetimeBeginsOther = Utility.DATETIME_FORMAT.parse("01.02.2024 | 15:00");
        datetimeEndsOther = Utility.DATETIME_FORMAT.parse("01.02.2024 | 15:30");
        createdByAdminOther = new Admin(seminarSchedule.getCreatedByAdmin().getAdminID() + 1);
        seminarOther = new Seminar(seminarSchedule.getSeminar().getSeminarID() + 1);
        educationalInstitutionOther = new EducationalInstitution(seminarSchedule.getEducationalInstitution().getEducationalInstitutionID() + 1);
    }

    @BeforeEach
    void setUp() throws ParseException {
        initializeSeminarSchedule();
        genericEntity = seminarSchedule;
    }

    @AfterEach
    void tearDown() {
        seminarSchedule = null;
    }

    @Test
    void test_getAttributeNames() {
        super.test_getAttributeNames("seminarScheduleID, datetimeBegins, datetimeEnds, createdByAdminID, seminarID, educationalInstitutionID");
    }

    @Test
    void test_getAttributeValues() {
        String result = String.format("%d, '%s', '%s', %d, %d, %d",
                seminarSchedule.getSeminarScheduleID(),
                new java.sql.Timestamp(seminarSchedule.getDatetimeBegins().getTime()),
                new java.sql.Timestamp(seminarSchedule.getDatetimeEnds().getTime()),
                seminarSchedule.getCreatedByAdmin().getAdminID(),
                seminarSchedule.getSeminar().getSeminarID(),
                seminarSchedule.getEducationalInstitution().getEducationalInstitutionID());
        super.test_getAttributeValues(result);
    }

    @Test
    void test_getID() {
        super.test_getID(seminarSchedule.getSeminarScheduleID());
    }

    @Test
    void test_setID() {
        super.test_setID(seminarSchedule.getSeminarScheduleID());
    }

    @Test
    void test_setAttributeValues() {
        String result = String.format("datetimeBegins = '%s', datetimeEnds = '%s', seminarID = %d",
                new java.sql.Timestamp(seminarSchedule.getDatetimeBegins().getTime()),
                new java.sql.Timestamp(seminarSchedule.getDatetimeEnds().getTime()),
                seminarSchedule.getSeminar().getSeminarID());
        super.test_setAttributeValues(result);
    }

    @Test
    void test_getSelectAllQuery() {
        String selectAllQuery = """
               SELECT ss.`seminarScheduleID`, ss.`datetimeBegins`, ss.`datetimeEnds`, s.`seminarID`, s.`name` as 's.name', s.`description`, ei.`educationalInstitutionID`, ei.`name` as 'ei.name', ei.`address`
               FROM `seminarschedules` ss JOIN `educationalinstitutions` ei ON ss.`educationalInstitutionID` = ei.`educationalInstitutionID`
               JOIN `seminars` s ON ss.`seminarID` = s.`seminarID`""";
        super.test_getSelectAllQuery(selectAllQuery);
    }

    @Test
    void test_getEntityFromResultSet() {
        try {
            ResultSet rs = mock(ResultSet.class);

            when(rs.getInt("ss.seminarScheduleID")).thenReturn(seminarSchedule.getSeminarScheduleID());
            when(rs.getTimestamp("datetimeBegins")).thenReturn(new java.sql.Timestamp(seminarSchedule.getDatetimeBegins().getTime()));
            when(rs.getTimestamp("datetimeEnds")).thenReturn(new java.sql.Timestamp(seminarSchedule.getDatetimeEnds().getTime()));
            when(rs.getInt("seminarID")).thenReturn(seminarSchedule.getSeminar().getSeminarID());
            when(rs.getString("s.name")).thenReturn(seminarSchedule.getSeminar().getName());
            when(rs.getString("description")).thenReturn(seminarSchedule.getSeminar().getDescription());
            when(rs.getInt("educationalInstitutionID")).thenReturn(seminarSchedule.getEducationalInstitution().getEducationalInstitutionID());
            when(rs.getString("ei.name")).thenReturn(seminarSchedule.getEducationalInstitution().getName());
            when(rs.getString("ei.address")).thenReturn(seminarSchedule.getEducationalInstitution().getAddress());
            SeminarSchedule ssFromRS = (SeminarSchedule) seminarSchedule.getEntityFromResultSet(rs);

            assertEquals(true, seminarSchedule.equalsAllWithoutSeminarEnrollments(ssFromRS));
        } catch (SQLException ex) {
            Logger.getLogger(SeminarScheduleTest.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition(String.format("seminarScheduleID = %d ", seminarSchedule.getSeminarScheduleID()));
    }

    @Test
    void test_getState() {
        assertEquals(State.UNCHANGED, seminarSchedule.getState());
    }

    //tests for class specific methods
    @Test
    void test_setSeminarScheduleID() {
        seminarSchedule.setSeminarScheduleID(IDOther);
        assertEquals(seminarSchedule.getSeminarScheduleID(), IDOther);
    }

    @Test
    void test_setDatetimeBegins() {
        seminarSchedule.setDatetimeBegins(datetimeBeginsOther);
        assertEquals(seminarSchedule.getDatetimeBegins(), datetimeBeginsOther);
    }

    @Test
    void test_setDatetimeBegins_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarSchedule.setDatetimeBegins(null));
    }

    @Test
    void test_setDatetimeBegins_pastDate() throws ParseException {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> seminarSchedule.setDatetimeBegins(Utility.GET_DATE_PAST()));
    }

    @Test
    void test_setDatetimeEnds() {
        seminarSchedule.setDatetimeEnds(datetimeEndsOther);
        assertEquals(seminarSchedule.getDatetimeEnds(), datetimeEndsOther);
    }

    @Test
    void test_setDatetimeEnds_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarSchedule.setDatetimeEnds(null));
    }

    @Test
    void test_setDatetimeEnds_pastDate() throws ParseException {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> seminarSchedule.setDatetimeEnds(Utility.GET_DATE_PAST()));
    }

    @Test
    void test_setDatetimeEnds_beforeDatetimeBegins() throws ParseException {
        Date datetimeEndsBeforeDatetimeBegins = Utility.DATETIME_FORMAT.parse("01.02.2024 | 11:30");
        assertThrowsExactly(IllegalArgumentException.class,
                () -> seminarSchedule.setDatetimeEnds(datetimeEndsBeforeDatetimeBegins));
    }

    @Test
    void test_setCreatedByAdmin() {
        seminarSchedule.setCreatedByAdmin(createdByAdminOther);
        assertEquals(seminarSchedule.getCreatedByAdmin(), createdByAdminOther);
    }

    @Test
    void test_setCreatedByAdmin_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarSchedule.setCreatedByAdmin(null));
    }

    @Test
    void test_setSeminar() {
        seminarSchedule.setSeminar(seminarOther);
        assertEquals(seminarSchedule.getSeminar(), seminarOther);
    }

    @Test
    void test_setSeminar_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarSchedule.setSeminar(null));
    }

    @Test
    void test_setEducationalInstitution() {
        seminarSchedule.setEducationalInstitution(educationalInstitutionOther);
        assertEquals(seminarSchedule.getEducationalInstitution(), educationalInstitutionOther);
    }

    @Test
    void test_setEducationalInstitution_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarSchedule.setEducationalInstitution(null));
    }

    @Test
    void test_setSeminarEnrollments() {
        List<SeminarEnrollment> seminarEnrollments = seminarSchedule.getSeminarEnrollments();
        seminarSchedule.setSeminarEnrollments(seminarEnrollments);
        assertEquals(seminarEnrollments, seminarSchedule.getSeminarEnrollments());
    }

    @Test
    void test_setSeminarEnrollments_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarSchedule.setSeminarEnrollments(null));
    }

    @Test
    void test_setState() {
        seminarSchedule.setState(State.CREATED);
        assertEquals(State.CREATED, seminarSchedule.getState());
    }

    @Test
    void test_setState_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarOther.setState(null));
    }

    @Test
    void test_equals_sameObject() {
        assertTrue(seminarSchedule.equals(seminarSchedule));
    }

    @Test
    void test_equals_null() {
        assertFalse(seminarSchedule.equals(null));
    }

    @Test
    void test_equals_differentClass() {
        assertFalse(seminarSchedule.equals(new Exception()));
    }

    /*
    Cases checked:
    -> all equal
    -> all equal but seminarScheduleID
    -> all equal but datetimeBegins
    -> all equal but datetimeEnds
    -> all equal but createdByAdmin
    -> all equal but seminar  
    -> all equal but educationalInstitution  
    -> all equal but seminarEnrollments sizes differ - other is larger  
    -> all equal but seminarEnrollments sizes differ - other is smaller  
     */
    @ParameterizedTest
    @MethodSource("equalsProvider")
    void test_equals(SeminarSchedule ssOther, boolean result) {
        assertEquals(result, seminarSchedule.equals(ssOther));
    }

    static Stream<Arguments> equalsProvider() throws Exception {
        initializeSeminarSchedule();
        return Stream.of(
                arguments(Utility.clone(seminarSchedule), true),
                arguments(cloneSeminarScheduleAndModify("seminarScheduleID"), false),
                arguments(cloneSeminarScheduleAndModify("datetimeBegins"), true),
                arguments(cloneSeminarScheduleAndModify("datetimeEnds"), true),
                arguments(cloneSeminarScheduleAndModify("createdByAdmin"), true),
                arguments(cloneSeminarScheduleAndModify("seminar"), true),
                arguments(cloneSeminarScheduleAndModify("educationalInstitution"), true),
                arguments(cloneSeminarScheduleAndModify("seminarEnrollmentsLarger"), true),
                arguments(cloneSeminarScheduleAndModify("seminarEnrollmentsSmaller"), true)
        );
    }

    @Test
    void test_equalsAllWithoutSeminarEnrollments_sameObject() {
        assertTrue(seminarSchedule.equalsAllWithoutSeminarEnrollments(seminarSchedule));
    }

    @Test
    void test_equalsAllWithoutSeminarEnrollments_null() {
        assertFalse(seminarSchedule.equalsAllWithoutSeminarEnrollments(null));
    }

    @Test
    void test_equalsAllWithoutSeminarEnrollments_differentClass() {
        assertFalse(seminarSchedule.equalsAllWithoutSeminarEnrollments(new Exception()));
    }

    @ParameterizedTest
    @MethodSource("equalsAllWithoutSeminarEnrollmentsProvider")
    void test_equalsAllWithoutSeminarEnrollments(SeminarSchedule ssOther, boolean result) {
        assertEquals(result, seminarSchedule.equalsAll(ssOther));
    }

    static Stream<Arguments> equalsAllWithoutSeminarEnrollmentsProvider() throws Exception {
        initializeSeminarSchedule();
        return Stream.of(
                arguments(Utility.clone(seminarSchedule), true),
                arguments(cloneSeminarScheduleAndModify("seminarScheduleID"), false),
                arguments(cloneSeminarScheduleAndModify("datetimeBegins"), false),
                arguments(cloneSeminarScheduleAndModify("datetimeEnds"), false),
                arguments(cloneSeminarScheduleAndModify("createdByAdmin"), true),
                arguments(cloneSeminarScheduleAndModify("seminar"), false),
                arguments(cloneSeminarScheduleAndModify("educationalInstitution"), false)
        );
    }

    @Test
    void test_equalsAll_sameObject() {
        assertTrue(seminarSchedule.equalsAll(seminarSchedule));
    }

    @Test
    void test_equalsAll_null() {
        assertFalse(seminarSchedule.equalsAll(null));
    }

    @Test
    void test_equalsAll_differentClass() {
        assertFalse(seminarSchedule.equalsAll(new Exception()));
    }

    @ParameterizedTest
    @MethodSource("equalsAllProvider")
    void test_equalsAll(SeminarSchedule ssOther, boolean result) {
        assertEquals(result, seminarSchedule.equalsAll(ssOther));
    }

    static Stream<Arguments> equalsAllProvider() throws Exception {
        initializeSeminarSchedule();
        return Stream.of(
                arguments(Utility.clone(seminarSchedule), true),
                arguments(cloneSeminarScheduleAndModify("seminarScheduleID"), false),
                arguments(cloneSeminarScheduleAndModify("datetimeBegins"), false),
                arguments(cloneSeminarScheduleAndModify("datetimeEnds"), false),
                arguments(cloneSeminarScheduleAndModify("createdByAdmin"), true),
                arguments(cloneSeminarScheduleAndModify("seminar"), false),
                arguments(cloneSeminarScheduleAndModify("educationalInstitution"), false),
                arguments(cloneSeminarScheduleAndModify("seminarEnrollmentsLarger"), false),
                arguments(cloneSeminarScheduleAndModify("seminarEnrollmentsSmaller"), false)
        );
    }

    public static SeminarSchedule cloneSeminarScheduleAndModify(String attribute) throws IOException, ClassNotFoundException {
        SeminarSchedule modifiedSs = Utility.clone(seminarSchedule);

        switch (attribute) {
            case "seminarScheduleID" ->
                modifiedSs.setSeminarScheduleID(IDOther);
            case "datetimeBegins" ->
                modifiedSs.setDatetimeBegins(datetimeBeginsOther);
            case "datetimeEnds" ->
                modifiedSs.setDatetimeEnds(datetimeEndsOther);
            case "createdByAdmin" ->
                modifiedSs.setCreatedByAdmin(createdByAdminOther);
            case "seminar" ->
                modifiedSs.setSeminar(seminarOther);
            case "educationalInstitution" ->
                modifiedSs.setEducationalInstitution(educationalInstitutionOther);
            case "seminarEnrollmentsLarger" ->
                modifiedSs.getSeminarEnrollments().add(
                        new SeminarEnrollment(modifiedSs, new Participant(4), "st name 4")
                );
            case "seminarEnrollmentsSmaller" ->
                modifiedSs.getSeminarEnrollments().remove(0);
            default ->
                throw new AssertionError();
        }
        return modifiedSs;
    }

    @Test
    void test_toString() {
        String ssString = seminarSchedule.toString();
        assertTrue(ssString.contains(seminarSchedule.getSeminarScheduleID() + ""));
        assertTrue(ssString.contains(seminarSchedule.getDatetimeBegins().toString()));
        assertTrue(ssString.contains(seminarSchedule.getDatetimeEnds().toString()));
        assertTrue(ssString.contains(seminarSchedule.getCreatedByAdmin().getAdminID() + ""));
        assertTrue(ssString.contains(seminarSchedule.getSeminar().getSeminarID() + ""));
        assertTrue(ssString.contains(seminarSchedule.getEducationalInstitution().getEducationalInstitutionID() + ""));
        assertTrue(ssString.contains(State.UNCHANGED.toString()));
    }
}
