import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateProfessor extends JFrame {
    private ProfessorHandler professorHandler;
    private JPanel jPanel;
    private JTextField jTextField;
    private JButton jButton;

    public UpdateProfessor(ProfessorHandler professorHandler) {
        this.professorHandler = professorHandler;
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
        this.professorHandler.getInitializer().setName(name);
        this.professorHandler.getInitializer().getUpdates().add("UPDATE Professor SET name = " + name + " WHERE professor_id = \"" + this.professorHandler.getInitializer().getUsername() + "\";");
    }
}
