import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;

public class LoginGUI extends JFrame {
    private JPanel contentPane;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JLabel labelLogin;
    private JLabel labelUsername;
    private JLabel labelPassword;

    private JButton btnLogin;
    private JButton btnReset;
    private JButton btnCancel;

    public LoginGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 250);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtUsername = new JTextField();
        txtUsername.setBounds(120, 28, 200, 26); // Adjust x, y, and width
        contentPane.add(txtUsername);
        txtUsername.setColumns(10);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 80, 200, 26); // Adjust x, y, and width
        contentPane.add(txtPassword);
        txtPassword.setColumns(10);

        labelLogin = new JLabel("<Login>");
        labelLogin.setBounds(30, 5, 100, 30); // Adjust x and width
        contentPane.add(labelLogin);

        labelUsername = new JLabel("Username");
        labelUsername.setBounds(30, 24, 100, 30); // Adjust x and width
        contentPane.add(labelUsername);

        labelPassword = new JLabel("Password");
        labelPassword.setBounds(30, 76, 100, 30); // Adjust x and width
        contentPane.add(labelPassword);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Save the new contact to the data file
                String username = txtUsername.getText();
                String password = txtPassword.getText();

                btnLoginClick(username, password);

                txtUsername.setText("");
                txtPassword.setText("");
            }
        });
        btnLogin.setBounds(24, 140, 100, 50);
        contentPane.add(btnLogin);

        btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnResetClick();
            }
        });
        btnReset.setBounds(145, 140, 100, 50);
        contentPane.add(btnReset);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCancelClick();
            }
        });
        btnCancel.setBounds(265, 140, 100, 50);
        contentPane.add(btnCancel);
    }

    public void btnLoginClick(String username, String password) {
        // Write code to save the contact to the data file here
        // For example, you can use a data storage mechanism like a database or a file.
        // Reset the text fields after saving
        // Write the contact information to the data file
        // Create the file if it doesn't exist
        boolean accountVerified = verifyAccount(username, password);

        if (accountVerified) {
            BookController.setUsername(username);
            JOptionPane.showMessageDialog(null, "Account verified.");
            this.dispose();
            SubGUI subGUI = new SubGUI();
            subGUI.show();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Account not verified. Please check your username or password.");
        }
    }

    public void btnResetClick() {
        txtUsername.setText("");
        txtPassword.setText("");
    }

    public void btnCancelClick() {
        dispose();
    }

    public boolean verifyAccount(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String usernameFromFile = parts[0];
                    String passwordFromFile = parts[1];

                    if (usernameFromFile.equals(username) && passwordFromFile.equals(password)) {
                        return true; // Account verified
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Account not verified
    }
}
