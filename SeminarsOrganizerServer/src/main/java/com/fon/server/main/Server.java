/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fon.server.main;

import com.fon.server.forms.ServerForm;

/**
 *
 * @author Aleksa
 */
public class Server {

    public static void main(String[] args) {
        Server server = new Server();
        try {
                    System.setProperty("jansi.passthrough", "true");
                            System.out.println("\u001B[31mThis will be red\u001B[0m");


            server.start();
        } catch (Exception e) {
            System.out.println("Error while starting server: " + e.getMessage());
        }
    }

    private void start() throws Exception {
        new ServerForm().setVisible(true);
    }
}
