import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class VoteRecordForm extends JFrame {
    // Form components
    private JLabel voterIdLabel, candidateIdLabel, voteDateLabel, voteTimeLabel, voteIdLabel;
    private JTextField voterIdField, candidateIdField, voteIdField;
    private JSpinner voteDateField, voteTimeField;
    private JButton submitButton;

    public VoteRecordForm() {
        // Set form layout
        setLayout(new GridLayout(4, 2));

        // Initialize form components
        voteIdLabel = new JLabel("Vote ID");
        voterIdLabel = new JLabel("Voter ID:");
        candidateIdLabel = new JLabel("Candidate ID:");
        voteDateLabel = new JLabel("Vote Date:");
        voteTimeLabel = new JLabel("Vote Time:");
        voteIdField = new JTextField();
        voterIdField = new JTextField();
        candidateIdField = new JTextField();
        voteDateField = new JSpinner(new SpinnerDateModel());
        voteTimeField = new JSpinner(new SpinnerDateModel());
        submitButton = new JButton("Submit");

        // Add form components to form
        add(voteIdLabel);
        add(voteIdField);
        add(voterIdLabel);
        add(voterIdField);
        add(candidateIdLabel);
        add(candidateIdField);
        add(voteDateLabel);
        add(voteDateField);
        add(voteTimeLabel);
        add(voteTimeField);
        add(new JLabel());
        add(submitButton);

        // Set form properties
        setTitle("Vote Record");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add submit button listener
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get entered data
                int voteId = Integer.parseInt(voteIdField.getText());
                int voterId = Integer.parseInt(voterIdField.getText());
                int candidateId = Integer.parseInt(candidateIdField.getText());
                java.util.Date voteDate = (java.util.Date) voteDateField.getValue();
                java.util.Date voteTime = (java.util.Date) voteTimeField.getValue();
                // Connect to PostgreSQL database
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
                    PreparedStatement st = conn.prepareStatement("INSERT INTO votes (vote_id,voter_id, candidate_id, vote_date, vote_time) VALUES (?,?, ?, ?, ?)");
                    st.setInt(1, voteId);
                    st.setInt(2, voterId);
                    st.setInt(3, candidateId);
                    st.setDate(4, new java.sql.Date(voteDate.getTime()));
                    st.setTime(5, new java.sql.Time(voteTime.getTime()));
                    int result = st.executeUpdate();

                    if (result == 1) {
                        // Show success message
                        JOptionPane.showMessageDialog(null, "Vote recorded successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error recording vote!");
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
        VoteRecordForm form = new VoteRecordForm();
        form.setVisible(true);
    }
}

