import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class VoterRegistrationForm extends JFrame {
    // Form components
    private JLabel nameLabel, ageLabel, addressLabel, voterIdLabel, partyLabel, partyIdLabel, emailLabel, phoneLabel, votedLabel;
    private JTextField nameField, voterIdField, partyField, emailField, phoneField;
    private JTextArea addressField;
    private JSpinner ageField, partyIdField;
    private JCheckBox votedField;
    private JButton submitButton, backButton;
    private JFrame displayFrame;
    private JPanel displayPanel;

    public VoterRegistrationForm() {
        // Set form layout
        setLayout(new GridLayout(5, 2));

        // Initialize form components
        nameLabel = new JLabel("Name:");
        ageLabel = new JLabel("Age:");
        addressLabel = new JLabel("Address:");
        voterIdLabel = new JLabel("Voter ID:");
        partyLabel = new JLabel("Party:");
        partyIdLabel = new JLabel("Party ID:");
        emailLabel = new JLabel("Email:");
        phoneLabel = new JLabel("Phone:");
        votedLabel = new JLabel("Voted:");

        nameField = new JTextField();
        voterIdField = new JTextField();
        addressField = new JTextArea();
        partyField = new JTextField();
        partyIdField = new JSpinner(new SpinnerNumberModel(0, 0, 3, 1));
        emailField = new JTextField();
        phoneField = new JTextField();
        ageField = new JSpinner(new SpinnerNumberModel(18, 18, 150, 1));
        votedField = new JCheckBox();
        submitButton = new JButton("Submit");
        backButton = new JButton("Back");


        // Add form components to form
        add(nameLabel);
        add(nameField);
        add(ageLabel);
        add(ageField);
        add(addressLabel);
        add(new JScrollPane(addressField));
        add(voterIdLabel);
        add(voterIdField);
        add(partyLabel);
        add(partyField);
        add(partyIdLabel);
        add(partyIdField);
        add(emailLabel);
        add(emailField);
        add(phoneLabel);
        add(phoneField);
        add(votedLabel);
        add(votedField);
        add(backButton);
        add(submitButton);


        // Set form properties
        setTitle("Voter Registration");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add submit button listener
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get entered data
                String name = nameField.getText();
                int age = (Integer) ageField.getValue();
                String address = addressField.getText();
                String voterId = voterIdField.getText();
                String party = partyField.getText();
                int partyId = (Integer) partyIdField.getValue();
                String email = emailField.getText();
                String phone = phoneField.getText();
                boolean voted = votedField.isSelected();


                // Connect to PostgreSQL database
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
                    PreparedStatement st = conn.prepareStatement("INSERT INTO voters (voter_id, name, age, registered_party, party_id, constituency, email, phone, voted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    st.setString(1, voterId);
                    st.setString(2, name);
                    st.setInt(3, age);
                    st.setString(4, party);
                    st.setInt(5, partyId);
                    st.setString(6, address);
                    st.setString(7, email);
                    st.setString(8, phone);
                    st.setBoolean(9, voted);
                    int result = st.executeUpdate();

                    if (result == 1) {
                        // Show success message
                        JOptionPane.showMessageDialog(null, "Voter registered successfully!");
                        clearForm();
                    } else {
                        // Show error message
                        JOptionPane.showMessageDialog(null, "Error registering voter!", "Error", JOptionPane.ERROR_MESSAGE);
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
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainMenu mainMenu = new MainMenu();
                mainMenu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
    }

    private void clearForm() {
        nameField.setText("");
        ageField.setValue(18);
        addressField.setText("");
        voterIdField.setText("");
        partyField.setText("");
        partyIdField.setValue(0);
        emailField.setText("");
        phoneField.setText("");
        votedField.setSelected(false);
    }

    public static void main(String[] args) {
        VoterRegistrationForm form = new VoterRegistrationForm();
        form.setVisible(true);
    }
}

                    /*if (result == 1) {
                        // Show success message
                        JOptionPane.showMessageDialog(null, "Voter registered successfully!");

                        // Create new display panel
                        displayPanel = new JPanel();
                        displayPanel.setLayout(new GridLayout(8, 2));

                        // Add data labels to display panel
                        displayPanel.add(new JLabel("Name:"));
                        displayPanel.add(new JLabel(name));
                        displayPanel.add(new JLabel("Age:"));
                        displayPanel.add(new JLabel(Integer.toString(age)));
                        displayPanel.add(new JLabel("Address:"));
                        displayPanel.add(new JLabel(address));
                        displayPanel.add(new JLabel("Voter ID:"));
                        displayPanel.add(new JLabel(voterId));
                        displayPanel.add(new JLabel("Party ID:"));
                        displayPanel.add(new JLabel(Integer.toString(partyId)));
                        displayPanel.add(new JLabel("Registered Party:"));
                        displayPanel.add(new JLabel(party));
                        displayPanel.add(new JLabel("Email:"));
                        displayPanel.add(new JLabel(email));
                        displayPanel.add(new JLabel("Phone:"));
                        displayPanel.add(new JLabel(phone));
                        displayPanel.add(new JLabel("Voted:"));
                        displayPanel.add(new JLabel(voted ? "Yes" : "No"));


                        // Initialize display frame
                        displayFrame = new JFrame();
                        displayFrame.add(displayPanel);
                        displayFrame.setTitle("Voter Information");
                        displayFrame.setSize(400, 300);
                        displayFrame.setLocationRelativeTo(null);
                        displayFrame.setVisible(true);
                    } else {
                        // Show error message
                        JOptionPane.showMessageDialog(null, "Error registering voter!");
                    }
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
        // Create form instance
        VoterRegistrationForm form = new VoterRegistrationForm();
        form.setVisible(true);
    }
}*/
