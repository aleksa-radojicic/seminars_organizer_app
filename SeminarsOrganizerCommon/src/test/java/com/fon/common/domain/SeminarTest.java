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
public class SeminarTest extends GenericEntityTest {

    private static Seminar seminar;

    private static int IDOther;
    private static String nameOther;
    private static String descriptionOther;
    private static Admin createdByAdminOther;

    public static void initializeSeminar() throws ParseException {
        seminar = (Seminar) IOJson.deserializeJson("seminar", Seminar.class);
        seminar.getSeminarTopics().forEach(x -> x.setSeminar(seminar));

        IDOther = seminar.getSeminarID() + 1;
        nameOther = seminar.getName() + Utility.STRING_OTHER;
        descriptionOther = seminar.getDescription() + Utility.STRING_OTHER;
        createdByAdminOther = new Admin(seminar.getCreatedByAdmin().getAdminID() + 1);
    }

    @BeforeEach
    void setUp() throws ParseException {
        initializeSeminar();
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
                seminar.getSeminarID(),
                seminar.getName(),
                seminar.getDescription(),
                seminar.getCreatedByAdmin().getAdminID()));
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
        String expectedValue = String.format("name ='%s', description = '%s'", seminar.getName(), seminar.getDescription());
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

            when(rs.getInt("seminarID")).thenReturn(seminar.getSeminarID());
            when(rs.getString("name")).thenReturn(seminar.getName());
            when(rs.getString("description")).thenReturn(seminar.getDescription());

            Seminar seminarFromRS = (Seminar) seminar.getEntityFromResultSet(rs);
            assertEquals(seminar.getSeminarID(), seminarFromRS.getSeminarID());
            assertEquals(seminar.getName(), seminarFromRS.getName());
            assertEquals(seminar.getDescription(), seminarFromRS.getDescription());
        } catch (SQLException ex) {
            Logger.getLogger(SeminarTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getQueryCondition() {
        super.test_getQueryCondition(String.format("seminarID = %d ", seminar.getSeminarID()));
    }

    @Test
    void test_getState() {
        assertEquals(State.UNCHANGED, seminar.getState());
    }

    //tests for class specific methods
    @Test
    void test_setParticipantID() {
        seminar.setSeminarID(IDOther);
        assertEquals(seminar.getSeminarID(), IDOther);
    }

    @Test
    void test_setName() {
        seminar.setName(nameOther);
        assertEquals(seminar.getName(), nameOther);
    }

    @Test
    void test_setName_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminar.setName(null));
    }

    @Test
    void test_setName_empty() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> seminar.setName(Utility.STRING_EMPTY));
    }

    @Test
    void test_setName_tooLong() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> seminar.setName(Utility.STRING_61_LENGTH));
    }

    @Test
    void test_setDescription() {
        seminar.setDescription(descriptionOther);
        assertEquals(seminar.getDescription(), descriptionOther);
    }

    @Test
    void test_setDescription_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminar.setDescription(null));
    }

    @Test
    void test_setDescription_tooLong() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> seminar.setDescription(Utility.STRING_201_LENGTH));
    }

    @Test
    void test_setCreatedByAdmin() {
        seminar.setCreatedByAdmin(createdByAdminOther);
        assertEquals(seminar.getCreatedByAdmin(), createdByAdminOther);
    }

    @Test
    void test_setCreatedByAdmin_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminar.setCreatedByAdmin(null));
    }

    @Test
    void test_setSeminarTopics() {
        List<SeminarTopic> seminarTopics = seminar.getSeminarTopics();
        seminar.setSeminarTopics(seminarTopics);
        assertTrue(SeminarTopic.equalsAll(seminarTopics, seminar.getSeminarTopics()));
    }

    @Test
    void test_setSeminarTopics_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminar.setSeminarTopics(null));
    }

    @Test
    void test_setSeminarTopics_nameNull() {
        SeminarTopic st = seminar.getSeminarTopics().get(0);
        String presenter = st.getPresenter();
        st = new SeminarTopic();
        st.setPresenter(presenter);
        seminar.getSeminarTopics().set(0, st);
        assertThrowsExactly(NullPointerException.class,
                () -> seminar.setSeminarTopics(seminar.getSeminarTopics()));
    }

    @Test
    void test_setSeminarTopics_presenterNull() {
        SeminarTopic st = seminar.getSeminarTopics().get(0);
        String name = st.getName();
        st = new SeminarTopic();
        st.setPresenter(name);
        seminar.getSeminarTopics().set(0, st);
        assertThrowsExactly(NullPointerException.class,
                () -> seminar.setSeminarTopics(seminar.getSeminarTopics()));
    }

    @Test
    void test_setState() {
        seminar.setState(State.CREATED);
        assertEquals(State.CREATED, seminar.getState());
    }

    @Test
    void test_setState_null() {
        assertThrowsExactly(NullPointerException.class,
                () -> seminar.setState(null));
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

    /*
    Cases checked:
    -> all equal
    -> all equal but seminarID
    -> all equal but name
    -> all equal but description
     */
    @ParameterizedTest
    @MethodSource("equalsProvider")
    void test_equals(Seminar sOther, boolean result) {
        assertEquals(result, seminar.equals(sOther));
    }

    static Stream<Arguments> equalsProvider() throws Exception {
        initializeSeminar();
        return Stream.of(
                arguments(Utility.clone(seminar), true),
                arguments(cloneSeminarAndModify("seminarID"), false),
                arguments(cloneSeminarAndModify("name"), true),
                arguments(cloneSeminarAndModify("description"), true)
        );
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

    /*
    Cases checked:
    -> all equal
    -> all equal but seminarID
    -> all equal but name
    -> all equal but description
    -> all equal but createdByAdmin  
    -> all equal but seminarTopics sizes differ - other is larger  
    -> all equal but seminarTopics sizes differ - other is smaller  
     */
    @ParameterizedTest
    @MethodSource("equalsAllProvider")
    void test_equalsAll(Seminar sOther, boolean result) {
        assertEquals(result, seminar.equalsAll(sOther));
    }

    static Stream<Arguments> equalsAllProvider() throws Exception {
        initializeSeminar();
        return Stream.of(
                arguments(Utility.clone(seminar), true),
                arguments(cloneSeminarAndModify("seminarID"), false),
                arguments(cloneSeminarAndModify("name"), false),
                arguments(cloneSeminarAndModify("description"), false),
                arguments(cloneSeminarAndModify("createdByAdmin"), true),
                arguments(cloneSeminarAndModify("seminarTopicsLarger"), false),
                arguments(cloneSeminarAndModify("seminarTopicsSmaller"), false)
        );
    }

    public static Seminar cloneSeminarAndModify(String attribute) throws IOException, ClassNotFoundException {
        Seminar modifiedSeminar = Utility.clone(seminar);

        switch (attribute) {
            case "seminarID" ->
                modifiedSeminar.setSeminarID(IDOther);
            case "name" ->
                modifiedSeminar.setName(nameOther);
            case "description" ->
                modifiedSeminar.setDescription(descriptionOther);
            case "createdByAdmin" ->
                modifiedSeminar.setCreatedByAdmin(createdByAdminOther);
            case "seminarTopicsLarger" ->
                modifiedSeminar.getSeminarTopics().add(
                        new SeminarTopic(modifiedSeminar, 4, "st4", "st name 4")
                );
            case "seminarTopicsSmaller" ->
                modifiedSeminar.getSeminarTopics().remove(0);
            default ->
                throw new AssertionError();
        }
        return modifiedSeminar;
    }

    @Test
    void test_toString() {
        String seminarString = seminar.toString();
        assertTrue(seminarString.contains(seminar.getName()));
        assertTrue(seminarString.contains(seminar.getDescription()));
    }
}
