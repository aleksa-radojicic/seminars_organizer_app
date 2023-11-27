/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.intercomm;

import java.io.Serializable;

/**
 *
 * @author Aleksa
 */
public class Request implements Serializable {

    private Object argument;
    private Operation operation;

    public Request(Object argument, Operation operation) {
        this.argument = argument;
        this.operation = operation;
    }

    public Request() {
    }

    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Request{");
        sb.append("argument=").append(argument);
        sb.append(", operation=").append(operation);
        sb.append('}');
        return sb.toString();
    }
}
