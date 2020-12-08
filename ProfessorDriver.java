import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfessorDriver extends JFrame {
    private JPanel jPanel;
    private JLabel message;
    private JRadioButton taList;
    private JRadioButton add;
    private JRadioButton remove;
    private JButton logout;
    private JButton next;
    private ButtonGroup buttonGroup;
    private Initializer initializer;
    private ProfessorHandler professorHandler;

    public enum PROFESSOR_STATE {
        taList,
        add,
        remove
    }

    public ProfessorDriver(Initializer initializer) {
        this.initializer = initializer;
        this.professorHandler = new ProfessorHandler(this, this.initializer);
        this.setTitle("Homepage");
        this.jPanel = new JPanel();
        this.buttonGroup = new ButtonGroup();
        this.message = new JLabel("Welcome back, " + this.initializer.getName() + ". What would you like to do today?");
        this.taList = new JRadioButton("TA List");
        this.taList.setActionCommand("TA List");
        this.taList.setSelected(true);
        this.add = new JRadioButton("Add TA");
        this.add.setActionCommand("Add TA");
        this.remove = new JRadioButton("Remove TA");
        this.remove.setActionCommand("Remove TA");
        this.logout = new JButton("Logout");
        this.next = new JButton("Next");

        this.setSize(500, 400);
        this.jPanel.setLayout(null);
        this.setLocationRelativeTo(null);

        this.setSizeLocation();
        this.addAll();
        this.addButtons();
        this.addListeners();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(this.jPanel);
        this.setVisible(true);
    }

    private void setSizeLocation() {
        this.message.setSize(400, 20);
        this.message.setLocation(80, 10);
        this.taList.setSize(400, 20);
        this.taList.setLocation(20, 70);
        this.add.setSize(400, 20);
        this.add.setLocation(20, 95);
        this.remove.setSize(400, 20);
        this.remove.setLocation(20, 120);
        this.logout.setSize(80, 20);
        this.logout.setLocation(250, 330);
        this.next.setSize(80, 20);
        this.next.setLocation(150, 330);
    }

    private void addAll() {
        this.jPanel.add(this.message);
        this.jPanel.add(this.taList);
        this.jPanel.add(this.add);
        this.jPanel.add(this.remove);
        this.jPanel.add(this.logout);
        this.jPanel.add(this.next);
    }

    private void addButtons() {
        this.buttonGroup.add(this.taList);
        this.buttonGroup.add(this.add);
        this.buttonGroup.add(this.remove);
    }

    private void addListeners() {
        this.logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                initializer.cleanup();
            }
        });

        this.next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temp = buttonGroup.getSelection().getActionCommand();
                if (temp.equals("TA List")) {
                    professorHandler.handle(PROFESSOR_STATE.taList);
                } else if (temp.equals("Add TA")) {
                    professorHandler.handle(PROFESSOR_STATE.add);
                } else if (temp.equals("Remove TA")) {
                    professorHandler.handle(PROFESSOR_STATE.remove);
                }
            }
        });
    }
}
