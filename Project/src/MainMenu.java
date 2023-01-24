import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame {
    private JButton newVoterButton, newCandidateButton, newPartyButton, logoutButton;


    public MainMenu() {
        // Set form layout
        setLayout(new GridLayout(4, 1));

        // Initialize form components
        newVoterButton = new JButton("New Voter");
        newCandidateButton = new JButton("New Candidate");
        newPartyButton = new JButton("New Party");
        logoutButton = new JButton("Logout");

        // Add form components to form
        add(newVoterButton);
        add(newCandidateButton);
        add(newPartyButton);
        add(logoutButton);

        // Set form properties
        setTitle("Main Menu");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add new voter button listener
        newVoterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VoterRegistrationForm voterForm = new VoterRegistrationForm();
                voterForm.setVisible(true);
            }
        });


        // Add new candidate button listener
        newCandidateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CandidateEntryForm candidateForm = new CandidateEntryForm();
                candidateForm.setVisible(true);
            }
        });

        // Add new party button listener
        newPartyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PartyForm partyForm = new PartyForm();
                partyForm.setVisible(true);
            }
        });

        // Add logout button listener
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdminLogin adminLogin = new AdminLogin();
                adminLogin.setVisible(true);
                // Close the MainMenu window
                MainMenu.this.dispose();
            }
        });
    }




    public static void main(String[] args) {
        AdminLogin adminLogin = new AdminLogin();
        adminLogin.setVisible(true);
        adminLogin.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                MainMenu mainMenu = new MainMenu();
                mainMenu.setVisible(false);
            }
        });
    }
}