import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class ChangePassword extends JFrame {
    // Form components
    private JLabel currentPasswordLabel, newPasswordLabel, confirmPasswordLabel;
    private JPasswordField currentPasswordField, newPasswordField, confirmPasswordField;
    private JButton submitButton;
    private String voterId;

    public ChangePassword() {
        this.voterId = voterId;
        // Set form layout
        setLayout(new GridLayout(4, 2, 10, 10));

        // Initialize form components
        currentPasswordLabel = new JLabel("Current Password:");
        newPasswordLabel = new JLabel("New Password:");
        confirmPasswordLabel = new JLabel("Confirm Password:");
        currentPasswordField = new JPasswordField();
        newPasswordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        submitButton = new JButton("Submit");

        // Add form components to form
        add(currentPasswordLabel);
        add(currentPasswordField);
        add(newPasswordLabel);
        add(newPasswordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(new JLabel());
        add(submitButton);

        // Set form properties
        setTitle("Change Password");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Set background and foreground colors
        getContentPane().setBackground(new Color(197, 208, 235));
        currentPasswordLabel.setForeground(Color.BLACK);
        newPasswordLabel.setForeground(Color.BLACK);
        confirmPasswordLabel.setForeground(Color.BLACK);
        submitButton.setBackground(new Color(65, 93, 163));
        submitButton.setForeground(Color.WHITE);

        // Add submit button listener
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get entered data
                String currentPassword = new String(currentPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                // Verify entered data
                if (currentPassword.equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter current password", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (newPassword.equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter new password", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (confirmPassword.equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter confirm password", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "New password and confirm password do not match", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
// Update password in database
                    Connection conn = null;
                    try {
// Connect to the database
                        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
// Create a statement
                        Statement stmt = conn.createStatement();
// Update password
                        stmt.executeUpdate("UPDATE voters SET password = '" + newPassword + "' WHERE voter_id = '" + voterId + "'");
// Show success message
                        JOptionPane.showMessageDialog(null, "Password changed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
// Close the current window
                        setVisible(false);
                        dispose();
// Open the voter menu window
                        VoterMenu voterMenu = new VoterMenu();
                        voterMenu.setVisible(true);
                    } catch (SQLException ex) {
// Show error message
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        try {
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (SQLException ex) {
// Show error message
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }
    public static void main(String[] args) {
// Show voter menu
        ChangePassword changePassword = new ChangePassword();
        changePassword.setVisible(true);
    }
}

