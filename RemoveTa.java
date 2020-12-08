import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RemoveTa extends JFrame {
    private ProfessorHandler professorHandler;
    private JPanel jPanel;
    private ArrayList<JLabel> courses;
    private ArrayList<JLabel> names;
    private ArrayList<JButton> remove;

    public RemoveTa(ProfessorHandler professorHandler) {
        this.professorHandler = professorHandler;
        this.courses = new ArrayList<>();
        this.names = new ArrayList<>();
        this.remove = new ArrayList<>();
        if (this.professorHandler.getInitializer().getProfessorCourses() == null) {
            this.courses.add(new JLabel("No courses"));
        } else {
            for (int i = 1; i < this.professorHandler.getInitializer().getProfessorCourses().size(); i++) {
                this.courses.add(new JLabel(this.professorHandler.getInitializer().getProfessorCourses().get(0).get(i) + " " + this.professorHandler.getInitializer().getProfessorCourses().get(1).get(i)));
                for (int j = 1; j < this.professorHandler.getInitializer().getTas().get(i - 1).size(); j++) {
                    this.names.add(new JLabel(this.professorHandler.getInitializer().addSpaces(this.professorHandler.getInitializer().getTas().get(i - 1).get(1).get(j)) + " (" + this.professorHandler.getInitializer().getEligibleTas().get(i - 1).get(0).get(j) + ")"));
                    this.remove.add(new JButton("Remove"));

                }
            }
        }

        this.jPanel = new JPanel();
        this.jPanel.setLayout(null);
        this.setTitle("Add TAs");
        this.setLocationRelativeTo(null);
        this.setSizeLocation();
        this.addAll();
        this.addActionListeners();
        this.getContentPane().add(this.jPanel);
        this.setVisible(true);
    }

    private void setSizeLocation() {
        this.setSize(500, 400);
        for (int i = 0; i < this.courses.size(); i++) {
            this.courses.get(i).setSize(400, 20);
            this.courses.get(i).setLocation(10, (i * this.names.size() * 20) + 30);
        }
        for (int i = 0; i < this.names.size(); i++) {
            this.names.get(i).setSize(400, 20);
            this.names.get(i).setLocation(10, (i * 20) + 60);
        }
        for (int i = 0; i < this.remove.size(); i++) {
            this.remove.get(i).setSize(150, 20);
            this.remove.get(i).setLocation(200, (i * 20) + 60);
        }
    }

    private void addAll() {
        for (int i = 0; i < this.courses.size(); i++) {
            this.jPanel.add(this.courses.get(i));
        }
        for (int i = 0; i < this.names.size(); i++) {
            this.jPanel.add(this.names.get(i));
        }
        for (int i = 0; i < this.remove.size(); i++) {
            this.jPanel.add(this.remove.get(i));
        }
    }

    private void addActionListeners() {
        for (int i = 0; i < this.remove.size(); i++) {
            this.remove.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick((JButton) e.getSource());
                }
            });
        }
    }

    private void handleButtonClick(JButton jButton) {
        int index = (jButton.getY() - 60) / 20;
        System.out.println("index = " + index);
        System.out.println("DELETE FROM TA WHERE student_id = " + this.professorHandler.getInitializer().getTas().get(index / this.courses.size()).get(0).get(index + 1));
        this.professorHandler.getInitializer().getUpdates().add("DELETE FROM TA WHERE student_id = " + this.professorHandler.getInitializer().getTas().get(index / this.courses.size()).get(0).get(index + 1));
        this.professorHandler.getInitializer().getQuery().prepare("removeta", "DELETE FROM TA WHERE student_id = ?", "READ UNCOMMITTED");
        this.professorHandler.getInitializer().getQuery().setVariables("@taid," + this.professorHandler.getInitializer().getTas().get(index / this.courses.size()).get(0).get(index + 1), "READ UNCOMMITTED");
        this.professorHandler.getInitializer().getQuery().sendQuery("EXECUTE removeta USING @taid;", "READ COMMITTED");
        //        System.out.println("INSERT INTO TA VALUES(" + this.professorHandler.getInitializer().getTas().get(index / this.courses.size()).get(0).get(index + 1) + ", \"2020\", \"Fall\", " + this.professorHandler.getInitializer().getCrns().get(index / this.courses.size() + 1) + ");");
//        this.professorHandler.getInitializer().getUpdates().add("INSERT INTO TA VALUES(" + this.professorHandler.getInitializer().getTas().get(index / this.courses.size()).get(0).get(index + 1) + ", \"2020\", \"Fall\", " + this.professorHandler.getInitializer().getCrns().get(index / this.courses.size() + 1) + ");");
//        if (!this.professorHandler.getInitializer().getTas().get(index / this.courses.size()).get(0).contains(this.professorHandler.getInitializer().getEligibleTas().get(index / this.courses.size()).get(0).get(index + 1))) {
//            this.professorHandler.getInitializer().getTas().get(index / this.courses.size()).get(0).add(this.professorHandler.getInitializer().getEligibleTas().get(index / this.courses.size()).get(0).get(index + 1));
//        }





//        this.studentHandler.getInitializer().getUpdates().add("INSERT INTO Friend VALUES(" + this.studentHandler.getInitializer().getUsername() + ", " + this.studentHandler.getInitializer().getNewFriends().get(1).get(index + 1) + ");");
////        this.names.get(index).setVisible(false);
////        this.add.get(index).setVisible(false);
//        if (this.studentHandler.getInitializer().getFriendList() == null) {
//            this.studentHandler.getInitializer().initializeFriendList();
//        }
//        if (!this.studentHandler.getInitializer().getFriendList().contains(this.names.get(index).getText())) {
//            this.studentHandler.getInitializer().getFriendList().add(this.names.get(index).getText());
//        }




//        int index2 = this.studentHandler.getInitializer().getNewFriends().get(0).indexOf(this.names.get(index).getText());
//        this.studentHandler.getInitializer().getNewFriends().get(0).remove(index2);
//        this.studentHandler.getInitializer().getNewFriends().get(1).remove(index2);
//        this.names.remove(index);
//        this.add.remove(index);
//        this.relocate();
    }

    private void relocate() {
        for (int i = 0; i < this.names.size(); i++) {
            this.names.get(i).setLocation(10, (i * 20) + 30);
            this.remove.get(i).setLocation(100, (i * 20) + 30);
        }
        this.repaint();
    }
}
