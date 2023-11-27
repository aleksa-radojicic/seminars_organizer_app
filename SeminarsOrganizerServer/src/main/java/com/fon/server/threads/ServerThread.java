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
 *
 * @author Aleksa
 */
public class ServerThread extends Thread {

    private final ServerSocket serverSocket;
    private List<ClientHandlerThread> clientHandlers;
    private List<ClientHandlerThread> loggedAdminsChs;
    private int clientNumber;
    private final ServerForm serverForm;

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

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

        public List<ClientHandlerThread> getClientHandlers() {
        return clientHandlers;
    }

    public void setClientHandlers(List<ClientHandlerThread> clientHandlers) {
        this.clientHandlers = clientHandlers;
    }

    public List<ClientHandlerThread> getLoggedAdmins() {
        return loggedAdminsChs;
    }

    public void setLoggedAdmins(List<ClientHandlerThread> loggedAdmins) {
        this.loggedAdminsChs = loggedAdmins;
    }
    
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

    public void stopServer() throws IOException {
        logoutClients();
        serverSocket.close();
    }

    private void logoutClients() {
        Iterator<ClientHandlerThread> iter = clientHandlers.iterator();

        while (iter.hasNext()) {
            ClientHandlerThread ch = iter.next();
            iter.remove();
            ch.logout();
        }
    }

    public void logout(ClientHandlerThread ch) throws IOException {
        removeAdminFromLoggedAdmins(ch);
        removeClientHandler(ch);
    }

    private void removeClientHandler(ClientHandlerThread pr) {
        clientHandlers.remove(pr);
    }

    //addAdminToLoggedAdmins
    public void login(ClientHandlerThread ch) {
        System.out.println("Admin '" + ch.getLoggedAdmin().getFullName() + "' has logged in");
        loggedAdminsChs.add(ch);
        Admin loggedInAdmin = ch.getLoggedAdmin();
        serverForm.addAdminToTable(loggedInAdmin);
    }

    public void removeAdminFromLoggedAdmins(ClientHandlerThread ch) {
        if (loggedAdminsChs.contains(ch)) {
            Admin loggedOutAdmin = ch.getLoggedAdmin();
            System.out.println("Admin '" + loggedOutAdmin.getFullName() + "' has logged out");
            loggedAdminsChs.remove(ch);
            serverForm.removeAdminFromTable(loggedOutAdmin);
        }
    }

    public boolean checkIfAdminIsAlreadyLoggedIn(Admin admin) {
        List<Admin> loggedAdmins = new LinkedList();
        loggedAdminsChs.forEach(x -> loggedAdmins.add(x.getLoggedAdmin()));
        
        return loggedAdmins.contains(admin);
    }
}
