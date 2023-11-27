/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.client.comunication;

import com.fon.common.domain.Admin;
import com.fon.common.intercomm.*;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Aleksa
 */
public class ClientCommunicator {

    private static ClientCommunicator instance;
    private final Socket socket;

    private ClientCommunicator() throws IOException {
        this.socket = new Socket("localhost", 9000);
    }

    public static ClientCommunicator getInstance() throws Exception {
        if (instance == null) {
            instance = new ClientCommunicator();
        }

        return instance;
    }

    public Response sendRequestReceiveResponse(Request request) throws Exception {
        System.out.println("request.getOperation() = " + request.getOperation());
        Communication.getInstance().send(socket, request);
        return (Response) Communication.getInstance().receive(socket);
    }
}
