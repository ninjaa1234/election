import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class AdminLogin extends JFrame {
    // Form components
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, HomeButton;

    public AdminLogin() {
        // Set form layout
        setLayout(new GridLayout(3, 2, 10, 10)); // added horizontal and vertical gaps of 10 pixels between components

        // Initialize form components
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        HomeButton = new JButton("Home");

        // Add form components to form
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(loginButton);
        add(HomeButton);

        // Set form properties
        setTitle("Admin Login");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false); // added to set proper resolution during maximize screen

        // Set background and foreground colors
        getContentPane().setBackground(new Color(197, 208, 235));
        usernameLabel.setForeground(Color.BLACK);
        passwordLabel.setForeground(Color.BLACK);
        loginButton.setBackground(new Color(65, 93, 163));
        loginButton.setForeground(Color.WHITE);

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
        HomeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HomePage homePage = new HomePage();
                homePage.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        // Show login form
        AdminLogin form = new AdminLogin();
        form.setVisible(true);
    }
}