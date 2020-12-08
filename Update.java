import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Update extends JFrame {
    private StudentHandler studentHandler;
    private JPanel jPanel;
    private JTextField jTextField;
    private JButton jButton;

    public Update(StudentHandler studentHandler) {
        this.studentHandler = studentHandler;
        this.jTextField = new JTextField();
        this.jButton = new JButton("Enter");
        this.jPanel = new JPanel();
        this.jPanel.setLayout(null);
        this.setTitle("Update name");
        this.setLocationRelativeTo(null);
        this.setSizeLocation();
        this.addAll();
        this.addActionListeners();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().add(this.jPanel);
        this.setVisible(true);
    }

    private void setSizeLocation() {
        this.setSize(400, 200);
        this.jTextField.setSize(100, 20);
        this.jTextField.setLocation(50, 20);
        this.jButton.setSize(80, 20);
        this.jButton.setLocation(150, 20);
    }

    private void addAll() {
        this.jPanel.add(this.jTextField);
        this.jPanel.add(this.jButton);
    }

    private void addActionListeners() {
        this.jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newName(jTextField.getText());
            }
        });
    }

    public void newName(String name) {
        this.studentHandler.getInitializer().setName(name);
        this.studentHandler.getInitializer().getUpdates().add("UPDATE Student SET name = " + name + " WHERE student_id = \"" + this.studentHandler.getInitializer().getUsername() + "\";");
    }
}
