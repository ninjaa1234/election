import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class CandidateEntryForm extends JFrame {
    // Form components
    private JLabel nameLabel, partyLabel, constituencyLabel, emailLabel, phoneLabel, candidateIdLabel;
    private JTextField nameField, partyField,candidateIdField, constituencyField, emailField, phoneField;
    //private JSpinner candidateIdField;
    private JButton submitButton;
    private JFrame displayFrame;
    private JPanel displayPanel;
    public CandidateEntryForm() {
        // Set form layout
        setLayout(new GridLayout(6, 2));

        // Initialize form components
        nameLabel = new JLabel("Name:");
        partyLabel = new JLabel("Party:");
        constituencyLabel = new JLabel("Constituency:");
        emailLabel = new JLabel("Email:");
        phoneLabel = new JLabel("Phone:");
        candidateIdLabel = new JLabel("Candidate ID:");
        nameField = new JTextField();
        partyField = new JTextField();
        constituencyField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        candidateIdField = new JTextField();
        submitButton = new JButton("Submit");

        // Add form components to form
        add(nameLabel);
        add(nameField);
        add(partyLabel);
        add(partyField);
        add(constituencyLabel);
        add(constituencyField);
        add(emailLabel);
        add(emailField);
        add(phoneLabel);
        add(phoneField);
        add(candidateIdLabel);
        add(candidateIdField);
        add(new JLabel());
        add(submitButton);

        // Set form properties
        setTitle("Candidate Entry");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add submit button listener
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get entered data
                String name = nameField.getText();
                String party = partyField.getText();
                String constituency = constituencyField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String candidateId =  candidateIdField.getText();

                // Connect to PostgreSQL database
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
                    PreparedStatement st = conn.prepareStatement("INSERT INTO candidates (candidate_id, name, party, constituency, email, phone) VALUES (?, ?, ?, ?, ?, ?)");
                    st.setString(1, candidateId);
                    st.setString(2, name);
                    st.setString(3, party);
                    st.setString(4, constituency);
                    st.setString(5, email);
                    st.setString(6, phone);
                    int result = st.executeUpdate();

                    if (result == 1) {
                        // Show success message
                        JOptionPane.showMessageDialog(null, "Candidate added!");


                        // Initialize display frame
                        displayPanel = new JPanel();
                        displayPanel.setLayout(new GridLayout(6, 2));


                        // Add data labels to display frame
                        displayPanel.add(new JLabel("Name:"));
                        displayPanel.add(new JLabel(name));
                        displayPanel.add(new JLabel("Party:"));
                        displayPanel.add(new JLabel(party));
                        displayPanel.add(new JLabel("Constituency:"));
                        displayPanel.add(new JLabel(constituency));
                        displayPanel.add(new JLabel("Email:"));
                        displayPanel.add(new JLabel(email));
                        displayPanel.add(new JLabel("Phone:"));
                        displayPanel.add(new JLabel(phone));
                        displayPanel.add(new JLabel("Candidate ID:"));
                        displayPanel.add(new JLabel(candidateId));

                        // Initialize display frame
                        displayFrame = new JFrame();
                        displayFrame.add(displayPanel);

                        // Set display frame properties
                        displayFrame.setTitle("Entered Data");
                        displayFrame.setSize(300, 150);
                        displayFrame.setLocationRelativeTo(null);
                        displayFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

                        // Show display frame
                        displayFrame.setVisible(true);
                    } else {
                        // Show error message
                        JOptionPane.showMessageDialog(null, "Error adding candidate!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Close database resources
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

                // Set display frame properties


    public static void main(String[] args) {
        // Show form
        CandidateEntryForm form = new CandidateEntryForm();
        form.setVisible(true);
    }
}
