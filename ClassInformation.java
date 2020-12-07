import javax.swing.*;
import java.util.ArrayList;

public class ClassInformation extends JFrame{
    private JPanel jPanel;
    private JLabel className;
    private JLabel grade;
    private ArrayList<JLabel> dueDates;
    private ArrayList<JLabel> friends;

    public ClassInformation(UserClasses userClasses, int index) {
        this.className = new JLabel("Course: " + userClasses.getStudentHandler().getInitializer().getCourses().get(0).get(index + 1) + " " + userClasses.getStudentHandler().getInitializer().getCourses().get(1).get(index + 1));
        this.grade = new JLabel("Grade: " + userClasses.getStudentHandler().getInitializer().getGradesCourse().get(index).get(1));
        this.dueDates = new ArrayList<>();
        this.dueDates.add(new JLabel("Assignments: "));
        this.friends = new ArrayList<>();
        this.friends.add(new JLabel("Friends: "));
        ArrayList<ArrayList<String>> dates = userClasses.getStudentHandler().getInitializer().getDueDatesAssignments().get(index);
        ArrayList<String> friendList = userClasses.getStudentHandler().getInitializer().getFriendsCourse().get(index);
        if (dates != null) {
            for (int i = 1; i < dates.get(0).size(); i++) {
                this.dueDates.add(new JLabel(dates.get(0).get(i) + ": " + dates.get(1).get(i).substring(0, dates.get(1).get(i).indexOf(':') - 2) + " " + dates.get(1).get(i).substring(dates.get(1).get(i).indexOf(':') - 2)));
            }
        }
        if (friendList != null) {
            for (int i = 1; i < friendList.size(); i++) {
                this.friends.add(new JLabel(friendList.get(i)));
            }
        }
        this.jPanel = new JPanel();
        this.jPanel.setLayout(null);
        this.setTitle("Class Information");
        this.setLocationRelativeTo(null);
        this.setSizeLocation();
        this.addAll();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().add(this.jPanel);
        this.setVisible(true);
    }

    private void setSizeLocation() {
        this.setSize(500, 400);
        this.className.setSize(400, 20);
        this.className.setLocation(10, 10);
        this.grade.setSize(400, 20);
        this.grade.setLocation(200, 10);
        for (int i = 0; i < this.dueDates.size(); i++) {
            this.dueDates.get(i).setSize(400, 20);
            this.dueDates.get(i).setLocation(10, (i * 20) + 50);
        }
        for (int i = 0; i < this.friends.size(); i++) {
            this.friends.get(i).setSize(400, 20);
            this.friends.get(i).setLocation(10, (i * 20) + 200);
        }
    }

    private void addAll() {
        this.jPanel.add(this.className);
        this.jPanel.add(this.grade);
        for (int i = 0; i < this.dueDates.size(); i++) {
            this.jPanel.add(this.dueDates.get(i));
        }
        for (int i = 0; i < this.friends.size(); i++) {
            this.jPanel.add(this.friends.get(i));
        }
    }
}
