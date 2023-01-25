
import java.awt.*;
        import java.awt.event.*;
        import java.sql.*;
        import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CandidateListForm extends JFrame {
    private JTable candidateTable;
    private JScrollPane scrollPane;
    private JButton refreshButton, backButton, newVoteCountButton;

    public CandidateListForm() {
        // Set form layout
        setLayout(new BorderLayout());

        // Initialize form components
        refreshButton = new JButton("Refresh");
        candidateTable = new JTable();
        scrollPane = new JScrollPane(candidateTable);
        backButton = new JButton("Back");
        newVoteCountButton = new JButton("Vote Count");

        // Add form components to form
        add(scrollPane, BorderLayout.CENTER);
        add(refreshButton, BorderLayout.LINE_END);
        add(backButton, BorderLayout.SOUTH);
        add(newVoteCountButton, BorderLayout.WEST);

        // Set form properties
        setTitle("Candidate List");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add refresh button listener
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshCandidateList();
            }
        });

        // Add nvote count button listener
        newVoteCountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VoteCountForm voteCountForm = new VoteCountForm();
                voteCountForm.setVisible(true);
                dispose();
            }
        });

        // Call method to populate candidate table
        refreshCandidateList();
    }

    private void refreshCandidateList() {
        // Connect to PostgreSQL database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM candidates");

            // Create table model and set it as the model for the candidate table
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Candidate ID");
            model.addColumn("Name");
            model.addColumn("Party ID");
            model.addColumn("Constituency");
            model.addColumn("Email");
            model.addColumn("Phone");
            candidateTable.setModel(model);

            // Populate table with data from result set
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("candidate_id"), rs.getString("name"), rs.getInt("party_id"), rs.getString("constituency"), rs.getString("email"), rs.getString("phone")});
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
        // Show candidate list form
        CandidateListForm form = new CandidateListForm();
        form.setVisible(true);
    }
}
