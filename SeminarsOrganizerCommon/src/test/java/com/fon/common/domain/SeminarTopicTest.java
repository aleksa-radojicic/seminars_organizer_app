/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import com.fon.common.utils.Utility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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
public class SeminarTopicTest extends GenericEntityTest {

    private static SeminarTopic seminarTopic;
    private static final Seminar seminar = new Seminar();
    private static final int seminarID = 1;
    private static final String seminarIDString = "1";
    private static final int ID = 1;
    private static final String IDString = "1";
    private static final String name = "name";
    private static final String presenter = "presenter";

    private static List<SeminarTopic> seminarTopics;

    private static int seminarIDOther = 2;
    private static final String seminarIDStringOther = "2";
    private static final int IDOther = 2;
    private static final String IDStringOther = "2";
    private static final String nameOther = "nameOther";
    private static final String presenterOther = "presenterOther";

    @BeforeEach
    void setUp() {
        seminar.setSeminarID(seminarID);
        seminarTopic = new SeminarTopic(seminar, ID, name, presenter);

        SeminarTopic st11 = new SeminarTopic(seminar, 11, "st11", "st name 11");
        SeminarTopic st12 = new SeminarTopic(seminar, 12, "st12", "st name 12");
        SeminarTopic st13 = new SeminarTopic(seminar, 13, "st13", "st name 13");
        seminarTopics = new LinkedList(Arrays.asList(st11, st12, st13));

        genericEntity = seminarTopic;
    }

    @AfterEach
    void tearDown() {
        seminarTopic = null;
    }

    @Test
    void test_getAttributeNames() {
        super.test_getAttributeNames("seminarID, seminarTopicID, name, presenter");
    }

    @Test
    void test_getAttributeValues() {
        super.test_getAttributeValues(String.format("%d, %d, '%s', '%s'",
                seminarID, ID, name, presenter));
    }

    @Test
    void test_getID() {
        super.test_getID(seminarTopic.getSeminarTopicID());
    }

    @Test
    void test_setID() {
        super.test_setID(seminarTopic.getSeminarTopicID());
    }

    @Test
    void test_setAttributeValues() {
        String expectedValue = String.format("seminarID = %d, seminarTopicID = %d, name = '%s', presenter = '%s'", seminarID, ID, name, presenter);
        assertEquals(expectedValue, seminarTopic.setAttributeValues());
    }

    @Test
    void test_getSelectAllQuery() {
        super.test_getSelectAllQuery("SELECT * from seminarTopics");
    }

    @Test
    void test_getEntityFromResultSet() {
        try {
            ResultSet rs = mock(ResultSet.class);

            when(rs.getInt("seminarTopicID")).thenReturn(ID);
            when(rs.getString("name")).thenReturn(name);
            when(rs.getString("presenter")).thenReturn(presenter);

            SeminarTopic seminarTopicFromRS = (SeminarTopic) seminarTopic.getEntityFromResultSet(rs);
            assertEquals(ID, seminarTopicFromRS.getSeminarTopicID());
            assertEquals(name, seminarTopicFromRS.getName());
            assertEquals(presenter, seminarTopicFromRS.getPresenter());
        } catch (SQLException ex) {
            Logger.getLogger(SeminarTopicTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition(String.format("seminarTopicID = %d ", ID));
    }

    @Test
    void test_getState() {
        assertEquals(State.UNCHANGED, seminarTopic.getState());
    }

    //tests for class specific methods
    @Test
    void test_setSeminar() {
        Seminar s = new Seminar();
        seminarTopic.setSeminar(s);
        assertEquals(seminarTopic.getSeminar(), s);
    }

    @Test
    void test_setSeminarTopicID() {
        seminarTopic.setSeminarTopicID(ID);
        assertEquals(seminarTopic.getSeminarTopicID(), ID);
    }

    @Test
    void test_setName() {
        seminarTopic.setName(name);
        assertEquals(seminarTopic.getName(), name);
    }

    @Test
    void test_setPresenter() {
        seminarTopic.setPresenter(presenter);
        assertEquals(seminarTopic.getPresenter(), presenter);
    }

    @Test
    void test_setState() {
        seminarTopic.setState(State.CREATED);
        assertEquals(State.CREATED, seminarTopic.getState());
    }

    @Test
    void test_equals_sameObject() {
        assertTrue(seminarTopic.equals(seminarTopic));
    }

    @Test
    void test_equals_null() {
        assertFalse(seminarTopic.equals(null));
    }

    @Test
    void test_equals_differentClass() {
        assertFalse(seminarTopic.equals(new Exception()));
    }

    @ParameterizedTest
    @CsvSource({
        //all equal
        seminarIDString + "," + IDString + "," + name + "," + presenter + ",true",
        //all equal but seminar
        seminarIDStringOther + "," + IDString + "," + name + "," + presenter + ",false",
        //all equal but seminarTopicID
        seminarIDString + "," + IDStringOther + "," + name + "," + presenter + ",false",
        //all equal but name
        seminarIDString + "," + IDString + "," + nameOther + "," + presenter + ",true",
        //all equal but description
        seminarIDString + "," + IDString + "," + name + "," + presenterOther + ",true"
    })
    void test_equals(int seminarID2, int ID2, String name2, String presenter2,
            boolean result) {

        Seminar s2 = new Seminar();
        s2.setSeminarID(seminarID2);
        SeminarTopic seminarTopicOther = new SeminarTopic(s2, ID2, name2, presenter2);

        assertEquals(result,
                seminarTopic.equals(seminarTopicOther));
    }

    @Test
    void test_equalsAll_sameObject() {
        assertTrue(seminarTopic.equalsAll(seminarTopic));
    }

    @Test
    void test_equalsAll_null() {
        assertFalse(seminarTopic.equalsAll(null));
    }

    @Test
    void test_equalsAll_differentClass() {
        assertFalse(seminarTopic.equalsAll(new Exception()));
    }

    @ParameterizedTest
    @CsvSource({
        //all equal
        seminarIDString + "," + IDString + "," + name + "," + presenter + ",true",
        //all equal but seminar
        seminarIDStringOther + "," + IDString + "," + name + "," + presenter + ",false",
        //all equal but seminarTopicID
        seminarIDString + "," + IDStringOther + "," + name + "," + presenter + ",false",
        //all equal but name
        seminarIDString + "," + IDString + "," + nameOther + "," + presenter + ",false",
        //all equal but description
        seminarIDString + "," + IDString + "," + name + "," + presenterOther + ",false"
    })
    void test_equalsAll(int seminarID2, int ID2, String name2, String presenter2,
            boolean result) {

        Seminar s2 = new Seminar();
        s2.setSeminarID(seminarID2);
        SeminarTopic seminarTopicOther = new SeminarTopic(s2, ID2, name2, presenter2);

        assertEquals(result,
                seminarTopic.equalsAll(seminarTopicOther));
    }

    @ParameterizedTest
    @MethodSource("seminarTopicStaticEqualsAllsProvider")
    void test_staticEqualsAll(List<SeminarTopic> st, List<SeminarTopic> stOther, boolean result) {
        assertEquals(result, SeminarTopic.equalsAll(st, stOther));
    }

    static Stream<Arguments> seminarTopicStaticEqualsAllsProvider() {
        try {
            Seminar seminar2 = new Seminar();
            seminar2.setSeminarID(seminarIDOther);

            //all equal
            List<SeminarTopic> st2_equal = Utility.getDeepCopy(seminarTopics);

            //sizes differ - other is larger
            List<SeminarTopic> st2_dSize_otherLarger = Utility.getDeepCopy(seminarTopics);
            SeminarTopic st = new SeminarTopic();
            st2_dSize_otherLarger.add(st);

            //sizes differ - other is smaller
            List<SeminarTopic> st2_dSize_otherSmaller = Utility.getDeepCopy(seminarTopics);
            st2_dSize_otherSmaller.remove(0);

            //all equal but seminar
            List<SeminarTopic> st2_dSeminar = Utility.getDeepCopy(seminarTopics);
            st2_dSeminar.get(0).setSeminar(seminar2);

            //all equal but seminarTopicID
            List<SeminarTopic> st2_dSeminarTopicID = Utility.getDeepCopy(seminarTopics);
            st2_dSeminarTopicID.get(0).setSeminarTopicID(IDOther);

            //all equal but name
            List<SeminarTopic> st2_dName = Utility.getDeepCopy(seminarTopics);
            st2_dName.get(0).setName(nameOther);

            //all equal but presenter
            List<SeminarTopic> st2_dPresenter = Utility.getDeepCopy(seminarTopics);
            st2_dPresenter.get(0).setPresenter(presenterOther);

            return Stream.of(
                    arguments(seminarTopics, st2_equal, true),
                    arguments(seminarTopics, st2_dSize_otherLarger, false),
                    arguments(seminarTopics, st2_dSize_otherSmaller, false),
                    arguments(seminarTopics, st2_dSeminar, false),
                    arguments(seminarTopics, st2_dSeminarTopicID, false),
                    arguments(seminarTopics, st2_dName, false),
                    arguments(seminarTopics, st2_dPresenter, false)
            );
        } catch (Exception ex) {
            Logger.getLogger(SeminarTopicTest.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Test
    void test_toString() {
        String seminarTopicString = seminarTopic.toString();
        assertTrue(seminarTopicString.contains(seminar.getSeminarID() + ""));
        assertTrue(seminarTopicString.contains(ID + ""));
        assertTrue(seminarTopicString.contains(name));
        assertTrue(seminarTopicString.contains(presenter));
        assertTrue(seminarTopicString.contains(State.UNCHANGED.toString()));
    }
}
