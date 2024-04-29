package client.view;

import client.components.ClientConnectionHandler;
import commons.Message;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

public class LoginDialog extends JDialog {
    private JTextField usernameField;
    private JTextField portField;

    public LoginDialog() {
        setTitle("Login");
        setSize(300, 150);
        setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel portLabel = new JLabel("Server Port:");
        portField = new JTextField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            dispose();
        });

        add(usernameLabel);
        add(usernameField);
        add(portLabel);
        add(portField);
        add(new JLabel());
        add(loginButton);
    }

    public int showDialog() {
        setLocationRelativeTo(null);
        setModal(true);
        setVisible(true);
        return getDefaultCloseOperation();
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public int getPort() {
        try {
            return Integer.parseInt(portField.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
