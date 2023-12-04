/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import com.fon.common.utils.Utility;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Aleksa
 */
public class SeminarScheduleTest extends GenericEntityTest {

    private static SeminarSchedule seminarSchedule;

    private static final int ID = 1;
    private static final String IDString = "1";
    private static Date datetimeBegins;
    private static final String datetimeBeginsString = "01.02.2024 | 14:00";
    private static Date datetimeEnds;
    private static final String datetimeEndsString = "01.02.2024 | 16:15";
    private static final int createdByAdminID = 1;
    private static final String createdByAdminIDString = "1";
    private static final Admin createdByAdmin = new Admin(createdByAdminID);

    private static final int seminarID = 1;
    private static final String seminarIDString = "1";
    private static final Seminar seminar = new Seminar(seminarID);
    private static final int eiID = 1;
    private static final String eiIDString = "1";
    private static final EducationalInstitution educationalInstitution = new EducationalInstitution(eiID);

    private static List<SeminarEnrollment> seminarEnrollments = new LinkedList();

    private static int IDOther = 2;
    private static final String IDStringOther = "2";
    private static Date datetimeBeginsOther;
    private static final String datetimeBeginsStringOther = "01.02.2024 | 15:00";
    private static Date datetimeEndsOther;
    private static final String datetimeEndsStringOther = "01.02.2024 | 15:30";
    private static final int createdByAdminIDOther = 2;
    private static final String createdByAdminIDStringOther = "2";
    private static final int seminarIDOther = 2;
    private static final String seminarIDStringOther = "2";
    private static final int eiIDOther = 2;
    private static final String eiIDStringOther = "2";

    private static void seminarScheduleInitializer() throws ParseException {
        datetimeBegins = Utility.DATETIME_FORMAT.parse(datetimeBeginsString);
        datetimeEnds = Utility.DATETIME_FORMAT.parse(datetimeEndsString);

        datetimeBeginsOther = Utility.DATETIME_FORMAT.parse(datetimeBeginsStringOther);
        datetimeEndsOther = Utility.DATETIME_FORMAT.parse(datetimeEndsStringOther);

        SeminarEnrollment se11 = new SeminarEnrollment(seminarSchedule, new Participant(11), "notes11");
        SeminarEnrollment se12 = new SeminarEnrollment(seminarSchedule, new Participant(12), "notes12");
        SeminarEnrollment se13 = new SeminarEnrollment(seminarSchedule, new Participant(13), "notes13");
        seminarEnrollments = new LinkedList(Arrays.asList(se11, se12, se13));

        seminarSchedule = new SeminarSchedule(ID, datetimeBegins, datetimeEnds, createdByAdmin, seminar, educationalInstitution, seminarEnrollments);
    }

    @BeforeEach
    void setUp() throws ParseException {
        seminarScheduleInitializer();
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
                ID, new java.sql.Timestamp(datetimeBegins.getTime()), new java.sql.Timestamp(datetimeEnds.getTime()),
                createdByAdmin.getAdminID(), seminar.getSeminarID(), educationalInstitution.getEducationalInstitutionID());
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
                new java.sql.Timestamp(datetimeBegins.getTime()),
                new java.sql.Timestamp(datetimeEnds.getTime()),
                seminar.getSeminarID());
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

            String sName = "sName";
            String sDescription = "sDescription";
            String eiName = "eiName";
            String eiAddress = "eiAddress";

            when(rs.getInt("ss.seminarScheduleID")).thenReturn(ID);
            when(rs.getTimestamp("datetimeBegins")).thenReturn(new java.sql.Timestamp(datetimeBegins.getTime()));
            when(rs.getTimestamp("datetimeEnds")).thenReturn(new java.sql.Timestamp(datetimeEnds.getTime()));
            when(rs.getInt("seminarID")).thenReturn(seminarID);
            when(rs.getString("s.name")).thenReturn(sName);
            when(rs.getString("description")).thenReturn(sDescription);
            when(rs.getInt("educationalInstitutionID")).thenReturn(eiID);
            when(rs.getString("s.name")).thenReturn(sName);
            when(rs.getString("ei.name")).thenReturn(eiName);
            when(rs.getString("ei.address")).thenReturn(eiAddress);

            SeminarSchedule ssFromRS = (SeminarSchedule) seminarSchedule.getEntityFromResultSet(rs);

            assertEquals(ID, ssFromRS.getSeminarScheduleID());
            assertEquals(datetimeBegins, ssFromRS.getDatetimeBegins());
            assertEquals(datetimeEnds, ssFromRS.getDatetimeEnds());
            assertEquals(seminarID, ssFromRS.getSeminar().getSeminarID());
            assertEquals(sName, ssFromRS.getSeminar().getName());
            assertEquals(sDescription, ssFromRS.getSeminar().getDescription());
            assertEquals(eiID, ssFromRS.getEducationalInstitution().getEducationalInstitutionID());
            assertEquals(eiName, ssFromRS.getEducationalInstitution().getName());
            assertEquals(eiAddress, ssFromRS.getEducationalInstitution().getAddress());
        } catch (SQLException ex) {
            Logger.getLogger(SeminarScheduleTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition(String.format("seminarScheduleID = %d ", ID));
    }

    @Test
    void test_getState() {
        assertEquals(State.UNCHANGED, seminarSchedule.getState());
    }

    //tests for class specific methods
    @Test
    void test_setSeminarScheduleID() {
        seminarSchedule.setSeminarScheduleID(ID);
        assertEquals(seminarSchedule.getSeminarScheduleID(), ID);
    }

    @Test
    void test_setDatetimeBegins() {
        seminarSchedule.setDatetimeBegins(datetimeBegins);
        assertEquals(seminarSchedule.getDatetimeBegins(), datetimeBegins);
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
        seminarSchedule.setDatetimeEnds(datetimeEnds);
        assertEquals(seminarSchedule.getDatetimeEnds(), datetimeEnds);
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
        seminarSchedule.setCreatedByAdmin(createdByAdmin);
        assertEquals(seminarSchedule.getCreatedByAdmin(), createdByAdmin);
    }

    @Test
    void test_setCreatedByAdmin_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarSchedule.setCreatedByAdmin(null));
    }

    @Test
    void test_setSeminar() {
        seminarSchedule.setSeminar(seminar);
        assertEquals(seminarSchedule.getSeminar(), seminar);
    }

    @Test
    void test_setSeminar_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarSchedule.setSeminar(null));
    }

    @Test
    void test_setEducationalInstitution() {
        seminarSchedule.setEducationalInstitution(educationalInstitution);
        assertEquals(seminarSchedule.getEducationalInstitution(), educationalInstitution);
    }

    @Test
    void test_setEducationalInstitution_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminarSchedule.setEducationalInstitution(null));
    }

    @Test
    void test_setSeminarEnrollments() {
        seminarSchedule.setSeminarEnrollments(seminarEnrollments);
        assertEquals(seminarSchedule.getSeminarEnrollments(), seminarEnrollments);
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
                () -> seminar.setState(null));
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

    @ParameterizedTest
    @CsvSource({
        //all equal
        IDString + "," + datetimeBeginsString + "," + datetimeEndsString + "," + createdByAdminIDString + "," + seminarIDString + "," + eiIDString + ",true",
        //all equal but seminarScheduleID
        IDStringOther + "," + datetimeBeginsString + "," + datetimeEndsString + "," + createdByAdminIDString + "," + seminarIDString + "," + eiIDString + ",false",
        //all equal but datetimeBegins
        IDString + "," + datetimeBeginsStringOther + "," + datetimeEndsString + "," + createdByAdminIDString + "," + seminarIDString + "," + eiIDString + ",true",
        //all equal but datetimeEnds
        IDString + "," + datetimeBeginsString + "," + datetimeEndsStringOther + "," + createdByAdminIDString + "," + seminarIDString + "," + eiIDString + ",true",
        //all equal but createdByAdmin
        IDString + "," + datetimeBeginsString + "," + datetimeEndsString + "," + createdByAdminIDStringOther + "," + seminarIDString + "," + eiIDString + ",true",
        //all equal but seminar
        IDString + "," + datetimeBeginsString + "," + datetimeEndsString + "," + createdByAdminIDString + "," + seminarIDStringOther + "," + eiIDString + ",true",
        //all equal but educationalInstitution
        IDString + "," + datetimeBeginsString + "," + datetimeEndsString + "," + createdByAdminIDString + "," + seminarIDString + "," + eiIDStringOther + ",true"
    })
    void test_equals(int seminarScheduleID2, String datetimeBegins2, String datetimeEnds2, int createdByAdminID2, int seminarID2, int eiID2,
            boolean result) {

        try {
            Admin a2 = new Admin(seminarID2);
            Seminar s2 = new Seminar(seminarID2);
            EducationalInstitution ei2 = new EducationalInstitution(seminarScheduleID2);

            SeminarSchedule ssOther = new SeminarSchedule(seminarScheduleID2,
                    Utility.DATETIME_FORMAT.parse(datetimeBegins2),
                    Utility.DATETIME_FORMAT.parse(datetimeEnds2),
                    a2, s2, ei2, new LinkedList());

            assertEquals(result,
                    seminarSchedule.equals(ssOther));
        } catch (ParseException ex) {
            Logger.getLogger(SeminarScheduleTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
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
    @CsvSource({
        //all equal
        IDString + "," + datetimeBeginsString + "," + datetimeEndsString + "," + createdByAdminIDString + "," + seminarIDString + "," + eiIDString + ",true",
        //all equal but seminarScheduleID
        IDStringOther + "," + datetimeBeginsString + "," + datetimeEndsString + "," + createdByAdminIDString + "," + seminarIDString + "," + eiIDString + ",false",
        //all equal but datetimeBegins
        IDString + "," + datetimeBeginsStringOther + "," + datetimeEndsString + "," + createdByAdminIDString + "," + seminarIDString + "," + eiIDString + ",false",
        //all equal but datetimeEnds
        IDString + "," + datetimeBeginsString + "," + datetimeEndsStringOther + "," + createdByAdminIDString + "," + seminarIDString + "," + eiIDString + ",false",
        //all equal but createdByAdmin
        IDString + "," + datetimeBeginsString + "," + datetimeEndsString + "," + createdByAdminIDStringOther + "," + seminarIDString + "," + eiIDString + ",true",
        //all equal but seminar
        IDString + "," + datetimeBeginsString + "," + datetimeEndsString + "," + createdByAdminIDString + "," + seminarIDStringOther + "," + eiIDString + ",false",
        //all equal but educationalInstitution
        IDString + "," + datetimeBeginsString + "," + datetimeEndsString + "," + createdByAdminIDString + "," + seminarIDString + "," + eiIDStringOther + ",false"
    })
    void test_equalsAllWithoutSeminarEnrollments(int seminarScheduleID2, String datetimeBegins2, String datetimeEnds2, int createdByAdminID2, int seminarID2, int eiID2,
            boolean result) {

        try {
            Admin a2 = new Admin(seminarID2);
            Seminar s2 = new Seminar(seminarID2);
            EducationalInstitution ei2 = new EducationalInstitution(eiID2);

            SeminarSchedule ssOther = new SeminarSchedule(seminarScheduleID2,
                    Utility.DATETIME_FORMAT.parse(datetimeBegins2), Utility.DATETIME_FORMAT.parse(datetimeEnds2), a2, s2, ei2, new LinkedList());

            assertEquals(result,
                    seminarSchedule.equalsAllWithoutSeminarEnrollments(ssOther));
        } catch (ParseException ex) {
            Logger.getLogger(SeminarScheduleTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
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
    @MethodSource("seminarScheduleEqualsAllProvider")
    void test_equalsAll(SeminarSchedule ss, SeminarSchedule ssOther, boolean result) {
        assertEquals(result, ss.equalsAll(ssOther));
    }

    static Stream<Arguments> seminarScheduleEqualsAllProvider() {
        try {
            seminarScheduleInitializer();

            List<SeminarEnrollment> seminarEnrollments2 = Utility.getDeepCopy(seminarEnrollments);

            //all equal
            SeminarSchedule ss2_equal = Utility.getDeepCopy(seminarSchedule);

            //all equal but seminarID
            SeminarSchedule ss2_dID = getSeminarScheduleDSeminarScheduleID();

            //all equal but name
            SeminarSchedule ss2_dDatetimeBegins = getSeminarScheduleDDatetimeBegins();

            //all equal but description
            SeminarSchedule ss2_dDatetimeEnds = getSeminarScheduleDDatetimeEnds();

            //all equal but createdByAdmin
            SeminarSchedule ss2_dCreatedByAdmin = getSeminarScheduleDCreatedByAdmin();

            //all equal but seminar
            SeminarSchedule ss2_dSeminar = getSeminarScheduleDSeminar();

            //all equal but educationalInstitution
            SeminarSchedule ss2_dEducationalInstitution = getSeminarScheduleDEducationalInstitution();

            //all equal but seminarTopics sizes differ - other is larger
            SeminarSchedule ss2_dTopics_dSize_otherLarger = getSeminarScheduleDTopics_dSize_otherLarger(seminarEnrollments2);

            //all equal but seminarTopics - different list sizes
            SeminarSchedule ss2_dTopics_dSize_otherSmaller = getSeminarScheduleDTopics_dSize_otherSmaller(seminarEnrollments2);
            return Stream.of(
                    arguments(seminarSchedule, ss2_equal, true),
                    arguments(seminarSchedule, ss2_dID, false),
                    arguments(seminarSchedule, ss2_dDatetimeBegins, false),
                    arguments(seminarSchedule, ss2_dDatetimeEnds, false),
                    arguments(seminarSchedule, ss2_dCreatedByAdmin, false),
                    arguments(seminarSchedule, ss2_dEducationalInstitution, false),
                    arguments(seminarSchedule, ss2_dSeminar, false),
                    arguments(seminarSchedule, ss2_dTopics_dSize_otherLarger, false),
                    arguments(seminarSchedule, ss2_dTopics_dSize_otherSmaller, false)
            );
        } catch (Exception ex) {
            Logger.getLogger(SeminarTest.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static SeminarSchedule getSeminarScheduleDSeminarScheduleID() throws ClassNotFoundException, Exception, IOException {
        SeminarSchedule ss2_dID = Utility.getDeepCopy(seminarSchedule);
        ss2_dID.setSeminarScheduleID(IDOther);
        ss2_dID.getSeminarEnrollments().forEach((x) -> x.setSeminarSchedule(ss2_dID));
        return ss2_dID;
    }

    private static SeminarSchedule getSeminarScheduleDDatetimeBegins() throws IOException, ClassNotFoundException {
        SeminarSchedule ss2_dDatetimeBegins = Utility.getDeepCopy(seminarSchedule);
        ss2_dDatetimeBegins.setDatetimeBegins(datetimeBeginsOther);
        ss2_dDatetimeBegins.getSeminarEnrollments().forEach((x) -> x.setSeminarSchedule(ss2_dDatetimeBegins));
        return ss2_dDatetimeBegins;
    }

    private static SeminarSchedule getSeminarScheduleDDatetimeEnds() throws IOException, ClassNotFoundException {
        SeminarSchedule ss2_dDatetimeEnds = Utility.getDeepCopy(seminarSchedule);
        ss2_dDatetimeEnds.setDatetimeEnds(datetimeEndsOther);
        ss2_dDatetimeEnds.getSeminarEnrollments().forEach((x) -> x.setSeminarSchedule(ss2_dDatetimeEnds));

        return ss2_dDatetimeEnds;
    }

    private static SeminarSchedule getSeminarScheduleDCreatedByAdmin() throws IOException, ClassNotFoundException {
        Admin a2 = new Admin(createdByAdminIDOther);
        SeminarSchedule ss2_dCreatedByAdmin = Utility.getDeepCopy(seminarSchedule);
        ss2_dCreatedByAdmin.setCreatedByAdmin(a2);
        ss2_dCreatedByAdmin.getSeminarEnrollments().forEach((x) -> x.setSeminarSchedule(ss2_dCreatedByAdmin));

        return ss2_dCreatedByAdmin;
    }

    private static SeminarSchedule getSeminarScheduleDSeminar() throws ClassNotFoundException, Exception, IOException {
        SeminarSchedule ss2_dSeminar = Utility.getDeepCopy(seminarSchedule);
        ss2_dSeminar.setSeminar(new Seminar(seminarIDOther));
        ss2_dSeminar.getSeminarEnrollments().forEach((x) -> x.setSeminarSchedule(ss2_dSeminar));

        return ss2_dSeminar;
    }

    private static SeminarSchedule getSeminarScheduleDEducationalInstitution() throws ClassNotFoundException, Exception, IOException {
        SeminarSchedule ss2_dEducationalInstitution = Utility.getDeepCopy(seminarSchedule);
        ss2_dEducationalInstitution.setEducationalInstitution(new EducationalInstitution(eiIDOther));
        ss2_dEducationalInstitution.getSeminarEnrollments().forEach((x) -> x.setSeminarSchedule(ss2_dEducationalInstitution));

        return ss2_dEducationalInstitution;
    }

    private static SeminarSchedule getSeminarScheduleDTopics_dSize_otherLarger(List seminarEnrollments2) throws IOException, ClassNotFoundException {
        SeminarSchedule ss2_dTopics_dSize_otherLarger = Utility.getDeepCopy(seminarSchedule);

        List<SeminarEnrollment> seminarEnrollments2_dSize = Utility.getDeepCopy(seminarEnrollments2);

        SeminarEnrollment se24 = new SeminarEnrollment(ss2_dTopics_dSize_otherLarger, new Participant(24), "notes24");
        seminarEnrollments2_dSize.add(se24);

        seminarEnrollments2_dSize.forEach((x) -> x.setSeminarSchedule(ss2_dTopics_dSize_otherLarger));
        ss2_dTopics_dSize_otherLarger.setSeminarEnrollments(seminarEnrollments2_dSize);

        return ss2_dTopics_dSize_otherLarger;
    }

    private static SeminarSchedule getSeminarScheduleDTopics_dSize_otherSmaller(List seminarEnrollments2) throws IOException, ClassNotFoundException {
        SeminarSchedule ss2_dTopics_dSize_otherSmaller = Utility.getDeepCopy(seminarSchedule);

        List<SeminarEnrollment> seminarEnrollments2_dSize = Utility.getDeepCopy(seminarEnrollments2);
        seminarEnrollments2_dSize.remove(0);

        seminarEnrollments2_dSize.forEach((x) -> x.setSeminarSchedule(ss2_dTopics_dSize_otherSmaller));
        ss2_dTopics_dSize_otherSmaller.setSeminarEnrollments(seminarEnrollments2_dSize);

        return ss2_dTopics_dSize_otherSmaller;
    }

    @Test
    void test_toString() {
        String seminarEnrollmentString = seminarSchedule.toString();
        assertTrue(seminarEnrollmentString.contains(ID + ""));
        assertTrue(seminarEnrollmentString.contains(datetimeBegins.toString()));
        assertTrue(seminarEnrollmentString.contains(datetimeEnds.toString()));
        assertTrue(seminarEnrollmentString.contains(createdByAdminID + ""));
        assertTrue(seminarEnrollmentString.contains(seminar.toString()));
        assertTrue(seminarEnrollmentString.contains(educationalInstitution.toString()));
        assertTrue(seminarEnrollmentString.contains(State.UNCHANGED.toString()));
    }
}
