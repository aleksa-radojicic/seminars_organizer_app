/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.server.repository.db;

import com.fon.server.repository.Repository;

/**
 *
 * @author Aleksa
 * @param <T>
 */
public interface DbRepository<T> extends Repository<T> {

    default void connect() throws Exception {
        DbConnectionFactory.getInstance().getConnection();
    }

    default void disconnect() throws Exception {
        DbConnectionFactory.getInstance().getConnection().close();
    }

    default void commit() throws Exception {
        DbConnectionFactory.getInstance().getConnection().commit();
    }

    default void rollback() throws Exception {
        DbConnectionFactory.getInstance().getConnection().rollback();
    }
}
