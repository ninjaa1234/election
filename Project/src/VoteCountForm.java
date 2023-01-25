import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;;

public class VoteCountForm extends JFrame {
    private JTable voteCountTable;
    private JScrollPane scrollPane;
    private JButton refreshButton, backButton, newWinnerButton;
    private JComboBox<String> constituencyComboBox;
    public VoteCountForm() {
        // Set form layout
        setLayout(new BorderLayout());

        // Initialize form components
        refreshButton = new JButton("Refresh");
        voteCountTable = new JTable();
        scrollPane = new JScrollPane(voteCountTable);
        constituencyComboBox = new JComboBox<String>();
        backButton = new JButton("Back");
        newWinnerButton = new JButton("Result");

        // Add form components to form
        add(scrollPane, BorderLayout.CENTER);
        add(constituencyComboBox, BorderLayout.NORTH);
        add(refreshButton, BorderLayout.EAST);
        add(backButton,BorderLayout.SOUTH);
        add(newWinnerButton,BorderLayout.LINE_START);

        // Set form properties
        setTitle("Vote Count");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Populate constituency combo box
        populateConstituencyComboBox();

        // Add refresh button listener
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshVoteCount();
            }
        });

        // Add Result List listener
        newWinnerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WinnersForm winnersForm = new WinnersForm();
                winnersForm.setVisible(true);
                dispose();
            }
        });

        // Call method to populate vote count table
        refreshVoteCount();
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
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CandidateListForm candidateListForm = new CandidateListForm();
                candidateListForm.setVisible(true);
                setVisible(false);
                dispose();
            }
        });
    }

    private void refreshVoteCount() {
        // Connect to PostgreSQL database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT candidate_id, name, vote_count, constituency FROM candidates INNER JOIN vote_count ON candidates.candidate_id = vote_count.candidate_id WHERE constituency = '" + constituencyComboBox.getSelectedItem() + "' ORDER BY vote_count DESC");

            // Create table model and set it as the model for the vote count table
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Candidate ID");
            model.addColumn("Name");
            model.addColumn("Vote Count");
            model.addColumn("Constituency");
            voteCountTable.setModel(model);
            // Populate table with data from result set
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("candidate_id"), rs.getString("name"), rs.getInt("vote_count"), rs.getString("constituency")});
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

    public static void main(String[] args) {
// Show vote count form
        VoteCountForm form = new VoteCountForm();
        form.setVisible(true);
    }
}