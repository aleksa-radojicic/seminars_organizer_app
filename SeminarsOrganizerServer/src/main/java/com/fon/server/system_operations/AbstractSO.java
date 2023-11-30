/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.system_operations;

import com.fon.server.repository.db.DbRepository;
import com.fon.server.repository.db.impl.RepositoryDbGeneric;
import com.fon.server.repository.Repository;

/**
 * Abstract class for system operations, equipped with methods for working with
 * database transaction.
 *
 * <p>
 * Uses template method pattern.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public abstract class AbstractSO {

    /**
     * Represents a read-only instance of database repository as
     * {@code  Repository}, default is {@code new RepositoryDbGeneric()}.
     */
    protected final Repository REPOSITORY = new RepositoryDbGeneric();

    /**
     * Executes a system operation within a transaction.
     *
     * @param arg Object that probably needs to be persisted as {@code Object}.
     * @throws Exception When an error occurred related to the input argument
     * and / or executing the system operation. In that case transaction will be
     * rolled back.
     */
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

    /**
     * Checks if all preconditions are satisfied regarding the input argument
     * before executing the system operation.
     *
     * @param arg Object that needs to be persisted as {@code Object} or that
     * represents a condition for retrieving objects from a database.
     * @throws Exception When an error occurred related to the input argument.
     */
    protected abstract void preconditions(Object arg) throws Exception;

    /**
     * Executes the system operation.
     *
     * @param arg Object that needs to be persisted as {@code Object} or that
     * represents a condition for retrieving objects from a database.
     * @throws Exception When an error occurred related to the input argument.
     */
    protected abstract void executeOperation(Object arg) throws Exception;

    /**
     * Starts a database transaction by connecting with the database.
     *
     * @throws Exception When the connection with the database cannot be
     * established.
     */
    private void startTransaction() throws Exception {
        ((DbRepository) REPOSITORY).connect();
    }

    /**
     * Commits a database transaction.
     *
     * @throws Exception When an error happened during the committing of the
     * transaction.
     */
    private void commitTransaction() throws Exception {
        ((DbRepository) REPOSITORY).commit();
    }

    /**
     * Rolls back a database transaction.
     *
     * @throws Exception When an error happened during the rolling back of the
     * transaction.
     */
    private void rollbackTransaction() throws Exception {
        ((DbRepository) REPOSITORY).rollback();
    }

    /**
     * Ends a database transaction by closing the connection with the database.
     *
     * @throws Exception When connection with the database cannot be
     * established.
     */
    private void endTransaction() throws Exception {
        ((DbRepository) REPOSITORY).disconnect();
    }
}
