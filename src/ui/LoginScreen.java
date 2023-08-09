package ui;

import customexceptions.EmptyInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame implements ActionListener {
    private JLabel userLabel, passLabel;
    private JTextField userText;
    private JPasswordField passText;
    private JButton login, cancel;

    public LoginScreen() {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(600, 200);

        initialization();

        JPanel contentPane = (JPanel) this.getContentPane();
        JPanel userPanel = new JPanel();
        JPanel passPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        userPanel.add(userLabel);
        userPanel.add(userText);

        passPanel.add(passLabel);
        passPanel.add(passText);

        buttonPanel.add(login);
        buttonPanel.add(cancel);

        contentPane.add(userPanel, BorderLayout.NORTH);
        contentPane.add(passPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        login.addActionListener(this);
        cancel.addActionListener(this);
        this.pack();
    }

    private void initialization() {
        userLabel = new JLabel("Username");
        passLabel = new JLabel("Password");
        userText = new JTextField(10);
        passText = new JPasswordField(10);
        login = new JButton("Login");
        cancel = new JButton("Cancel");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button == cancel) System.exit(0);
        try {
            String userName = userText.getText().trim();
            String password = new String(passText.getPassword());
            if (userName.isEmpty() || password.isEmpty())
                throw new EmptyInputException();
            if (userName.equals("admin") && password.equals("admin")) {
                new MenuScreen();
                this.setVisible(false);
            } else
                JOptionPane.showMessageDialog(
                        null,
                        "Username or password is wrong, try again",
                        "Login failed",
                        JOptionPane.ERROR_MESSAGE
                );
        } catch (EmptyInputException e1) {
            JOptionPane.showMessageDialog(
                    null,
                    "Username or password cannot be empty",
                    "Empty field",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}
