/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.common.intercomm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Singleton class that manages communication functionalities via sockets.
 *
 * <p>
 * This class provides methods to handle sending and receiving objects through
 * sockets.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 *
 */
public class Communication {

    /**
     * The singleton instance of the Communication class.
     */
    private static Communication instance;

    /**
     * Getter of instance (Singleton pattern).
     *
     * @return The singleton instance of the Communication class.
     */
    public static Communication getInstance() {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    /**
     * Private non-parametric constructor.
     */
    private Communication() {
    }

    /**
     * Sends an object through the provided socket's output stream.
     *
     * @param socket The socket used for communication.
     * @param object The object to be sent.
     * @throws IOException If an I/O error occurs during the sending process.
     */
    public void send(Socket socket, Object object) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(object);
        oos.flush();
    }

    /**
     * Receives an object from the provided socket's input stream.
     *
     * @param socket The socket used for communication.
     * @return The received object.
     * @throws IOException If an I/O error occurs during the receiving process.
     * @throws ClassNotFoundException If the class of the received object cannot
     * be found.
     */
    public Object receive(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        return ois.readObject();
    }
}
