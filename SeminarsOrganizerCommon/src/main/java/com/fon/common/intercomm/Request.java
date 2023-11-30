/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.intercomm;

import java.io.Serializable;

/**
 * Class representing a request that a client sends to server. It contains
 * the purpose of the request and the data that will be sent.
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class Request implements Serializable {

    /**
     * Data client sends (server receives) as {@code Object}.
     */
    private Object argument;

    /**
     * Operation server needs to perform as {@code Operation} when server
     * receives the request.
     */
    private Operation operation;

    /**
     * Constructor with all parameters.
     *
     * @param argument Data client sends (server receives) as {@code Object}.
     * @param operation Operation server needs to perform as
     * {@code Operation} when server receives the request.
     */
    public Request(Object argument, Operation operation) {
        this.argument = argument;
        this.operation = operation;
    }

    /**
     * Non-parametric constructor.
     */
    public Request() {
    }

    /**
     * Getter for argument.
     *
     * @return Data client sends (server receives) as {@code Object}.
     */
    public Object getArgument() {
        return argument;
    }

    /**
     * Setter for argument.
     *
     * @param argument Data client sends (server receives) as {@code Object}.
     */
    public void setArgument(Object argument) {
        this.argument = argument;
    }

    /**
     * Getter for operation.
     *
     * @return Operation server needs to perform as {@code Operation} when
     * server receives the request.
     *
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * Setter for operation.
     *
     * @param operation Operation server needs to perform as
     * {@code Operation} when server receives the request.
     */
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    /**
     * toString method which returns all attributes of Request.
     *
     * @return String representation of the Request.
     */
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
