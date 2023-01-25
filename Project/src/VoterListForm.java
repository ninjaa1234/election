import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VoterListForm extends JFrame {
    private JTable voterTable;
    private JScrollPane scrollPane;
    private JButton refreshButton, backButton;

    public VoterListForm() {
        // Set form layout
        setLayout(new BorderLayout());

        // Initialize form components
        refreshButton = new JButton("Refresh");
        voterTable = new JTable();
        scrollPane = new JScrollPane(voterTable);
        backButton = new JButton("Back");

        // Add form components to form
        add(scrollPane, BorderLayout.CENTER);
        add(refreshButton, BorderLayout.LINE_END);
        add(backButton, BorderLayout.SOUTH);

        // Set form properties
        setTitle("Voter List");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add refresh button listener
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshVoterList();
            }
        });

        // Call method to populate voter table
        refreshVoterList();
    }

    private void refreshVoterList() {
        // Connect to PostgreSQL database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM voters");

            // Create table model and set it as the model for the voter table
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Voter ID");
            model.addColumn("Name");
            model.addColumn("Age");
            model.addColumn("Constituency");
            model.addColumn("Email");
            model.addColumn("Phone");
            model.addColumn("Voted");
            voterTable.setModel(model);

            // Populate table with data from result set
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("voter_id"), rs.getString("name"), rs.getInt("age"), rs.getString("constituency"), rs.getString("email"), rs.getString("phone"), rs.getBoolean("voted")});
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
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainMenu mainMenu = new MainMenu();
                mainMenu.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        // Show voter list form
        VoterListForm form = new VoterListForm();
        form.setVisible(true);
    }
}