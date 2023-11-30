/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.client.comunication;

import com.fon.common.intercomm.*;
import java.io.IOException;
import java.net.Socket;

/**
 * Singleton class responsible for sending requests to the server and receiving
 * responses from the server.
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class ClientCommunicator {

    /**
     * The singleton instance of the {@code ClientCommunicator} class.
     */
    private static ClientCommunicator instance;

    /**
     * The socket for communication with the server as {@code Socket}.
     */
    private final Socket socket;

    /**
     * Private non-parametric constructor.
     *
     * @throws java.io.IOException When connection to the server cannot be
     * established.
     */
    private ClientCommunicator() throws IOException {
        this.socket = new Socket("localhost", 9000);
    }

    /**
     * Getter of instance (Singleton pattern).
     *
     * @return The singleton instance of the {@code ClientCommunicator} class.
     * @throws java.io.IOException When connection to the server cannot be
     * established.
     */
    public static ClientCommunicator getInstance() throws IOException {
        if (instance == null) {
            instance = new ClientCommunicator();
        }
        return instance;
    }

    /**
     * Sends a request to the server and receives a response from the server.
     *
     * @param request Request as {@code Request} that will be sent to the
     * server.
     * @return Response as {@code Response} from the server.
     * @throws Exception When there is an I/O socket error on the client-side or
     * server-side.
     */
    public Response sendRequestReceiveResponse(Request request) throws Exception {
        System.out.println("request.getOperation() = " + request.getOperation());
        Communication.getInstance().send(socket, request);
        return (Response) Communication.getInstance().receive(socket);
    }
}
