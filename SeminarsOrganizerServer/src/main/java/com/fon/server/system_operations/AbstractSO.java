/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations;

import com.fon.server.repository.db.DbRepository;
import com.fon.server.repository.db.impl.RepositoryDbGeneric;
import com.fon.server.repository.Repository;

/**
 *
 * @author Aleksa
 */
public abstract class AbstractSO {

    protected final Repository REPOSITORY = new RepositoryDbGeneric();

    public final void execute(Object arg) throws Exception {
        try {
            preconditions(arg);
            startTransaction();
            executeOperation(arg);
            commitTransaction();
        } catch (Exception exception) {
            rollbackTransaction();
            throw exception;
        } finally {
            endTransaction();
        }
    }

    protected abstract void preconditions(Object arg) throws Exception;

    protected abstract void executeOperation(Object arg) throws Exception;

    private void startTransaction() throws Exception {
        ((DbRepository) REPOSITORY).connect();
    }

    private void commitTransaction() throws Exception {
        ((DbRepository) REPOSITORY).commit();
    }

    private void rollbackTransaction() throws Exception {
        ((DbRepository) REPOSITORY).rollback();
    }

    private void endTransaction() throws Exception {
        ((DbRepository) REPOSITORY).disconnect();
    }
}
