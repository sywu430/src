import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class RegisterGUI extends JFrame {
    private JPanel contentPane;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JLabel labelUsername;
    private JLabel labelPassword;

    private JButton btnRegister;
    private JButton btnReset;
    private JButton btnCancel;

    public RegisterGUI() {
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

        labelUsername = new JLabel("Username");
        labelUsername.setBounds(30, 24, 100, 30); // Adjust x and width
        contentPane.add(labelUsername);

        labelPassword = new JLabel("Password");
        labelPassword.setBounds(30, 76, 100, 30); // Adjust x and width
        contentPane.add(labelPassword);

        btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Save the new contact to the data file
                String username = txtUsername.getText();
                String password = txtPassword.getText();

                if (username.equals("") || password.equals("")) {
                    System.out.println("Please enter a username and password");
                    return;
                }

                btnRegisterClick(username, password);

                txtUsername.setText("");
                txtPassword.setText("");

                JOptionPane.showMessageDialog(null, "Suceessfully Registered!");
                dispose();
            }
        });
        btnRegister.setBounds(24, 140, 100, 50);
        contentPane.add(btnRegister);

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

    public void btnRegisterClick(String username, String password) {
        // Write code to save the contact to the data file here
        // For example, you can use a data storage mechanism like a database or a file.
        // Reset the text fields after saving
        // Write the contact information to the data file
        // Create the file if it doesn't exist

        try {
            // Create the file if it doesn't exist
            File file = new File("users.txt");
            File book_file = new File("books.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            if (!book_file.exists()) {
                book_file.createNewFile();
            }
            // Create a FileWriter object to write to the file
            FileWriter fileWriter = new FileWriter(file, true);

            // Create a BufferedWriter object to buffer the output
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the content to the file
            bufferedWriter.write(username + "," + password);
            bufferedWriter.newLine();
            // Write the contact information to the file
            bufferedWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

    public void btnResetClick() {
        // Reset the text fields
        txtUsername.setText("");
        txtPassword.setText("");
    }

    public void btnCancelClick() {
        // Close the window
        dispose();
    }
}
