package com.fon.server.repository;

import java.util.List;

/**
 *
 * @author Aleksa
 * @param <GenericEntity>
 */
public interface Repository<GenericEntity> {

    void create(GenericEntity arg) throws Exception;

    void update(GenericEntity arg) throws Exception;

    void delete(GenericEntity arg, String whereSection) throws Exception;

    public List<GenericEntity> getByCondition(GenericEntity arg, String condition) throws Exception;
}
