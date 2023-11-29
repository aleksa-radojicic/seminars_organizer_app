/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.main;

import com.fon.server.forms.ServerForm;

/**
 * Class responsible for opening the server form.
 *
 * @author Aleksa
 */
public class Server {

    /**
     * Main method initiating the process of opening the server form.
     *
     * @param args The command-line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    /**
     * Creates and shows the server form.
     */
    private void start() {
        new ServerForm().setVisible(true);
    }
}
