import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Remove extends JFrame {
    private StudentHandler studentHandler;
    private JPanel jPanel;
    private ArrayList<JLabel> names;
    private ArrayList<JButton> remove;

    public Remove(StudentHandler studentHandler) {
        this.studentHandler = studentHandler;
        this.names = new ArrayList<>();
        this.remove = new ArrayList<>();
        for (int i = 1; i < studentHandler.getInitializer().getFriendList().size(); i++) {
            this.names.add(new JLabel(studentHandler.getInitializer().addSpaces(studentHandler.getInitializer().getFriendList().get(i))));
            this.remove.add(new JButton("Remove"));
        }
        this.jPanel = new JPanel();
        this.jPanel.setLayout(null);
        this.setTitle("Remove Friends");
        this.setLocationRelativeTo(null);
        this.setSizeLocation();
        this.addAll();
        this.addActionListeners();
        this.getContentPane().add(this.jPanel);
        this.setVisible(true);
    }

    private void setSizeLocation() {
        this.setSize(500, 400);
        for (int i = 0; i < this.names.size(); i++) {
            this.names.get(i).setSize(400, 20);
            this.names.get(i).setLocation(10, (i * 20) + 30);
            this.remove.get(i).setSize(150, 20);
            this.remove.get(i).setLocation(100, (i * 20) + 30);
        }
    }

    private void addAll() {
        for (int i = 0; i < this.names.size(); i++) {
            this.jPanel.add(this.names.get(i));
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
        int index = (jButton.getY() - 30) / 20;
        System.out.println("index = " + index);
        this.studentHandler.getInitializer().getUpdates().add("DELETE FROM Friend WHERE main = " + this.studentHandler.getInitializer().getUsername() + " AND target = " + this.studentHandler.getInitializer().getFriendList().get(index) + ";");
//        this.names.remove(index);
//        this.remove.remove(index);
//        this.relocate();
    }

    private void relocate() {
        for (int i = 0; i < this.names.size(); i++) {
            this.names.get(i).setLocation(10, (i * 20) + 30);
            this.remove.get(i).setLocation(100, (i * 20) + 30);
        }
    }
}
