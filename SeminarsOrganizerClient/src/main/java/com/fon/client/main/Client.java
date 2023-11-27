/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.fon.client.main;

import com.fon.client.forms.LoginForm;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Aleksa
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        try {
            client.start();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Сервер није покренут или сте унели погрешан број порта.");
        }
    }

    public void start() throws IOException, Exception {
        JFrame form = new LoginForm();
        form.setVisible(true);

    }
}
