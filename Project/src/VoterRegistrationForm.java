import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class VoterRegistrationForm extends JFrame {
    // Form components
    private JLabel nameLabel, ageLabel, addressLabel, voterIdLabel, passwordLabel, emailLabel, phoneLabel, votedLabel;
    private JTextField nameField, voterIdField, emailField, phoneField;
    private JTextArea addressField;
    private JSpinner ageField;
    private JCheckBox votedField;
    private JButton submitButton, backButton;
    private JPasswordField passwordField;

    public VoterRegistrationForm() {
        // Set form layout
        setLayout(new GridLayout(5, 2));

        // Initialize form components
        nameLabel = new JLabel("Name:");
        passwordLabel = new JLabel("Password:");
        ageLabel = new JLabel("Age:");
        addressLabel = new JLabel("Address:");
        voterIdLabel = new JLabel("Voter ID:");
        emailLabel = new JLabel("Email:");
        phoneLabel = new JLabel("Phone:");
        votedLabel = new JLabel("Voted:");
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        nameField = new JTextField();
        voterIdField = new JTextField();
        addressField = new JTextArea();
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
        add(passwordLabel);
        add(passwordField);
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
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                String phone = phoneField.getText();
                boolean voted = votedField.isSelected();


                // Connect to PostgreSQL database
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
                    PreparedStatement st = conn.prepareStatement("INSERT INTO voters (voter_id, name, password, age, constituency, email, phone, voted) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                    st.setString(1, voterId);
                    st.setString(2, name);
                    st.setString(3,password);
                    st.setInt(4, age);
                    st.setString(5, address);
                    st.setString(6, email);
                    st.setString(7, phone);
                    st.setBoolean(8, voted);
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
        passwordField.setText("");
        emailField.setText("");
        phoneField.setText("");
        votedField.setSelected(false);
    }

    public static void main(String[] args) {
        VoterRegistrationForm form = new VoterRegistrationForm();
        form.setVisible(true);
    }
}
