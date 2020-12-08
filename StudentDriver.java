import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentDriver extends JFrame {
    private JPanel jPanel;
    private JLabel message;
    private JRadioButton userClasses;
    private JRadioButton friendSchedule;
    private JRadioButton add;
    private JRadioButton remove;
    private JRadioButton update;
    private JButton logout;
    private JButton next;
    private ButtonGroup buttonGroup;
    private Initializer initializer;
    private StudentHandler studentHandler;

    public enum STUDENT_STATE {
        userClasses,
        friendSchedule,
        add,
        remove,
        updateName
    }

    public StudentDriver(Initializer initializer) {
        this.initializer = initializer;
        this.studentHandler = new StudentHandler(this, this.initializer);
        this.setTitle("Homepage");
        this.jPanel = new JPanel();
        this.buttonGroup = new ButtonGroup();
        this.message = new JLabel("Welcome back, " + this.initializer.getName() + ". What would you like to do today?");
        this.userClasses = new JRadioButton("My Classes");
        this.userClasses.setActionCommand("My Classes");
        this.userClasses.setSelected(true);
        this.friendSchedule = new JRadioButton("View Friends' Classes");
        this.friendSchedule.setActionCommand("View Friends' Classes");
        this.add = new JRadioButton("Add Friend");
        this.add.setActionCommand("Add Friend");
        this.remove = new JRadioButton("Remove Friend");
        this.remove.setActionCommand("Remove Friend");
        this.update = new JRadioButton("Update Name");
        this.update.setActionCommand("Update Name");
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
        this.add.setSize(400, 20);
        this.add.setLocation(20, 120);
        this.remove.setSize(400, 20);
        this.remove.setLocation(20, 145);
        this.update.setSize(400, 20);
        this.update.setLocation(20, 170);
        this.logout.setSize(80, 20);
        this.logout.setLocation(250, 330);
        this.next.setSize(80, 20);
        this.next.setLocation(150, 330);
    }

    private void addAll() {
        this.jPanel.add(this.message);
        this.jPanel.add(this.userClasses);
        this.jPanel.add(this.friendSchedule);
        this.jPanel.add(this.add);
        this.jPanel.add(this.remove);
        this.jPanel.add(this.update);
        this.jPanel.add(this.logout);
        this.jPanel.add(this.next);
    }

    private void addButtons() {
        this.buttonGroup.add(this.userClasses);
        this.buttonGroup.add(this.friendSchedule);
        this.buttonGroup.add(this.add);
        this.buttonGroup.add(this.remove);
        this.buttonGroup.add(this.update);
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
                } else if (temp.equals("View Friends' Classes")) {
                    studentHandler.handle(STUDENT_STATE.friendSchedule);
                } else if (temp.equals("Add Friend")) {
                    studentHandler.handle(STUDENT_STATE.add);
                } else if (temp.equals("Remove Friend")) {
                    studentHandler.handle(STUDENT_STATE.remove);
                } else if (temp.equals("Update Name")) {
                    studentHandler.handle(STUDENT_STATE.updateName);
                }
            }
        });
    }

    public void resetName() {
        this.message.setText("Welcome back, " + this.initializer.getName() + ". What would you like to do today?");
        this.repaint();
    }
}
