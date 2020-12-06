import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Authentication extends JFrame {
    private String[] loginInformation;
    private JPanel jPanel;
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel username;
    private JLabel password;
    private JComboBox studentProfessor;
    private final String[] options = { "", "Student", "Professor" };
    private String option;
    private Encryption encryption;

    public Authentication(Component component) {
        this.loginInformation = new String[2];
        //this.encryption = new Encryption("40674244454045cb9a70040a30e1c007".getBytes(),
                                         //"@1B2c3D4e5F6g7H8".getBytes());
        this.setTitle("Login");
        this.jPanel = new JPanel();
        this.loginButton = new JButton("Login");
        this.usernameField = new JTextField(15);
        this.passwordField = new JPasswordField(15);
        this.username = new JLabel("Username: ");
        this.password = new JLabel("Password: ");
        this.studentProfessor = new JComboBox(options);
        this.studentProfessor.setSelectedIndex(0);

        this.setSize(400, 200);
        this.jPanel.setLayout(null);
        this.setLocationRelativeTo(component);
        this.setSizes();
        this.addComponents();

        this.getContentPane().add(this.jPanel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.requestFocus();

        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (true) {
                    //loginInformation[0] = encryption.encrypt(usernameField.getText().getBytes());
                    //loginInformation[1] = encryption.encrypt(new String(passwordField.getPassword()).getBytes());
                    loginInformation[0] = usernameField.getText();
                    loginInformation[1] = new String(passwordField.getPassword());
                    if (loginInformation[0] != null && loginInformation[1] != null) {
                        break;
                    }
                }
                updateLoginInformation(usernameField.getText(), new String(passwordField.getPassword()));
            }
        });

        this.studentProfessor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox jComboBox = (JComboBox) e.getSource();
                updateOption((String) jComboBox.getSelectedItem());
            }
        });
    }

    private void setSizes() {
        this.loginButton.setLocation(150, 125);
        this.loginButton.setSize(80, 20);
        this.username.setLocation(20, 30);
        this.username.setSize(80, 20);
        this.usernameField.setLocation(90, 30);
        this.usernameField.setSize(200, 20);
        this.password.setLocation(20, 65);
        this.password.setSize(80, 20);
        this.passwordField.setLocation(90, 65);
        this.passwordField.setSize(200, 20);
        this.studentProfessor.setLocation(90, 100);
        this.studentProfessor.setSize(200, 20);
    }

    private void addComponents() {
        this.jPanel.add(this.username);
        this.jPanel.add(this.usernameField);
        this.jPanel.add(this.password);
        this.jPanel.add(this.passwordField);
        this.jPanel.add(this.loginButton);
        this.jPanel.add(this.studentProfessor);
    }

    private void updateLoginInformation(String username, String password) {
        this.loginInformation[0]= username;
        this.loginInformation[1] = password;
    }

    private void updateOption(String option) {
        this.option = option;
    }

    public String[] getLoginInformation() {
        return this.loginInformation;
    }

    public Encryption getEncryption() {
        return this.encryption;
    }

    public void cleanup() {
        this.dispose();
    }

    public void incorrect() {
        //this.passwordField.setText("");
        JLabel incorrectString = new JLabel("Username or password incorrect");
        incorrectString.setLocation(75, 50);
        incorrectString.setSize(200, 100);
        this.jPanel.add(incorrectString);
        this.repaint();
    }

    public String getOption() {
        return this.option;
    }
}
