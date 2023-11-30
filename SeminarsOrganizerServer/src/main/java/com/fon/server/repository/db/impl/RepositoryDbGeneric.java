/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.repository.db.impl;

import com.fon.common.domain.GenericEntity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import com.fon.server.repository.db.DbConnectionFactory;
import com.fon.server.repository.db.DbRepository;

/**
 * Class representing generic database repository with MySQL implementation of
 * implemented CRUD operations.
 *
 * <p>
 * Implements {@code DbRepository<GenericEntity>} interface.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class RepositoryDbGeneric implements DbRepository<GenericEntity> {

    @Override
    public void create(GenericEntity arg) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
                .append(arg.getTableName())
                .append(" (")
                .append(arg.getAttributeNames())
                .append(")")
                .append(" VALUES (")
                .append(arg.getAttributeValues())
                .append(")");
        String query = sb.toString();
//        System.out.println(query);

        try (Statement statement = connection.createStatement()) {
            statement.execute(query, Statement.RETURN_GENERATED_KEYS);
            try (ResultSet rsKey = statement.getGeneratedKeys()) {
                if (rsKey.next()) {
                    int id = rsKey.getInt(1);
                    arg.setID(id);
                }
            }
        }
    }

    @Override
    public void update(GenericEntity arg) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ")
                .append(arg.getTableName())
                .append(" SET ")
                .append(arg.setAttributeValues())
                .append(" WHERE ")
                .append(arg.getQueryCondition());
        String query = sb.toString();
        System.out.println(query);

        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    @Override
    public void delete(GenericEntity arg, String whereSection) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ")
                .append(arg.getTableName())
                .append(" ")
                .append(whereSection);
        String query = sb.toString();
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    @Override
    public List<GenericEntity> getByCondition(GenericEntity arg, String whereSection) throws Exception {
        List<GenericEntity> genericEntities = new LinkedList();
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        String query = arg.getSelectAllQuery() + " " + whereSection;
//        System.out.println("query = " + query);
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                genericEntities.add(arg.getEntityFromResultSet(resultSet));
            }
        }
        return genericEntities;
    }
}
