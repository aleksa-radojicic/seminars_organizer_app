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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class SeminarTopicTest extends GenericEntityTest {

    private static SeminarTopic st;

    private static List<SeminarTopic> seminarTopics;

    private static Seminar seminarOther;
    private static int IDOther;
    private static String nameOther;
    private static String presenterOther;

    public static void initializeSeminarTopics() {
        SeminarTopic[] seminarTopicsArray = (SeminarTopic[]) IOJson.deserializeJson("seminar_topics", SeminarTopic[].class);
        seminarTopics = new LinkedList(Arrays.asList(seminarTopicsArray));

        st = seminarTopics.get(0);

        int seminarIDOther = st.getSeminar().getSeminarID() + 1;
        seminarOther = new Seminar(seminarIDOther);
        IDOther = st.getSeminarTopicID() + 1;
        nameOther = st.getName() + Utility.STRING_OTHER;
        presenterOther = st.getPresenter()+ Utility.STRING_OTHER;
    }

    @BeforeEach
    void setUp() {
        initializeSeminarTopics();
        genericEntity = st;
    }

    @AfterEach
    void tearDown() {
        st = null;
        genericEntity = null;
    }

    @Test
    void test_getAttributeNames() {
        super.test_getAttributeNames("seminarID, seminarTopicID, name, presenter");
    }

    @Test
    void test_getAttributeValues() {
        super.test_getAttributeValues(String.format("%d, %d, '%s', '%s'",
                st.getSeminar().getSeminarID(), st.getSeminarTopicID(), st.getName(), st.getPresenter()));
    }

    @Test
    void test_getID() {
        super.test_getID(st.getSeminarTopicID());
    }

    @Test
    void test_setID() {
        super.test_setID(st.getSeminarTopicID());
    }

    @Test
    void test_setAttributeValues() {
        String expectedValue = String.format("name = '%s', presenter = '%s'", st.getName(), st.getPresenter());
        assertEquals(expectedValue, st.setAttributeValues());
    }

    @Test
    void test_getSelectAllQuery() {
        super.test_getSelectAllQuery("SELECT * from seminarTopics");
    }

    @Test
    void test_getEntityFromResultSet() {
        try {
            ResultSet rs = mock(ResultSet.class);

            when(rs.getInt("seminarTopicID")).thenReturn(st.getSeminarTopicID());
            when(rs.getString("name")).thenReturn(st.getName());
            when(rs.getString("presenter")).thenReturn(st.getPresenter());

            SeminarTopic seminarTopicFromRS = (SeminarTopic) st.getEntityFromResultSet(rs);
            assertEquals(st.getSeminarTopicID(), seminarTopicFromRS.getSeminarTopicID());
            assertEquals(st.getName(), seminarTopicFromRS.getName());
            assertEquals(st.getPresenter(), seminarTopicFromRS.getPresenter());
        } catch (SQLException ex) {
            Logger.getLogger(SeminarTopicTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition(String.format("seminarID = %d AND seminarTopicID = %d ", st.getSeminar().getSeminarID(), st.getSeminarTopicID()));
    }

    @Test
    void test_getState() {
        assertEquals(State.UNCHANGED, st.getState());
    }

    //tests for class specific methods
    @Test
    void test_setSeminar() {
        Seminar s = new Seminar();
        st.setSeminar(s);
        assertEquals(st.getSeminar(), s);
    }

    @Test
    void test_setSeminar_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> st.setSeminar(null));
    }

    @Test
    void test_setSeminarTopicID() {
        st.setSeminarTopicID(IDOther);
        assertEquals(st.getSeminarTopicID(), IDOther);
    }

    @Test
    void test_setName() {
        st.setName(nameOther);
        assertEquals(st.getName(), nameOther);
    }

    @Test
    void test_setName_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> st.setName(null));
    }

    @Test
    void test_setName_empty() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> st.setName(Utility.STRING_EMPTY));
    }

    @Test
    void test_setName_tooLong() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> st.setName(Utility.STRING_61_LENGTH));
    }

    @Test
    void test_setPresenter() {
        st.setPresenter(presenterOther);
        assertEquals(st.getPresenter(), presenterOther);
    }

    @Test
    void test_setPresenter_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> st.setPresenter(null));
    }

    @Test
    void test_setPresenter_empty() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> st.setPresenter(Utility.STRING_EMPTY));
    }

    @Test
    void test_setPresenter_tooLong() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> st.setPresenter(Utility.STRING_61_LENGTH));
    }

    @Test
    void test_setState() {
        st.setState(State.CREATED);
        assertEquals(State.CREATED, st.getState());
    }

    @Test
    void test_setState_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> st.setState(null));
    }

    @Test
    void test_validateNull() {
        assertDoesNotThrow(() -> st.validateNull());
    }

    @Test
    void test_validateNull_nameNull() {
        SeminarTopic seminarTopic = new SeminarTopic(st.getSeminar(), st.getSeminarTopicID());
        seminarTopic.setPresenter(st.getPresenter());
        assertThrowsExactly(NullPointerException.class,
                () -> seminarTopic.validateNull());
    }

    @Test
    void test_validateNull_presenterNull() {
        SeminarTopic seminarTopic = new SeminarTopic(st.getSeminar(), st.getSeminarTopicID());
        seminarTopic.setName(st.getName());
        assertThrowsExactly(NullPointerException.class,
                () -> seminarTopic.validateNull());
    }

    @Test
    void test_equals_sameObject() {
        assertTrue(st.equals(st));
    }

    @Test
    void test_equals_null() {
        assertFalse(st.equals(null));
    }

    @Test
    void test_equals_differentClass() {
        assertFalse(st.equals(new Exception()));
    }

    /*
    Cases checked:
    -> all equal
    -> all equal but seminar
    -> all equal but seminarTopicID
    -> all equal but name
    -> all equal but presenter
     */
    @ParameterizedTest
    @MethodSource("equalsProvider")
    void test_equals(SeminarTopic stOther, boolean result) {
        assertEquals(result, st.equals(stOther));
    }

    static Stream<Arguments> equalsProvider() throws Exception {
        initializeSeminarTopics();
        return Stream.of(
                arguments(Utility.clone(st), true),
                arguments(cloneSeminarTopicAndModify("seminar"), false),
                arguments(cloneSeminarTopicAndModify("seminarTopicID"), false),
                arguments(cloneSeminarTopicAndModify("name"), true),
                arguments(cloneSeminarTopicAndModify("presenter"), true)
        );
    }

    @Test
    void test_equalsAll_sameObject() {
        assertTrue(st.equalsAll(st));
    }

    @Test
    void test_equalsAll_null() {
        assertFalse(st.equalsAll(null));
    }

    @Test
    void test_equalsAll_differentClass() {
        assertFalse(st.equalsAll(new Exception()));
    }

    @ParameterizedTest
    @MethodSource("equalsAllProvider")
    void test_equalsAll(SeminarTopic stOther, boolean result) {
        assertEquals(result, st.equalsAll(stOther));
    }

    static Stream<Arguments> equalsAllProvider() throws IOException, ClassNotFoundException {
        initializeSeminarTopics();
        return Stream.of(
                arguments(Utility.clone(st), true),
                arguments(cloneSeminarTopicAndModify("seminar"), false),
                arguments(cloneSeminarTopicAndModify("seminarTopicID"), false),
                arguments(cloneSeminarTopicAndModify("name"), false),
                arguments(cloneSeminarTopicAndModify("presenter"), false)
        );
    }

    @ParameterizedTest
    @MethodSource("staticEqualsAllsProvider")
    void test_staticEqualsAll(List<SeminarTopic> seminarTopicsOther, boolean result) {
        assertEquals(result, SeminarTopic.equalsAll(seminarTopics, seminarTopicsOther));
    }

    /*
    Cases checked:
    -> all equal
    -> sizes differ - other is larger
    -> sizes differ - other is smaller
    -> all equal but seminar
    -> all equal but seminarTopicID
    -> all equal but name
    -> all equal but presenter
     */
    static Stream<Arguments> staticEqualsAllsProvider() throws IOException, ClassNotFoundException {
        initializeSeminarTopics();
        return Stream.of(
                arguments(Utility.clone(seminarTopics), true),
                arguments(cloneSeminarTopicsListAndModify("larger"), false),
                arguments(cloneSeminarTopicsListAndModify("smaller"), false),
                arguments(cloneSeminarTopicsListAndModify("seminar"), false),
                arguments(cloneSeminarTopicsListAndModify("seminarTopicID"), false),
                arguments(cloneSeminarTopicsListAndModify("name"), false),
                arguments(cloneSeminarTopicsListAndModify("presenter"), false)
        );
    }

    public static SeminarTopic cloneSeminarTopicAndModify(String attribute) throws IOException, ClassNotFoundException {
        SeminarTopic modifiedSt = Utility.clone(st);

        switch (attribute) {
            case "seminar" ->
                modifiedSt.setSeminar(seminarOther);
            case "seminarTopicID" ->
                modifiedSt.setSeminarTopicID(IDOther);
            case "name" ->
                modifiedSt.setName(nameOther);
            case "presenter" ->
                modifiedSt.setPresenter(presenterOther);
            default ->
                throw new AssertionError();
        }
        return modifiedSt;
    }

    public static List<SeminarTopic> cloneSeminarTopicsListAndModify(String attribute) throws IOException, ClassNotFoundException {
        List<SeminarTopic> modifiedSeminarTopics = Utility.clone(seminarTopics);

        switch (attribute) {
            case "larger" ->
                modifiedSeminarTopics.add(new SeminarTopic());
            case "smaller" ->
                modifiedSeminarTopics.remove(0);
            case "seminar" ->
                modifiedSeminarTopics.get(0).setSeminar(seminarOther);
            case "seminarTopicID" ->
                modifiedSeminarTopics.get(0).setSeminarTopicID(IDOther);
            case "name" ->
                modifiedSeminarTopics.get(0).setName(nameOther);
            case "presenter" ->
                modifiedSeminarTopics.get(0).setPresenter(presenterOther);
            default ->
                throw new AssertionError();
        }
        return modifiedSeminarTopics;
    }

    @Test
    void test_toString() {
        String seminarTopicString = st.toString();
        assertTrue(seminarTopicString.contains(st.getSeminar().getSeminarID() + ""));
        assertTrue(seminarTopicString.contains(st.getSeminarTopicID() + ""));
        assertTrue(seminarTopicString.contains(st.getName()));
        assertTrue(seminarTopicString.contains(st.getPresenter()));
        assertTrue(seminarTopicString.contains(State.UNCHANGED.toString()));
    }
}
