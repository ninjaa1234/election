import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends JFrame {
    private JButton adminLoginButton, voterLoginButton;
    public HomePage() {
        // Set form layout
        setLayout(new FlowLayout());

        // Initialize form components
        adminLoginButton = new JButton("Admin Login");
        voterLoginButton = new JButton("Voter Login");

        // Add form components to form
        add(adminLoginButton);
        add(voterLoginButton);

        // Set form properties
        setTitle("Home Page");
        setSize(250, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add action listener to admin login button
        adminLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                setVisible(false);
                dispose();

                // Open the admin login window
                AdminLogin adminLogin = new AdminLogin();
                adminLogin.setVisible(true);
            }
        });

        // Add action listener to voter login button
        voterLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                setVisible(false);
                dispose();

                // Open the voter login window
                VoterLogin voterLogin = new VoterLogin();
                voterLogin.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        // Show home page
        HomePage homePage = new HomePage();
        homePage.setVisible(true);
    }
}
