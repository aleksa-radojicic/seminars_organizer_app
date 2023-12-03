/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import com.fon.common.utils.Utility;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
public class SeminarTest extends GenericEntityTest {

    private static Seminar seminar;
    private static final int ID = 1;
    private static final String IDString = ID + "";
    private static final String name = "name";
    private static final String description = "surname";
    private static final Admin createdByAdmin = new Admin();
    private static List<SeminarTopic> seminarTopics;

    private static final int IDOther = 2;
    private static final String IDStringOther = "2";
    private static final String nameOther = "nameOther";
    private static final String descriptionOther = "surnameOther";
    private static final int createdByAdminIDOther = 2;

    @BeforeEach
    void setUp() {
        SeminarTopic st11 = new SeminarTopic(seminar, 11, "st11", "st name 11");
        SeminarTopic st12 = new SeminarTopic(seminar, 12, "st12", "st name 12");
        SeminarTopic st13 = new SeminarTopic(seminar, 13, "st13", "st name 13");
        seminarTopics = new LinkedList(List.of(st11, st12, st13));

        seminar = new Seminar(ID, name, description, createdByAdmin, seminarTopics);
        genericEntity = seminar;
    }

    @AfterEach
    void tearDown() {
        seminar = null;
    }

    @Test
    void test_getAttributeNames() {
        super.test_getAttributeNames("seminarID, name, description, createdByAdminID");
    }

    @Test
    void test_getAttributeValues() {
        super.test_getAttributeValues(String.format("%d, '%s', '%s', %d",
                ID, name, description, createdByAdmin.getAdminID()));
    }

    @Test
    void test_getID() {
        super.test_getID(seminar.getSeminarID());
    }

    @Test
    void test_setID() {
        super.test_setID(seminar.getSeminarID());
    }

    @Test
    void test_setAttributeValues() {
        String expectedValue = String.format("name ='%s', description = '%s'", name, description);
        assertEquals(expectedValue, seminar.setAttributeValues());
    }

    @Test
    void test_getSelectAllQuery() {
        super.test_getSelectAllQuery("SELECT * from seminars");
    }

    @Test
    void test_getEntityFromResultSet() {
        try {
            ResultSet rs = mock(ResultSet.class);

            when(rs.getInt("seminarID")).thenReturn(ID);
            when(rs.getString("name")).thenReturn(name);
            when(rs.getString("description")).thenReturn(description);

            Seminar seminarFromRS = (Seminar) seminar.getEntityFromResultSet(rs);
            assertEquals(ID, seminarFromRS.getSeminarID());
            assertEquals(name, seminarFromRS.getName());
            assertEquals(description, seminarFromRS.getDescription());
        } catch (SQLException ex) {
            Logger.getLogger(SeminarTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition(String.format("seminarID = %d ", ID));
    }

    @Test
    void test_getState() {
        assertEquals(State.UNCHANGED, seminar.getState());
    }

    //tests for class specific methods
    @Test
    void test_setParticipantID() {
        seminar.setSeminarID(ID);
        assertEquals(seminar.getSeminarID(), ID);
    }

    @Test
    void test_setName() {
        seminar.setName(name);
        assertEquals(seminar.getName(), name);
    }

    @Test
    void test_setDescription() {
        seminar.setDescription(description);
        assertEquals(seminar.getDescription(), description);
    }

    @Test
    void test_setCreatedByAdmin() {
        seminar.setCreatedByAdmin(createdByAdmin);
        assertEquals(seminar.getCreatedByAdmin(), createdByAdmin);
    }

    @Test
    void test_setSeminarTopics() {
        seminar.setSeminarTopics(seminarTopics);
        assertTrue(SeminarTopic.equalsAll(seminarTopics, seminar.getSeminarTopics()));
    }

    @Test
    void test_setState() {
        seminar.setState(State.CREATED);
        assertEquals(State.CREATED, seminar.getState());
    }

    @Test
    void test_equals_sameObject() {
        assertTrue(seminar.equals(seminar));
    }

    @Test
    void test_equals_null() {
        assertFalse(seminar.equals(null));
    }

    @Test
    void test_equals_differentClass() {
        assertFalse(seminar.equals(new Exception()));
    }

    @ParameterizedTest
    @CsvSource({
        //all equal
        IDString + "," + name + "," + description + ",true",
        //all equal but seminarID
        IDStringOther + "," + name + "," + description + ",false",
        //all equal but name
        IDString + "," + nameOther + "," + description + ",true",
        //all equal but description
        IDString + "," + name + "," + descriptionOther + ",true"
    })
    void test_equals(int ID2, String name2, String description2,
            boolean result) {

        Seminar seminarOther = new Seminar(ID2, name2, description2);

        assertEquals(result,
                seminar.equals(seminarOther));
    }

    @Test
    void test_equalsAll_sameObject() {
        assertTrue(seminar.equalsAll(seminar));
    }

    @Test
    void test_equalsAll_null() {
        assertFalse(seminar.equalsAll(null));
    }

    @Test
    void test_equalsAll_differentClass() {
        assertFalse(seminar.equalsAll(new Exception()));
    }

    @ParameterizedTest
    @MethodSource("seminarEqualsAllProvider")
    void test_equalsAll(Seminar s, Seminar sOther, boolean result) {
        assertEquals(result, s.equalsAll(sOther));
    }

    static Stream<Arguments> seminarEqualsAllProvider() {
        SeminarTopic st11 = new SeminarTopic(seminar, 11, "st11", "st name 11");
        SeminarTopic st12 = new SeminarTopic(seminar, 12, "st12", "st name 12");
        SeminarTopic st13 = new SeminarTopic(seminar, 13, "st13", "st name 13");
        seminarTopics = new LinkedList(List.of(st11, st12, st13));

        SeminarTopic st21 = new SeminarTopic(null, 21, "st21", "st name 21");
        SeminarTopic st22 = new SeminarTopic(null, 22, "st22", "st name 22");
        SeminarTopic st23 = new SeminarTopic(null, 23, "st23", "st name 23");
        List<SeminarTopic> seminarTopics2 = new LinkedList(List.of(st21, st22, st23));

        seminar = new Seminar(ID, name, description, createdByAdmin, seminarTopics);

        try {
            //all equal
            Seminar s2_equal = Utility.getDeepCopy(seminar);

            //all equal but seminarID
            Seminar s2_dID = getSeminarDSeminarID();

            //all equal but name
            Seminar s2_dName = getSeminarDName();

            //all equal but description
            Seminar s2_dDescription = getSeminarDDescription();

            //all equal but createdByAdmin
            Seminar s2_dCreatedByAdmin = getSeminarDCreatedByAdmin();

            //all equal but seminarTopics sizes differ - other is larger
            Seminar s2_dTopics_dSize_otherLarger = getSeminarDTopics_dSize_otherLarger(seminarTopics2);

            //all equal but seminarTopics - different list sizes
            Seminar s2_dTopics_dSize_otherSmaller = getSeminarDTopics_dSize_otherSmaller(seminarTopics2);
            return Stream.of(
                    arguments(seminar, s2_equal, true),
                    arguments(seminar, s2_dID, false),
                    arguments(seminar, s2_dName, false),
                    arguments(seminar, s2_dDescription, false),
                    arguments(seminar, s2_dCreatedByAdmin, true),
                    arguments(seminar, s2_dTopics_dSize_otherLarger, false),
                    arguments(seminar, s2_dTopics_dSize_otherSmaller, false)
            );
        } catch (Exception ex) {
            Logger.getLogger(SeminarTest.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private static Seminar getSeminarDSeminarID() throws ClassNotFoundException, Exception, IOException {
        Seminar s2_dID = Utility.getDeepCopy(seminar);
        s2_dID.setSeminarID(IDOther);
        return s2_dID;
    }

    private static Seminar getSeminarDName() throws IOException, ClassNotFoundException {
        Seminar s2_dName = Utility.getDeepCopy(seminar);
        s2_dName.setName(nameOther);
        return s2_dName;
    }

    private static Seminar getSeminarDDescription() throws IOException, ClassNotFoundException {
        Seminar s2_dDescription = Utility.getDeepCopy(seminar);
        s2_dDescription.setDescription(descriptionOther);

        return s2_dDescription;
    }

    private static Seminar getSeminarDCreatedByAdmin() throws IOException, ClassNotFoundException {
        Admin a2 = new Admin();
        a2.setAdminID(createdByAdminIDOther);
        Seminar s2_dCreatedByAdmin = Utility.getDeepCopy(seminar);
        s2_dCreatedByAdmin.setCreatedByAdmin(a2);

        return s2_dCreatedByAdmin;
    }

    private static Seminar getSeminarDTopics_dSize_otherLarger(List seminarTopics2) throws IOException, ClassNotFoundException {
        Seminar s2_dTopics_dSize_otherLarger = Utility.getDeepCopy(seminar);

        List<SeminarTopic> seminarTopics2_dSize = Utility.getDeepCopy(seminarTopics2);

        SeminarTopic st24 = new SeminarTopic(s2_dTopics_dSize_otherLarger, 24, "st24", "st name 24");
        seminarTopics2_dSize.add(st24);

        seminarTopics2_dSize.forEach((x) -> x.setSeminar(s2_dTopics_dSize_otherLarger));
        s2_dTopics_dSize_otherLarger.setSeminarTopics(seminarTopics2_dSize);

        return s2_dTopics_dSize_otherLarger;
    }

    private static Seminar getSeminarDTopics_dSize_otherSmaller(List seminarTopics2) throws IOException, ClassNotFoundException {
        Seminar s2_dTopics_dSize_otherSmaller = Utility.getDeepCopy(seminar);

        List<SeminarTopic> seminarTopics2_dSize = Utility.getDeepCopy(seminarTopics2);
        seminarTopics2_dSize.remove(0);

        seminarTopics2_dSize.forEach((x) -> x.setSeminar(s2_dTopics_dSize_otherSmaller));
        s2_dTopics_dSize_otherSmaller.setSeminarTopics(seminarTopics2_dSize);

        return s2_dTopics_dSize_otherSmaller;
    }

    @Test
    void test_toString() {
        String seminarString = seminar.toString();

        assertTrue(seminarString.contains(name));
        assertTrue(seminarString.contains(description));
    }
}
