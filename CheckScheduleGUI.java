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
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CheckScheduleGUI extends JFrame {
    private JPanel contentPane;

    private JLabel labelTable;
    private JButton btnModify;
    private JButton btnCancel;
    private JButton btnClose;

    private JTable table;
    private JScrollPane scrolledTable;

    public CheckScheduleGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        labelTable = new JLabel("<Schedule>");
        labelTable.setBounds(30, 15, 100, 20);
        contentPane.add(labelTable);

        String header[] = { "id", "time", "date", "departure", "arrival" };
        DefaultTableModel model = new DefaultTableModel(header, 0); // header추가, 행은 0개 지정
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrolledTable = new JScrollPane(table);
        scrolledTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrolledTable.setBounds(30, 50, 450, 200);
        contentPane.add(scrolledTable);

        ArrayList<Ticket> tickets = BookController.getBookedTicket();
        for (Ticket ticket : tickets) {
            String[] row = ticket.toStringArray();
            model.addRow(row);
        }

        btnModify = new JButton("Modify schedule");
        btnModify.setBounds(130, 260, 120, 50);
        contentPane.add(btnModify);
        btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnModifyClick(table.getSelectedRows()[0]);
            }
        });

        btnCancel = new JButton("Cancel ticket");
        btnCancel.setBounds(260, 260, 100, 50);
        contentPane.add(btnCancel);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCancelClick(table.getSelectedRows()[0]);
            }
        });

        btnClose = new JButton("Close");
        btnClose.setBounds(370, 260, 100, 50);
        contentPane.add(btnClose);
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCloseClick();
            }
        });
    }

    public void btnModifyClick(int selectedRowIdx) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int id = Integer.parseInt(model.getValueAt(selectedRowIdx, 0).toString());
            String departure = model.getValueAt(selectedRowIdx, 3).toString();
            String arrival = model.getValueAt(selectedRowIdx, 4).toString();
            String date = model.getValueAt(selectedRowIdx, 2).toString();

            ModifyScheduleGUI modifyScheduleGUI = new ModifyScheduleGUI(id, departure, arrival, date);
            modifyScheduleGUI.show();

            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please select a row.");
        }
    }

    public void btnCancelClick(int selectedRowIdx) {
        try {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int id = Integer.parseInt(model.getValueAt(selectedRowIdx, 0).toString());
            BookController.cancelBook(id);
            model.removeRow(selectedRowIdx);
            JOptionPane.showMessageDialog(null, "Ticket canceled successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please select a row.");
        }
    }

    public void btnCloseClick() {
        this.dispose();
    }
}
