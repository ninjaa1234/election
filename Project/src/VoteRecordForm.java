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
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
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

        // Add form components to form
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
        setTitle("Vote Record");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Set background and foreground colors
        getContentPane().setBackground(new Color(197, 208, 235));
        voteIdField.setForeground(Color.BLACK);
        voterIdField.setForeground(Color.BLACK);
        voteIdLabel.setBackground(new Color(65, 93, 163));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(0,0,0));

        // Add submit button listener
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get entered data
                int voteId = Integer.parseInt(voteIdField.getText());
                String voterId =  voterIdField.getText();
                //int voterId = Integer.parseInt(voterIdField.getText());
                //int candidateId = Integer.parseInt(candidateIdField.getText());
                String candidateId =  candidateIdField.getText();
                java.util.Date voteDate = (java.util.Date) voteDateField.getValue();
                java.util.Date voteTime = (java.util.Date) voteTimeField.getValue();
                // Connect to PostgreSQL database
                Connection conn = null;
                try {
                    conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/election", "postgres", "appu12");
                    PreparedStatement st = conn.prepareStatement("INSERT INTO votes (vote_id,voter_id, candidate_id, vote_date, vote_time) VALUES (?,?, ?, ?, ?)");
                    st.setInt(1, voteId);
                    st.setString(2, voterId);
                    st.setString(3, candidateId);
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

