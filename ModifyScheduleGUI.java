import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.HashSet;
import java.util.ArrayList;

public class ModifyScheduleGUI extends JFrame {
    private JPanel contentPane;

    private JLabel labelModify;
    private JLabel labelDeparture;
    private JLabel labelArrival;
    private JLabel labelDate;

    private JComboBox<String> cbDeparture;
    private JComboBox<String> cbArrival;
    private JTextField txtDate;

    private JButton btnSearch;
    private JButton btnModify;
    private JButton btnClose;

    private JTable table;
    private JScrollPane scrolledTable;

    private int targetTicketId;

    public ModifyScheduleGUI(int id, String departure, String arrival, String date) {
        targetTicketId = id;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 480, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        labelModify = new JLabel("Modify Schedule to different time");
        labelModify.setBounds(30, 15, 300, 20);
        contentPane.add(labelModify);

        labelDeparture = new JLabel("Departure");
        labelDeparture.setBounds(30, 45, 100, 20);
        contentPane.add(labelDeparture);

        labelArrival = new JLabel("Arrival");
        labelArrival.setBounds(30, 75, 100, 20);
        contentPane.add(labelArrival);

        labelDate = new JLabel("Date\n(MM-DD-YYYY)");
        labelDate.setBounds(30, 105, 150, 20);
        contentPane.add(labelDate);

        cbDeparture = new JComboBox<String>();
        cbDeparture.setBounds(170, 45, 200, 20);
        cbArrival = new JComboBox<String>();
        cbArrival.setBounds(170, 75, 200, 20);

        cbDeparture.addItem(departure);
        cbArrival.addItem(arrival);

        contentPane.add(cbDeparture);
        contentPane.add(cbArrival);

        txtDate = new JTextField(date);
        txtDate.setBounds(170, 105, 200, 20);
        contentPane.add(txtDate);

        btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSearchClick(cbDeparture.getSelectedItem().toString(),
                        cbArrival.getSelectedItem().toString(),
                        txtDate.getText());
            }
        });
        btnSearch.setBounds(380, 40, 80, 90);
        contentPane.add(btnSearch);

        btnModify = new JButton("Modify to");
        btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnModifyClick(table.getSelectedRows()[0]);
            }
        });
        btnModify.setBounds(370, 450, 100, 50);
        contentPane.add(btnModify);

        btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCloseClick();
            }
        });
        btnClose.setBounds(30, 450, 100, 50);
        contentPane.add(btnClose);

        String header[] = { "id", "time", "date", "departure", "arrival" };
        DefaultTableModel model = new DefaultTableModel(header, 0); // header추가, 행은 0개 지정
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrolledTable = new JScrollPane(table);
        scrolledTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrolledTable.setBounds(30, 140, 430, 300);
        contentPane.add(scrolledTable);

        btnSearchClick(departure, arrival, date);
    }

    public void btnSearchClick(String departure, String arrival, String date) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        ArrayList<Ticket> tickets = BookController.searchTicket(departure, arrival, date);

        for (Ticket ticket : tickets) {
            model.addRow(ticket.toStringArray());
        }
    }

    public void btnModifyClick(int selectedRowIdx) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int id = Integer.parseInt(model.getValueAt(selectedRowIdx, 0).toString());
            BookController.bookTicket(id);
            BookController.cancelBook(targetTicketId);
            JOptionPane.showMessageDialog(null, "Ticket modified successfully!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Please select a ticket!");
        }
    }

    public void btnCloseClick() {
        dispose();
    }
}
