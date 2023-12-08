/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.admin;

import com.fon.common.domain.Admin;
import com.fon.common.exceptions.LoginException;
import com.fon.common.utils.IOJson;
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
public class LoginSOTest {

    private LoginSO loginSO;
    private Admin admin;

    private Admin loggedAdmin;

    @BeforeEach
    void setUp() {
        loginSO = new LoginSO();
        admin = (Admin) IOJson.deserializeJson("admin", Admin.class);
        loggedAdmin = admin;
    }

    @AfterEach
    void tearDown() {
        loginSO = null;
        admin = null;
        loggedAdmin = null;
    }

    @Test
    void test_setLoggedAdmin() {
        loggedAdmin.setAdminID(admin.getAdminID());
        assertEquals(loggedAdmin.getAdminID(), admin.getAdminID());
    }

    @Test
    void test_preconditions() {
        assertDoesNotThrow(() -> loginSO.preconditions(admin));
    }

    @Test

    void test_preconditions_dClass() {
        assertThrowsExactly(Exception.class,
                () -> loginSO.preconditions(new Exception()));
    }

    @Test

    void test_preconditions_null() {
        assertThrowsExactly(Exception.class,
                () -> loginSO.preconditions(null));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation() {
        try {
            assertDoesNotThrow(() -> loginSO.execute(admin));

        } catch (Exception ex) {
            Logger.getLogger(LoginSOTest.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_wrongCredentials_username() {
        try {
            admin.setUsername(admin.getUsername()+ "Other");
            assertThrowsExactly(LoginException.class,
                    () -> loginSO.execute(admin));

        } catch (Exception ex) {
            Logger.getLogger(LoginSOTest.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    void test_executeOperation_wrongCredentials_password() {
        try {
            admin.setPassword(admin.getPassword() + "Other");
            assertThrowsExactly(LoginException.class,
                    () -> loginSO.execute(admin));

        } catch (Exception ex) {
            Logger.getLogger(LoginSOTest.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new AssertionError(ex.getMessage());
        }
    }
}
