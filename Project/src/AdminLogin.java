import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class AdminLogin extends JFrame {
    // Form components
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public AdminLogin() {
        // Set form layout
        setLayout(new GridLayout(3, 2));

        // Initialize form components
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        // Add form components to form
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(loginButton);

        // Set form properties
        setTitle("Admin Login");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add login button listener
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get entered data
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Connect to PostgreSQL database
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT * FROM admin WHERE username='" + username + "' AND password='" + password + "'");

                    if (rs.next()) {
                        // Show success message
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        //Close the current window
                        setVisible(false);
                        dispose();
                        // Open the main menu window
                        MainMenu mainMenu = new MainMenu();
                        mainMenu.setVisible(true);
                    } else {
                        // Show error message
                        JOptionPane.showMessageDialog(null, "Incorrect username or password!", "Error", JOptionPane.ERROR_MESSAGE);
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
        AdminLogin form = new AdminLogin();
        form.setVisible(true);
    }
}