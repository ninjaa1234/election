import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class VoterLogin extends JFrame {
    // Form components
    private JLabel voterIdLabel, voterNameLabel, passwordLabel;
    private JTextField voterIdField, voterNameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public VoterLogin() {
        // Set form layout
        setLayout(new GridLayout(4, 2, 10, 10));

        // Initialize form components
        voterIdLabel = new JLabel("Voter ID:");
        voterNameLabel = new JLabel("Voter Name:");
        passwordLabel = new JLabel("Password:");
        voterIdField = new JTextField();
        voterNameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        // Add form components to form
        add(voterIdLabel);
        add(voterIdField);
        add(voterNameLabel);
        add(voterNameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(loginButton);

        // Set form properties
        setTitle("Voter Login");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Set background and foreground colors
        getContentPane().setBackground(new Color(197, 208, 235));
        voterIdLabel.setForeground(Color.BLACK);
        voterNameLabel.setForeground(Color.BLACK);
        passwordLabel.setForeground(Color.BLACK);
        loginButton.setBackground(new Color(65, 93, 163));
        loginButton.setForeground(Color.WHITE);

        // Add login button listener
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get entered data
                String voterId = voterIdField.getText();
                String voterName = voterNameField.getText();
                String password = new String(passwordField.getPassword());
                // Connect to PostgreSQL database
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM voters WHERE voter_id='" + voterId + "' AND name='" + voterName + "' AND password='" + password + "'");

                    if (rs.next()) {
                        // Show success message
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        //Close the current window
                        setVisible(false);
                        dispose();
                        // Open the main menu window
                        VoterMenu voterMenu = new VoterMenu();
                        voterMenu.setVisible(true);
                    } else {
                        // Show error message
                        JOptionPane.showMessageDialog(null, "Incorrect voter ID, voter name or password!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Close database resources
                    rs.close();
                    st.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        // Show login form
        VoterLogin form = new VoterLogin();
        form.setVisible(true);
    }
}



