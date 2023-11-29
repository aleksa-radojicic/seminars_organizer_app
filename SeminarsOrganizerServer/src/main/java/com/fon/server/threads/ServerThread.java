/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.threads;

import com.fon.common.domain.Admin;
import com.fon.server.constants.ServerConstants;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;
import com.fon.server.forms.ServerForm;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Represents a server thread that handles client connections and logged
 * administrators.
 *
 * <p>
 * This class, that extends {@link Thread}, handles accepting client
 * connections, managing logged-in administrators, and performing server
 * shutdown operations when required.
 * </p>
 *
 * @author Aleksa
 * @since 0.0.1
 */
public class ServerThread extends Thread {

    /**
     * The server socket used for handling client connections as
     * {@link ServerSocket} (read only).
     *
     */
    private final ServerSocket serverSocket;

    /**
     * List of client handler threads managing connected clients as
     * {@link List<ClientHandlerThread>}.
     */
    private List<ClientHandlerThread> clientHandlers;

    /**
     * List of client handler threads representing logged-in administrators as
     * {@link List<ClientHandlerThread>}.
     */
    private List<ClientHandlerThread> loggedAdminsChs;

    /**
     * Counter to track the number of connected clients as {@link int}.
     */
    private int clientNumber;

    /**
     * Reference to the server's form as {@link ServerForm}.
     */
    private final ServerForm serverForm;

    /**
     * Constructor with serverForm parameter.
     *
     * <p>
     * Loads server port from configuration file and initializes serverSocket
     * using it, initializes clientHandlers and loggedAdminsChs to empty lists
     * and initializes clientNumber to {@code 1}.
     * </p>
     *
     * @param serverForm Reference to the server's form as {@link ServerForm}.
     * @throws IOException If an I/O error occurs while loading configuration
     * file or while initializing serverSocket.
     */
    public ServerThread(ServerForm serverForm) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(ServerConstants.SERVER_CONFIG_FILE_PATH));
        String port = properties.getProperty(ServerConstants.SERVER_CONFIG_PORT);
        serverSocket = new ServerSocket(Integer.parseInt(port));
        clientHandlers = new LinkedList();
        loggedAdminsChs = new LinkedList();
        clientNumber = 0;
        this.serverForm = serverForm;
    }

    /**
     * Getter for serverSocket.
     *
     * @return The server socket used for handling client connections as
     * {@link ServerSocket}.
     */
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * Getter for clientHandlers.
     *
     * @return List of client handler threads managing connected clients as
     * {@link List<ClientHandlerThread>}.
     */
    public List<ClientHandlerThread> getClientHandlers() {
        return clientHandlers;
    }

    /**
     * Setter for clientHandlers.
     *
     * @param clientHandlers List of client handler threads managing connected
     * clients as {@link List<ClientHandlerThread>}.
     */
    public void setClientHandlers(List<ClientHandlerThread> clientHandlers) {
        this.clientHandlers = clientHandlers;
    }

    /**
     * Getter for loggedAdminsChs.
     *
     * @return List of client handler threads representing logged-in
     * administrators as {@link List<ClientHandlerThread>}.
     */
    public List<ClientHandlerThread> getLoggedAdmins() {
        return loggedAdminsChs;
    }

    /**
     * Setter for loggedAdminsChs.
     *
     * @param loggedAdmins List of client handler threads representing logged-in
     * administrators as {@link List<ClientHandlerThread>}.
     */
    public void setLoggedAdmins(List<ClientHandlerThread> loggedAdmins) {
        this.loggedAdminsChs = loggedAdmins;
    }

    /**
     * Initiates and manages client connections within the server thread.
     *
     * <p>
     * This method is responsible for managing client connections by
     * continuously listening for incoming connections on the server socket. It
     * accepts client connections, creates a {@link ClientHandlerThread} to
     * manage each client, and starts the threads.
     * </p>
     *
     * <p>
     * Upon accepting a client connection, it increments the client number and
     * adds a new {@code ClientHandlerThread} to the list of client handlers to
     * manage the connected client. This method runs in a loop until the server
     * socket is closed, at which point it finalizes its operations.
     * </p>
     */
    @Override
    public void run() {
        System.out.println("Server thread has started");
        try {
            while (!serverSocket.isClosed()) {
                System.out.println("Waiting for clients...");
                Socket socket = serverSocket.accept();
                System.out.println("Client " + clientNumber + " has connected ");
                ClientHandlerThread ch = new ClientHandlerThread(this, socket, clientNumber);
                clientHandlers.add(ch);
                ch.start();
                clientNumber++;
            }
        } catch (IOException e) {
        }
        System.out.println("Server thread has finished");
    }

    /**
     * Stops the server, logging out all clients and closing the server socket.
     *
     * @throws IOException If an I/O error occurs while closing the server
     * socket.
     */
    public void stopServer() throws IOException {
        logoutClients();
        serverSocket.close();
    }

    /**
     * Logs out all connected clients.
     *
     * <p>
     * This method iterates through the list of {@code ClientHandlerThread}s
     * managing connected clients. For each client handler thread found, it
     * invokes the {@link ClientHandlerThread#logout()} method to perform the
     * logout process for the associated client.
     * </p>
     */
    private void logoutClients() {
        Iterator<ClientHandlerThread> iter = clientHandlers.iterator();

        while (iter.hasNext()) {
            ClientHandlerThread ch = iter.next();
            iter.remove();
            ch.logout();
        }
    }

    /**
     *
     *
     * @param ch
     * @throws IOException
     */
    public void logout(ClientHandlerThread ch) throws IOException {
        removeAdminFromLoggedAdmins(ch);
        removeClientHandler(ch);
    }

    /**
     * Removes specific client handler from the clientHandlers list.
     *
     * @param pr Specific {@link ClientHandlerThread} that needs to be removed.
     */
    private void removeClientHandler(ClientHandlerThread pr) {
        clientHandlers.remove(pr);
    }

    /**
     * Handles event of admin's login successfully.
     *
     * <p>
     * Updates list of logged admins by adding current client handler thread and
     * adds logged admin to the table displaying online admins on the server
     * form.
     * </p>
     *
     * @param ch Thread handling the client who wants to log in as
     * {@link ClientHandlerThread}.
     */
    public void login(ClientHandlerThread ch) {
        System.out.println("Admin '" + ch.getLoggedAdmin().getFullName() + "' has logged in");
        loggedAdminsChs.add(ch);
        Admin loggedInAdmin = ch.getLoggedAdmin();
        serverForm.addAdminToTable(loggedInAdmin);
    }

    /**
     * Handles event of admin's logout successfully.
     *
     * <p>
     * Updates list of logged admins by removing current client handler thread
     * and removes logged admin from the table displaying online admins on the
     * server form.
     * </p>
     *
     * @param ch Thread handling the client who wants to log out as
     * {@link ClientHandlerThread}.
     */
    public void removeAdminFromLoggedAdmins(ClientHandlerThread ch) {
        if (loggedAdminsChs.contains(ch)) {
            Admin loggedOutAdmin = ch.getLoggedAdmin();
            System.out.println("Admin '" + loggedOutAdmin.getFullName() + "' has logged out");
            loggedAdminsChs.remove(ch);
            serverForm.removeAdminFromTable(loggedOutAdmin);
        }
    }

    /**
     * Checks if an admin is already logged in.
     *
     * <p>
     * It extracts all {@link Admin}s from loggedAdminsChs and checks if an
     * admin is in the list of all logged admins.
     * </p>
     *
     * @param admin Admin that needs to be checked if he is already logged in.
     * @return {@code true} if admin is already logged in, otherwise
     * {@code false}.
     */
    public boolean checkIfAdminIsAlreadyLoggedIn(Admin admin) {
        List<Admin> loggedAdmins = new LinkedList();
        loggedAdminsChs.forEach(x -> loggedAdmins.add(x.getLoggedAdmin()));

        return loggedAdmins.contains(admin);
    }
}
