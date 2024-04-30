package client.view;

import client.components.ClientController;
import commons.Message;

import javax.swing.*;
import java.awt.*;

public class ChatWindow extends JFrame {
    private final JTextArea messageArea;
    private final JTextField inputField;
    private final String username;
    private ClientController clientController;

    public ChatWindow() {
        this.username = askUsername();

        setTitle("Chat - " + username);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.addActionListener(e -> {
            sendMessage(inputField.getText());
            inputField.setText("");
        });
        add(inputField, BorderLayout.SOUTH);

        JButton sendButton = new JButton("Send Message");
        sendButton.addActionListener(e -> {
            sendMessage(inputField.getText());
            inputField.setText("");
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        add(buttonPanel, BorderLayout.NORTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu serverMenu = new JMenu("Server");
        JMenuItem connectMenuItem = new JMenuItem("Connect to Server");
        connectMenuItem.addActionListener(e -> {
            String[] addressAndPort = askAddressAndPort();
            if (addressAndPort != null) {
                connectToServer(addressAndPort[0], Integer.parseInt(addressAndPort[1]));
            }
        });
        serverMenu.add(connectMenuItem);
        menuBar.add(serverMenu);
        setJMenuBar(menuBar);

        setVisible(true);

        displayMessage("You are not connected to any server. Use the 'Connect to Server' option to connect.");
    }

    private String askUsername() {
        return JOptionPane.showInputDialog("Enter your username:");
    }

    private String[] askAddressAndPort() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField addressField = new JTextField("localhost");
        JTextField portField = new JTextField("6565");

        panel.add(new JLabel("Server Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Server Port:"));
        panel.add(portField);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Server Address and Port", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String address = addressField.getText();
            String port = portField.getText();
            return new String[]{address, port};
        } else {
            return null;
        }
    }

    private void connectToServer(String address, int port) {
        new Thread(() -> {
            try {
                clientController = new ClientController(address, port);
                clientController.startClient();
                displayMessage("Connected to server");
                inputField.setEditable(true);
            } catch (Exception e) {
                displayMessage("Failed to connect to server. Please check the address and port.");
            }
        }).start();
    }

    private void sendMessage(String content) {
        // TODO: VERY BAD WAY TO DO THIS!!!!!
        clientController.getClientConnectionHandler()
                .sendMessage(new Message(username, content));
        displayMessage(username + ": " + content);
    }

    public void displayMessage(String message) {
        messageArea.append(message + "\n");
    }

    public static void main(String[] args) {
        new ChatWindow();
    }
}