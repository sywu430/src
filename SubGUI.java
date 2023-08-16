import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SubGUI extends JFrame {
    private JPanel contentPane;
    private JButton btnSearchTicket;
    private JButton btnCheckSchedule;

    public SubGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        btnSearchTicket = new JButton("Search Ticket");
        btnSearchTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSearchTicketClick();
            }
        });
        btnSearchTicket.setBounds(123, 47, 200, 50);
        contentPane.add(btnSearchTicket);

        btnCheckSchedule = new JButton("Check Schedule");
        btnCheckSchedule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCheckScheduleClick();
            }
        });
        btnCheckSchedule.setBounds(123, 107, 200, 50);
        contentPane.add(btnCheckSchedule);

        JButton btnCancel = new JButton("Close");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancel.setBounds(123, 167, 200, 50);
        contentPane.add(btnCancel);
    }

    public void btnSearchTicketClick() {
        SearchTicketGUI searchTicketGUI = new SearchTicketGUI();
        searchTicketGUI.show();
    }

    public void btnCheckScheduleClick() {
        CheckScheduleGUI checkScheduleGUI = new CheckScheduleGUI();
        checkScheduleGUI.show();
    }
}
