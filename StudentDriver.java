import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDriver extends JFrame {
    private JPanel jPanel;
    private JLabel message;
    private JRadioButton userClasses;
    private JRadioButton friendSchedule;
    private JRadioButton addRemove;
    private JButton logout;
    private JButton next;
    private ButtonGroup buttonGroup;
    private Initializer initializer;
    private StudentHandler studentHandler;
    private String name;

    public enum STUDENT_STATE {
        userClasses,
        friendSchedule,
        addRemove
    }

    public StudentDriver(Initializer initializer) {
        this.initializer = initializer;
        this.studentHandler = new StudentHandler(this, this.initializer);
        this.setTitle("Homepage");
        this.jPanel = new JPanel();
        this.buttonGroup = new ButtonGroup();
        this.name = this.initializer.getQuery().getTableColumn("SELECT name FROM Student WHERE student_id = \"" + this.initializer.getUsername() +  "\";", "name").get(1);
        this.message = new JLabel("Welcome back, " + name + ". What would you like to do today?");
        this.userClasses = new JRadioButton("My Classes");
        this.userClasses.setActionCommand("My Classes");
        this.userClasses.setSelected(true);
        this.friendSchedule = new JRadioButton("View Friends' Schedules");
        this.friendSchedule.setActionCommand("View Friends' Schedules");
        this.addRemove = new JRadioButton("Add/Remove Friend");
        this.addRemove.setActionCommand("Add/Remove Friend");
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
        this.userClasses.setSize(400, 20);
        this.userClasses.setLocation(20, 70);
        this.friendSchedule.setSize(400, 20);
        this.friendSchedule.setLocation(20, 95);
        this.addRemove.setSize(400, 20);
        this.addRemove.setLocation(20, 120);
        this.logout.setSize(80, 20);
        this.logout.setLocation(250, 330);
        this.next.setSize(80, 20);
        this.next.setLocation(150, 330);
    }

    private void addAll() {
        this.jPanel.add(this.message);
        this.jPanel.add(this.userClasses);
        this.jPanel.add(this.friendSchedule);
        this.jPanel.add(this.addRemove);
        this.jPanel.add(this.logout);
        this.jPanel.add(this.next);
    }

    private void addButtons() {
        this.buttonGroup.add(this.userClasses);
        this.buttonGroup.add(this.friendSchedule);
        this.buttonGroup.add(this.addRemove);
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
                if (temp.equals("My Classes")) {
                    studentHandler.handle(STUDENT_STATE.userClasses);
                } else if (temp.equals("View Friends' Schedules")) {
                    studentHandler.handle(STUDENT_STATE.friendSchedule);
                } else if (temp.equals("Add/Remove Friend")) {
                    studentHandler.handle(STUDENT_STATE.addRemove);
                }
            }
        });
    }
}
