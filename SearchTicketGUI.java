import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.HashSet;
import java.util.ArrayList;

public class SearchTicketGUI extends JFrame {
    private JPanel contentPane;

    private JLabel labelDeparture;
    private JLabel labelArrival;
    private JLabel labelDate;

    private JComboBox<String> cbDeparture;
    private JComboBox<String> cbArrival;
    private JTextField txtDate;

    private JButton btnSearch;
    private JButton btnBook;
    private JButton btnClose;

    private JTable table;
    private JScrollPane scrolledTable;

    public SearchTicketGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 480, 520);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        labelDeparture = new JLabel("Departure");
        labelDeparture.setBounds(30, 15, 100, 20);
        contentPane.add(labelDeparture);

        labelArrival = new JLabel("Arrival");
        labelArrival.setBounds(30, 45, 100, 20);
        contentPane.add(labelArrival);

        labelDate = new JLabel("Date\n(MM-DD-YYYY)");
        labelDate.setBounds(30, 75, 150, 20);
        contentPane.add(labelDate);

        cbDeparture = new JComboBox<String>();
        cbDeparture.setBounds(170, 15, 200, 20);
        cbArrival = new JComboBox<String>();
        cbArrival.setBounds(170, 45, 200, 20);

        // load destinations
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader("tickets.txt"));
            HashSet<String> srcs = new HashSet<String>();
            HashSet<String> dests = new HashSet<String>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String src = tokens[3];
                String dest = tokens[4];
                srcs.add(src);
                dests.add(dest);
            }
            br.close();

            for (String src : srcs) {
                cbDeparture.addItem(src);
            }
            for (String dest : dests) {
                cbArrival.addItem(dest);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        contentPane.add(cbDeparture);
        contentPane.add(cbArrival);

        txtDate = new JTextField();
        txtDate.setBounds(170, 75, 200, 20);
        contentPane.add(txtDate);

        btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSearchClick(cbDeparture.getSelectedItem().toString(),
                        cbArrival.getSelectedItem().toString(),
                        txtDate.getText());
            }
        });
        btnSearch.setBounds(380, 10, 80, 90);
        contentPane.add(btnSearch);

        btnBook = new JButton("Book");
        btnBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBookClick(table.getSelectedRows()[0]);
            }
        });
        btnBook.setBounds(370, 420, 100, 50);
        contentPane.add(btnBook);

        btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCloseClick();
            }
        });
        btnClose.setBounds(30, 420, 100, 50);
        contentPane.add(btnClose);

        String header[] = { "id", "time", "date", "departure", "arrival" };
        DefaultTableModel model = new DefaultTableModel(header, 0); // header추가, 행은 0개 지정
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrolledTable = new JScrollPane(table);
        scrolledTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrolledTable.setBounds(30, 110, 430, 300);
        contentPane.add(scrolledTable);
    }

    public void btnSearchClick(String departure, String arrival, String date) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        ArrayList<Ticket> tickets = BookController.searchTicket(departure, arrival, date);

        for (Ticket ticket : tickets) {
            model.addRow(ticket.toStringArray());
        }
    }

    public void btnBookClick(int selectedRowIdx) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int id = Integer.parseInt(model.getValueAt(selectedRowIdx, 0).toString());
            BookController.bookTicket(id);
            // model.setRowCount(0);
            model.removeRow(selectedRowIdx);
            JOptionPane.showMessageDialog(null, "Ticket booked successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Please select a ticket!");
        }
    }

    public void btnCloseClick() {
        dispose();
    }
}
