import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class VoteForm extends JFrame {
    // Form components
    private JLabel voterIdLabel, candidateIdLabel, voteDateLabel, voteTimeLabel, voteIdLabel;
    private JTextField voterIdField, candidateIdField, voteIdField;
    private JSpinner voteDateField, voteTimeField;
    private JButton submitButton, backButton;
    public VoteForm() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

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
        backButton = new JButton("Back");

        // Add form components to form
        add(voteIdLabel);
        add(voteIdField);
        add(voterIdLabel);
        add(voterIdField);
        add(candidateIdLabel);
        add(candidateIdField);
        add(backButton);
        c.gridx = 0;
        c.gridy = 5;
        add(backButton, c);
        add(submitButton);
        c.gridx = 0;
        c.gridy = 0;
        add(voteIdLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        add(voteIdField, c);
        c.gridx = 0;
        c.gridy = 1;
        add(voterIdLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        add(voterIdField, c);
        c.gridx = 0;
        c.gridy = 2;
        add(candidateIdLabel, c);
        c.gridx = 1;
        c.gridy = 2;
        add(candidateIdField, c);
        c.gridx = 0;
        c.gridy = 3;
        add(voteDateLabel, c);
        c.gridx = 1;
        c.gridy = 3;
        add(voteDateField, c);
        c.gridx = 0;
        c.gridy = 4;
        add(voteTimeLabel, c);
        c.gridx = 1;
        c.gridy = 4;
        add(voteTimeField, c);
        c.gridx = 1;
        c.gridy = 5;
        add(submitButton, c);

        // Set form properties
        setTitle("Vote Form");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Add submit button listener
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int voteId = Integer.parseInt(voteIdField.getText());
                // Get entered data
                String voterId = voterIdField.getText();
                String candidateId = candidateIdField.getText();
                java.util.Date voteDate = (java.util.Date) voteDateField.getValue();
                java.util.Date voteTime = (java.util.Date) voteTimeField.getValue();

                // Connect to PostgreSQL database
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
                    PreparedStatement st = conn.prepareStatement("SELECT voted FROM voters WHERE voter_id = ?");
                    st.setString(1, voterId);
                    ResultSet rs = st.executeQuery();
                    boolean voted = false;
                    if (rs.next()) {
                        voted = rs.getBoolean("voted");
                    }
                    if (!voted) {
// Insert vote into database
                        st = conn.prepareStatement("INSERT INTO votes (vote_id, voter_id, candidate_id, vote_date, vote_time) VALUES (?,?,?,?,?)");
                        st.setInt(1, voteId);
                        st.setString(2, voterId);
                        st.setString(3, candidateId);
                        st.setDate(4, new java.sql.Date(voteDate.getTime()));
                        st.setTime(5, new java.sql.Time(voteTime.getTime()));
                        st.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Vote cast successfully!");

                        // Update voter's voted status
                        st = conn.prepareStatement("UPDATE voters SET voted = true WHERE voter_id = ?");
                        st.setString(1, voterId);
                        st.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Vote successfully submitted!");
                        // Clear form fields
                        voteIdField.setText("");
                        voterIdField.setText("");
                        candidateIdField.setText("");
                        voteDateField.setValue(new java.util.Date());
                        voteTimeField.setValue(new java.util.Date());
                    } else {
                        JOptionPane.showMessageDialog(null, "This voter has already voted!");
// Clear form fields
                        voteIdField.setText("");
                        voterIdField.setText("");
                        candidateIdField.setText("");
                        voteDateField.setValue(new java.util.Date());
                        voteTimeField.setValue(new java.util.Date());
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

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VoterMenu voterMenu = new VoterMenu();
                voterMenu.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new VoteForm().setVisible(true);
    }
}

