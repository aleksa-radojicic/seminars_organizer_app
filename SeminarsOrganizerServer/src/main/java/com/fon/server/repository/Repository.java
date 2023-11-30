package com.fon.server.repository;

import java.util.List;

/**
 * Parametric interface with supported database CRUD operations.
 *
 * @author Aleksa
 * @since 0.0.1
 * @param <GenericEntity>
 */
public interface Repository<GenericEntity> {

    /**
     * Creates a specified domain object in the database.
     *
     * @param arg Domain object that needs to be created as
     * {@code GenericEntity}.
     * @throws Exception When an error happened while creating a given domain
     * object.
     */
    void create(GenericEntity arg) throws Exception;

    /**
     * Saves a specified domain object in the database.
     *
     * @param arg Domain object that needs to be saved as {@code GenericEntity}.
     * @throws Exception When an error happened while saving a given domain
     * object.
     */
    void update(GenericEntity arg) throws Exception;

    /**
     * Deletes a specified domain object from the database that meets a certain
     * condition.
     *
     * @param arg Domain object that needs to be deleted as
     * {@code GenericEntity}.
     * @param condition The entire WHERE query section as {@code String} used
     * deleting a record from the corresponding database table given by
     * {@code arg}.
     * @throws Exception When an error happened while deleting a given domain
     * object.
     */
    void delete(GenericEntity arg, String condition) throws Exception;

    /**
     * Retrieves all domain object from the database that meet a certain
     * condition.
     *
     * @param arg Domain object as {@code GenericEntity} that signifies what
     * domain objects need to be retrieved from the database.
     * @param condition The entire WHERE query section as {@code String} used
     * for filtering a corresponding database table given by {@code arg}.
     * @return All domain objects retrieved from the database that meet a
     * certain condition as {@code List<GenericEntity>}.
     * @throws Exception When an error happened while retrieving all given
     * domain objects.
     */
    public List<GenericEntity> getByCondition(GenericEntity arg, String condition) throws Exception;
}
