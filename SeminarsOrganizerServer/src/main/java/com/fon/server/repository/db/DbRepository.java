/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.server.repository.db;

import com.fon.server.repository.Repository;

/**
 * Parametric interface with default implementations of connect, disconnect, commit and
 * rollback operations regarding a database.
 *
 * @author Aleksa
 * @since 0.0.1
 * @param <T>
 */
public interface DbRepository<T> extends Repository<T> {

    /**
     * Connects to a database.
     *
     * @throws Exception When the connection with the database cannot be
     * established.
     */
    default void connect() throws Exception {
        DbConnectionFactory.getInstance().getConnection();
    }

    /**
     * Disconnects from the database.
     *
     * @throws Exception When the connection to the database cannot be
     * closed.
     */
    default void disconnect() throws Exception {
        DbConnectionFactory.getInstance().getConnection().close();
    }

    /**
     * Commits a database transaction.
     *
     * @throws Exception When an error happened during the committing of the
     * transaction.
     */
    default void commit() throws Exception {
        DbConnectionFactory.getInstance().getConnection().commit();
    }

    /**
     * Rolls back a database transaction.
     *
     * @throws Exception When an error happened during the rolling back of the
     * transaction.
     */
    default void rollback() throws Exception {
        DbConnectionFactory.getInstance().getConnection().rollback();
    }
}
