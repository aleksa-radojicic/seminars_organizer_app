/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.intercomm;

import java.io.Serializable;

/**
 * Class representing a response that server sends to a client. It contains
 * the purpose of the response, data that will be sent and information about errors
 * that might have happened.
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class Response implements Serializable {

    /**
     * Data server sends (server receives) as {@code Object}.
     */
    private Object result;

    /**
     * Operation client needs to perform as {@code Operation} when server sends
     * the response.
     */
    private Operation operation;

    /**
     * Error that might have happened at server's side as {@code Exception}.
     */
    private Exception exception;

    /**
     * Constructor with all parameters.
     *
     * @param result Data server sends (server receives) as {@code Object}.
     * @param operation Operation client needs to perform as {@code Operation}
     * when server sends the response.
     * @param exception Error that might have happened at server's side as
     * {@code Exception}.
     */
    public Response(Object result, Operation operation, Exception exception) {
        this.result = result;
        this.operation = operation;
        this.exception = exception;
    }

    /**
     * Non-parametric constructor.
     */
    public Response() {
    }

    /**
     * Getter for result.
     *
     * @return Data server sends (server receives) as {@code Object}.
     */
    public Object getResult() {
        return result;
    }

    /**
     * Setter for result.
     *
     * @param result Data server sends (server receives) as {@code Object}.
     */
    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * Getter for operation.
     *
     * @return Operation client needs to perform as {@code Operation} when
     * server sends the response.
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * Setter for operation.
     *
     * @param operation Operation client needs to perform as {@code Operation}
     * when server sends the response.
     */
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    /**
     * Getter for exception.
     *
     * @return Error that might have happened at server 's side as
     * {@code Exception}.
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Setter for exception.
     *
     * @param exception Error that might have happened at server 's side as
     * {@code Exception}.
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }
}
