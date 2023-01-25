import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class PartyForm extends JFrame {
    private JLabel partyIdLabel, partyNameLabel, partyPresidentLabel, partyHeadquarterLabel, partySignLabel;
    private JTextField partyIdField, partyNameField, partyPresidentField, partyHeadquarterField, partySignField;
    private JButton submitButton, backButton;
    private Connection conn;
    private Statement stmt;


    public PartyForm() {
        super("Party Form");
        setLayout(new FlowLayout());

        partyIdLabel = new JLabel("Party ID:");
        add(partyIdLabel);

        partyIdField = new JTextField(10);
        add(partyIdField);

        partyNameLabel = new JLabel("Party Name:");
        add(partyNameLabel);

        partyNameField = new JTextField(10);
        add(partyNameField);

        partyPresidentLabel = new JLabel("Party President:");
        add(partyPresidentLabel);

        partyPresidentField = new JTextField(10);
        add(partyPresidentField);

        partyHeadquarterLabel = new JLabel("Party Headquarter:");
        add(partyHeadquarterLabel);

        partyHeadquarterField = new JTextField(10);
        add(partyHeadquarterField);

        partySignLabel = new JLabel("Party Sign:");
        add(partySignLabel);

        partySignField = new JTextField(10);
        add(partySignField);

        submitButton = new JButton("Submit");
        add(submitButton);

        JButton clearFormButton = new JButton("Clear Form");
        add(clearFormButton);
        clearFormButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                partyIdField.setText("");
                partyNameField.setText("");
                partyPresidentField.setText("");
                partyHeadquarterField.setText("");
                partySignField.setText("");
            }
        });

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
                    stmt = conn.createStatement();
                    stmt.executeUpdate("INSERT INTO party (party_id, party_name, party_president, party_headquarter, party_sign) " +
                            "VALUES ('" + partyIdField.getText() + "', '" + partyNameField.getText() + "', '" + partyPresidentField.getText() + "', '" + partyHeadquarterField.getText() + "', '" + partySignField.getText() + "')");
                    JOptionPane.showMessageDialog(null, "Data added successfully!");
                    conn.close();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        });
        backButton = new JButton("Back");
        add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainMenu mainMenu = new MainMenu();
                mainMenu.setVisible(true);
                dispose();
            }
        });

        setSize(350, 250);
        setVisible(true);
    }


    public static void main(String[] args) {
        new PartyForm();
    }
}
