import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VoterMenu extends JFrame {
    private JButton voteButton, viewResultsButton, changePasswordButton, logoutButton;
    public VoterMenu() {
        // Set form layout
        setLayout(new GridLayout(4, 1, 10, 10));

        // Initialize form components
        voteButton = new JButton("Vote");
        viewResultsButton = new JButton("View Results");
        changePasswordButton = new JButton("Change Password");
        logoutButton = new JButton("Logout");

        // Add form components to form
        add(voteButton);
        add(viewResultsButton);
        add(changePasswordButton);
        add(logoutButton);

        // Set form properties
        setTitle("Voter Menu");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Set background and foreground colors
        getContentPane().setBackground(new Color(197, 208, 235));
        voteButton.setBackground(new Color(65, 93, 163));
        voteButton.setForeground(Color.WHITE);
        viewResultsButton.setBackground(new Color(65, 93, 163));
        viewResultsButton.setForeground(Color.WHITE);
        changePasswordButton.setBackground(new Color(65, 93, 163));
        changePasswordButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(65, 93, 163));
        logoutButton.setForeground(Color.WHITE);

        // Add vote button listener
        voteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                setVisible(false);
                dispose();
                // Open the vote form window
                VoteForm voteForm = new VoteForm();
                voteForm.setVisible(true);
                dispose();
            }
        });

        // Add view results button listener
        viewResultsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                setVisible(false);
                dispose();
                // Open the view results window
                WinnersForm winnersForm = new WinnersForm();
                winnersForm.setVisible(true);
            }
        });

        // Add change password button listener
        changePasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
// Close the current window
                setVisible(false);
                dispose();
// Open the change password window

                ChangePassword changePassword = new ChangePassword();
                changePassword.setVisible(true);


            }
        });
        // Add logout button listener
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show confirmation message
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Confirm", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Close the current window
                    setVisible(false);
                    dispose();
                    // Open the home page window
                    HomePage homePage = new HomePage();
                    homePage.setVisible(true);
                }
            }
        });
    }

    public static void main(String[] args) {
// Show voter menu
        VoterMenu voterMenu = new VoterMenu();
        voterMenu.setVisible(true);
    }
}




