import javax.swing.*;
import java.util.ArrayList;

public class TaList extends JFrame {
    private ProfessorHandler professorHandler;
    private JPanel jPanel;
    private ArrayList<JLabel> courseTas;

    public TaList(ProfessorHandler professorHandler) {
        this.professorHandler = professorHandler;
        this.courseTas = new ArrayList<>();
        this.jPanel = new JPanel();
        this.jPanel.setLayout(null);
        this.setTitle("TA List");
        this.setLocationRelativeTo(null);
        this.setLabels(this.professorHandler.getInitializer().getProfessorCourses(), this.professorHandler.getInitializer().getTas());
        this.setSizeLocation();
        this.addAll();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().add(this.jPanel);
        this.setVisible(true);
    }

    private void setLabels(ArrayList<ArrayList<String>> professorCourses, ArrayList<ArrayList<ArrayList<String>>> tas) {
        if (professorCourses != null && tas == null) {
            for (int i = 1; i < professorCourses.size(); i++) {
                this.courseTas.add(new JLabel(professorCourses.get(0).get(i) + ": No TAs"));
            }
            return;
        } else if (professorCourses == null) {
            this.courseTas.add(new JLabel("No Courses"));
            return;
        }
        for (int i = 1; i < professorCourses.get(0).size(); i++) {
            String temp = professorCourses.get(0).get(i) + " " + professorCourses.get(1).get(i) + ": ";
            for (int j = 1; j < tas.get(i - 1).size(); j++) {
                temp += this.professorHandler.getInitializer().addSpaces(tas.get(i - 1).get(1).get(j)) + " (" + tas.get(i - 1).get(0).get(j) + ")";
                if (j != tas.get(i - 1).size() - 1) {
                    temp += ",";
                }
            }
            this.courseTas.add(new JLabel(temp));
        }
    }

    private void setSizeLocation() {
        this.setSize(500, 400);
        for (int i = 0; i < this.courseTas.size(); i++) {
            this.courseTas.get(i).setSize(400, 20);
            this.courseTas.get(i).setLocation(10, (i * 20) + 50);
        }
    }

    private void addAll() {
        for (int i = 0; i < this.courseTas.size(); i++) {
            this.jPanel.add(this.courseTas.get(i));
        }
    }
}
