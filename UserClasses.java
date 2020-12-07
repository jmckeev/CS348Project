import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserClasses extends JFrame {
    private JPanel jPanel;
    private JLabel message;
    private ArrayList<JButton> classes;
    private StudentHandler studentHandler;

    public UserClasses(StudentHandler studentHandler) {
        this.studentHandler = studentHandler;
        this.classes = new ArrayList<>();
        this.setTitle("My Classes");
        this.jPanel = new JPanel();
        this.message = new JLabel("My Classes");
        this.jPanel.setLayout(null);
        this.setLocationRelativeTo(null);
        this.initializeButtons();
        this.setSizeLocation();
        this.addAll();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().add(this.jPanel);
        this.setVisible(true);
    }

    private void initializeButtons() {
        ArrayList<ArrayList<String>> courses = this.studentHandler.getInitializer().getCourses();
        for (int i = 1; i < courses.get(0).size(); i++) {
            this.classes.add(new JButton(courses.get(0).get(i) + " " + courses.get(1).get(i)));
        }
        for (int i = 0; i < this.classes.size(); i++) {
            this.classes.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick((JButton) e.getSource());
                }
            });
        }
    }

    private void handleButtonClick(JButton jButton) {
        int index = this.studentHandler.getInitializer().getCourses().indexOf(jButton.getText()) + 1;
        ClassInformation classInformation = new ClassInformation(this, index);
    }

    private void setSizeLocation() {
        this.setSize(500, 400);
        this.message.setSize(400, 20);
        this.message.setLocation(200, 10);
        for (int i = 0; i < this.classes.size(); i++) {
            this.classes.get(i).setSize(150, 20);
            this.classes.get(i).setLocation(180, (i * 50) + 50);
        }
    }

    private void addAll() {
        this.jPanel.add(this.message);
        for (int i = 0; i < this.classes.size(); i++) {
            this.jPanel.add(this.classes.get(i));
        }
    }

    public StudentHandler getStudentHandler() {
        return this.studentHandler;
    }
}
