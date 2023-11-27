/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations.admin;

import java.util.List;
import com.fon.common.domain.Admin;
import com.fon.common.exceptions.ServerValidationException;
import com.fon.server.system_operations.AbstractSO;

/**
 *
 * @author Aleksa
 */
public class LoginSO extends AbstractSO {

    private Admin loggedAdmin;

    public Admin getLoggedAdmin() {
        return loggedAdmin;
    }

    public void setLoggedAdmin(Admin loggedAdmin) {
        this.loggedAdmin = loggedAdmin;
    }

    @Override
    protected void preconditions(Object arg) throws Exception {
        if (arg == null || !(arg instanceof Admin)) {
            throw new Exception("Послати објекат није одговарајућег типа");
        }
    }

    @Override
    protected void executeOperation(Object arg) throws Exception {
        Admin admin = (Admin) arg;

        String whereQuerySection = "WHERE username = '" + admin.getUsername() + "' and password = '" + admin.getPassword() + "'";

        List<Admin> admins = (List<Admin>) REPOSITORY.getByCondition(new Admin(), whereQuerySection);
//        for (Admin a : admins) {
//            if (a.getUsername().equals(admin.getUsername()) && a.getPassword().equals(admin.getPassword())) {
//                loggedAdmin = a;
//                return;
//            }
//        }
        if (admins.isEmpty()) {
            throw new ServerValidationException("Нетачно корисничко име или лозинка");
        }
        loggedAdmin = admins.get(0);
    }
}
