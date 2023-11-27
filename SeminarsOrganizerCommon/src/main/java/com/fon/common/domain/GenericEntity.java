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
 *
 * @author Aleksa
 */
public interface GenericEntity {

    default public String getTableName() {
        String className = this.getClass().getSimpleName();
        String firstLetterLowercased = className.substring(0, 1).toLowerCase();
        String restOfClassName = className.substring(1);
        String tableName = firstLetterLowercased + restOfClassName + "s";

        return tableName;
    }

    //NOTE: Works only for domain classes that do not have foreign keys
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

    //NOTE: Works only for domain classes that do not have foreign keys
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

    default public void setID(int ID) throws Exception {
        String methodName = "set" + this.getClass().getSimpleName() + "ID";
        Method methodSetID = this.getClass().getMethod(methodName, int.class);
        methodSetID.invoke(this, ID);
    }

    default public int getID() throws Exception {
        String methodName = "get" + this.getClass().getSimpleName() + "ID";
        Method methodGetID = this.getClass().getMethod(methodName);
        return (int) methodGetID.invoke(this);
    }

    default public String setAttributeValues() throws Exception {
        String[] attributeList = getAttributeNames().split(", ");
        String[] attributeValues = getAttributeValues().split(", ");

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < attributeList.length; i++) {
            if (isStringOrDate(attributeValues[i])) {
                result.append(attributeList[i])
                        .append(" = '")
                        .append(attributeValues[i])
                        .append("'");
            } else {
                result.append(attributeList[i])
                        .append(" = ")
                        .append(attributeValues[i]);
            }

            if (i != attributeList.length - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    default public String getSelectAllQuery() {
        return "SELECT * from " + getTableName();
    }

    public GenericEntity getEntityFromResultSet(ResultSet rs) throws SQLException;

    public State getState();

    default public String getQueryCondition() throws Exception {
        return getTableName().substring(0, getTableName().length() - 1) + "ID = " + getID() + " ";
    }

    private boolean isStringOrDate(String value) {
        return value.startsWith("'") && value.endsWith("'") || isDate(value);
    }

    private boolean isDate(String value) {
        try {
            new Date(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
