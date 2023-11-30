/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.domain;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Aleksa
 */
public abstract class GenericEntityTest {

    protected static GenericEntity genericEntity;

    @Test
    void test_getTableName_null() {
        assertFalse(genericEntity.getTableName() == null);
    }

    @Test
    void test_getAttributeNames_null() {
        assertFalse(genericEntity.getAttributeNames() == null);
    }

    void test_getAttributeNames(String attributeNames) {
        assertTrue(genericEntity.getAttributeNames().equals(attributeNames));
    }

    @Test
    void test_getAttributeValues_null() {
        try {
            assertFalse(genericEntity.getAttributeValues() == null);
        } catch (Exception ex) {
            Logger.getLogger(GenericEntityTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    void test_getAttributeValues(String attributeValues) {
        try {
            assertTrue(genericEntity.getAttributeValues().equals(attributeValues));
        } catch (Exception ex) {
            Logger.getLogger(AdminTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    void test_getID(int adminID) {
        try {
            assertEquals(genericEntity.getID(), adminID);
        } catch (Exception ex) {
            Logger.getLogger(GenericEntityTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_setID() {
        try {
            int testValue = 2;
            genericEntity.setID(testValue);
            assertEquals(genericEntity.getID(), testValue);
        } catch (Exception ex) {
            Logger.getLogger(AdminTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_setAttributeValues_null() {
        try {
            assertFalse(genericEntity.setAttributeValues() == null);
        } catch (Exception ex) {
            Logger.getLogger(GenericEntityTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    void test_setAttributeValues(String attributeNamesAndValues) {
        try {
            assertTrue(genericEntity.setAttributeValues().equals(attributeNamesAndValues));
        } catch (Exception ex) {
            Logger.getLogger(GenericEntityTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    void test_getSelectAllQuery_null() {
        assertFalse(genericEntity.getSelectAllQuery() == null);
    }

    void test_getSelectAllQuery(String selectAllQuery) {
        assertTrue(genericEntity.getSelectAllQuery().equals(selectAllQuery));
    }

    void test_getQueryCondition(String queryCondition) {
        try {
            assertEquals(genericEntity.getQueryCondition(), queryCondition);
        } catch (Exception ex) {
            Logger.getLogger(GenericEntityTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    void test_getState_notImplemented() {
        assertThrowsExactly(UnsupportedOperationException.class,
                () -> genericEntity.getState());
    }
}
