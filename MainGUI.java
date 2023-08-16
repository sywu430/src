import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainGUI extends JFrame {
    private JPanel contentPane;
    private JButton btnLogin;
    private JButton btnRegister;

    public MainGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 250);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLoginClick();
            }
        });
        btnLogin.setBounds(100, 40, 200, 50);
        contentPane.add(btnLogin);

        btnRegister = new JButton("Create Account");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRegisterClick();
            }
        });
        btnRegister.setBounds(100, 120, 200, 50);
        contentPane.add(btnRegister);
    }

    public void btnLoginClick() {
        LoginGUI loginGUI = new LoginGUI();
        this.dispose();
        loginGUI.show();
    }

    public void btnRegisterClick() {
        RegisterGUI registerGUI = new RegisterGUI();
        registerGUI.show();
    }
}
