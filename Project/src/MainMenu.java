import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame {
    private JButton newVoterButton, newCandidateButton;

    public MainMenu() {
        // Set form layout
        setLayout(new GridLayout(2, 1));

        // Initialize form components
        newVoterButton = new JButton("New Voter");
        newCandidateButton = new JButton("New Candidate");

        // Add form components to form
        add(newVoterButton);
        add(newCandidateButton);

        // Set form properties
        setTitle("Main Menu");
        setSize(300, 150);
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
    }

    public static void main(String[] args) {
        AdminLogin adminLogin = new AdminLogin();
        adminLogin.setVisible(true);
        adminLogin.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                MainMenu mainMenu = new MainMenu();
                mainMenu.setVisible(true);
            }
        });
    }
}