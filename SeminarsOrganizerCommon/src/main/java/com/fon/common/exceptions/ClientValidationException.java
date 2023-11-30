/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.exceptions;

/**
 * Exception indicating validation failure for client-related operations (errors
 * regarding validation).
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class ClientValidationException extends Exception {

    /**
     * Non-parametric constructor.
     */
    public ClientValidationException() {
    }

    /**
     * Constructor with message parameter.
     *
     * @param message The detail message describing the validation failure or
     * error condition.
     */
    public ClientValidationException(String message) {
        super(message);
    }
}
