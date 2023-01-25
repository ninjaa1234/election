import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame {
    private JButton newVoterButton, newCandidateButton, newPartyButton,newVoterListButton, newCandidateListButton,logoutButton;


    public MainMenu() {
        // Set form layout
        setLayout(new GridLayout(5, 1));

        // Initialize form components
        newVoterButton = new JButton("New Voter");
        newCandidateButton = new JButton("New Candidate");
        newPartyButton = new JButton("New Party");
        newVoterListButton = new JButton("Voter List");
        newCandidateListButton = new JButton("Candidate List");
        logoutButton = new JButton("Logout");

        // Add form components to form
        add(newVoterButton);
        add(newCandidateButton);
        add(newPartyButton);
        add(newVoterListButton);
        add(newCandidateListButton);
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
                dispose();
            }
        });


        // Add new candidate button listener
        newCandidateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CandidateEntryForm candidateForm = new CandidateEntryForm();
                candidateForm.setVisible(true);
                dispose();
            }
        });

        // Add new party button listener
        newPartyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PartyForm partyForm = new PartyForm();
                partyForm.setVisible(true);
                dispose();
            }
        });

        // Add new Voter List listener
        newVoterListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VoterListForm voterListForm = new VoterListForm();
                voterListForm.setVisible(true);
                dispose();
            }
        });

        // Add new Candidate List listener
        newCandidateListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CandidateListForm candidateListForm = new CandidateListForm();
                candidateListForm.setVisible(true);
                dispose();
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