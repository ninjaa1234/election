import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WinnersForm extends JFrame {
    private JTable winnersTable;
    private JScrollPane scrollPane;
    private JButton refreshButton, backButton;
    private JComboBox<String> constituencyComboBox;

    public WinnersForm() {
        // Set form layout
        setLayout(new BorderLayout());

        // Initialize form components
        refreshButton = new JButton("Refresh");
        winnersTable = new JTable();
        scrollPane = new JScrollPane(winnersTable);
        constituencyComboBox = new JComboBox<String>();
        backButton = new JButton("Back");


        // Add form components to form
        add(scrollPane, BorderLayout.CENTER);
        add(constituencyComboBox, BorderLayout.NORTH);
        add(refreshButton, BorderLayout.EAST);
        add(backButton, BorderLayout.SOUTH);


        // Set form properties
        setTitle("Winners");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Populate constituency combo box
        populateConstituencyComboBox();

        // Add refresh button listener
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshWinners();
            }
        });

        // Call method to populate winners table
        refreshWinners();
    }

    private void populateConstituencyComboBox() {
        // Connect to PostgreSQL database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT DISTINCT constituency FROM candidates");

            // Populate combo box with data from result set
            while (rs.next()) {
                constituencyComboBox.addItem(rs.getString("constituency"));
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

    }

    private void refreshWinners() {
// Connect to PostgreSQL database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT candidate_id, name, party_name, vote_count, constituency FROM candidates INNER JOIN vote_count ON candidates.candidate_id = vote_count.candidate_id INNER JOIN party ON candidates.party_id = party.party_id WHERE constituency = '" + constituencyComboBox.getSelectedItem() + "' ORDER BY vote_count DESC LIMIT 1");
            // Create table model and set it as the model for the winners table
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Candidate ID");
            model.addColumn("Name");
            model.addColumn("Party Name");
            model.addColumn("Vote Count");
            model.addColumn("Constituency");
            winnersTable.setModel(model);

            // Populate table with data from result set
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("candidate_id"), rs.getString("name"), rs.getString("party_name"), rs.getInt("vote_count"), rs.getString("constituency")});
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
                VoteCountForm voteCountForm = new VoteCountForm();
                voteCountForm.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
    }



    public static void main(String[] args) {
// Show vote count form
        WinnersForm form = new WinnersForm();
        form.setVisible(true);
    }
}