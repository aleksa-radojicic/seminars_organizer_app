/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fon.common.domain;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Generic domain entity implemented by all domain classes. Contains methods for
 * interacting with a generic database repository.
 *
 *
 * <p>
 * <strong>Note:</strong> Certain default methods in this interface assume
 * working with domain classes that do not have foreign keys.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public interface GenericEntity {

    /**
     * Returns the database table name of the implementing domain class as
     * string. Used for MySQL queries.
     *
     * <p>
     * This method returns the table name based on the implementing class's
     * simple name. It assumes a convention of using the class name in
     * lowercase, with an added 's' at the end, representing the plural form of
     * the class name as the table name.
     * </p>
     *
     * @return Database table name as string.
     */
    default public String getTableName() {
        String className = this.getClass().getSimpleName();
        String firstLetterLowercased = className.substring(0, 1).toLowerCase();
        String restOfClassName = className.substring(1);
        String tableName = firstLetterLowercased + restOfClassName + "s";

        return tableName;
    }

    /**
     * Returns the names of attributes belonging to the implementing domain
     * class as string. Used for INSERT MySQL queries.
     *
     * <p>
     * This method collects the names of attributes declared within the
     * implementing class. It iterates through the class's fields and constructs
     * a comma-separated string containing the names of the attributes.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> The default implementation assumes that the
     * implementing domain class doesn't have foreign keys.
     * </p>
     *
     * @return Comma-separated names of attributes as string.
     */
    default public String getAttributeNames() {
        Field[] fields = this.getClass().getDeclaredFields();
        StringBuilder attributeNames = new StringBuilder();

        for (Field field : fields) {
            attributeNames.append(field.getName()).append(", ");
        }

        if (attributeNames.length() > 0) {
            attributeNames.setLength(attributeNames.length() - 2);
        }

        return attributeNames.toString();
    }

    /**
     * Returns the values of attributes belonging to the implementing domain
     * class as string. Used for INSERT MySQL queries.
     *
     * <p>
     * This method returns the values of attributes declared within the
     * implementing class. It iterates through the class's attribute names,
     * invokes corresponding getter methods to obtain attribute values and
     * creates a string containing these values.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> The default implementation assumes that the
     * implementing domain class doesn't have foreign keys. It handles values of
     * type String or Date by enclosing them with single quotes before appending
     * to the resulting string.
     * </p>
     *
     * @return Comma-separated values of attributes as string.
     *
     * @throws Exception If there is an issue invoking getter methods or
     * handling attribute values.
     */
    default public String getAttributeValues() throws Exception {
        StringBuilder attributeValues = new StringBuilder();
        String[] attributeList = getAttributeNames().split(", ");

        for (String attributeName : attributeList) {
            String capitalizedAttributeName = attributeName.substring(0, 1).toUpperCase() + attributeName.substring(1);
            String getterMethodName = "get" + capitalizedAttributeName;
            Method getterMethod = this.getClass().getMethod(getterMethodName);

            Object value = getterMethod.invoke(this);

            if (value instanceof String || value instanceof Date) {
                attributeValues.append("'").append(value).append("'");
            } else {
                attributeValues.append(value);
            }

            attributeValues.append(", ");
        }

        if (attributeValues.length() > 0) {
            attributeValues.setLength(attributeValues.length() - 2); // Remove the extra trailing comma and space
        }

        return attributeValues.toString();
    }

    /**
     * Sets the ID for the implementing class.
     *
     * <p>
     * This method sets the ID for the implementing domain class based on the
     * provided value. It dynamically constructs the method name for setting the
     * ID using reflection and invokes the appropriate setter method with the
     * given ID parameter.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> This method assumes the existence of a specific
     * setter method for setting the ID in the implementing class.
     * </p>
     *
     * @param ID The ID value to be set for the implementing class.
     *
     * @throws Exception If there is an issue invoking the setter method or
     * handling the ID value.
     */
    default public void setID(int ID) throws Exception {
        String methodName = "set" + this.getClass().getSimpleName() + "ID";
        Method methodSetID = this.getClass().getMethod(methodName, int.class);
        methodSetID.invoke(this, ID);
    }

    /**
     * Returns the ID of the implementing class.
     *
     * <p>
     * This method returns the ID of the implementing domain class by
     * dynamically constructing the method name for retrieving the ID using
     * reflection and then invoking the corresponding getter method.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> This method assumes the existence of a specific
     * getter method for retrieving the ID in the implementing class.
     * </p>
     *
     * @return The ID value of the class as integer.
     *
     * @throws Exception If there is an issue invoking the getter method or
     * handling the ID value.
     */
    default public int getID() throws Exception {
        String methodName = "get" + this.getClass().getSimpleName() + "ID";
        Method methodGetID = this.getClass().getMethod(methodName);
        return (int) methodGetID.invoke(this);
    }

    /**
     * Constructs a string of attribute-value pairs for updating database
     * records for the implementing domain class. Used for UPDATE MySQL queries.
     *
     * <p>
     * This method constructs a string containing attribute-value pairs based on
     * the attributes and their corresponding values in the implementing class.
     * It utilizes the attribute names and values returned from the class and
     * formats them into MySQL-compatible pairs.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> The default implementation assumes that the
     * implementing domain class doesn't have foreign keys and the availability
     * of both attribute names and their corresponding values from the
     * implementing class.
     * </p>
     *
     * @return Formatted attribute-value pairs for database updates as string.
     *
     * @throws Exception If there is an issue retrieving attribute names or
     * values or handling the construction of attribute-value pairs (reflection
     * related).
     */
    default public String setAttributeValues() throws Exception {
        String[] attributeList = getAttributeNames().split(", ");
        String[] attributeValues = getAttributeValues().split(", ");

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < attributeList.length; i++) {
            result.append(attributeList[i])
                    .append(" = ")
                    .append(attributeValues[i]);

            if (i != attributeList.length - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    /**
     * Returns a SQL query to retrieve all records from the database table
     * corresponding to the implementing domain class.
     *
     *
     * @return A SQL query string to retrieve all records from the corresponding
     * database table as string.
     */
    default public String getSelectAllQuery() {
        return "SELECT * from " + getTableName();
    }

    /**
     * Returns a specific GenericEntity object created from a ResultSet
     * corresponding to the implementing domain class.
     *
     * @param rs The ResultSet containing data used to construct the entity
     * object.
     *
     * @return An instance of GenericEntity object constructed from the
     * ResultSet.
     *
     * @throws SQLException If there is an issue accessing or retrieving data
     * from the ResultSet.
     */
    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException;

    /**
     * Returns the state of the implemented domain class indicating its status
     * for database operations.
     *
     * @return The state of the object indicating its status for database
     * operations as State object.
     */
    public State getState();

    /**
     * Returns an ID query condition for database operations as string.
     *
     * <p>
     * This method generates a MySQL query condition for database operations by
     * combining the table name and the object's ID.
     * </p>
     *
     * @return The ID query condition as string.
     *
     * @throws Exception If there is an issue retrieving the object's ID
     * (reflection related).
     */
    default public String getQueryCondition() throws Exception {
        return getTableName().substring(0, getTableName().length() - 1) + "ID = " + getID() + " ";
    }
}
