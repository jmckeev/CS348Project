import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Add extends JFrame {
    private StudentHandler studentHandler;
    private JPanel jPanel;
    private ArrayList<JLabel> names;
    private ArrayList<JButton> add;

    public Add(StudentHandler studentHandler) {
        this.studentHandler = studentHandler;
        this.names = new ArrayList<>();
        this.add = new ArrayList<>();
        if (this.studentHandler.getInitializer().getNewFriends() == null) {
            this.names.add(new JLabel("No suggestions"));
        } else {
            for (int i = 1; i < this.studentHandler.getInitializer().getNewFriends().get(0).size(); i++) {
                this.names.add(new JLabel(studentHandler.getInitializer().addSpaces(studentHandler.getInitializer().getNewFriends().get(0).get(i))));
                this.add.add(new JButton("Add"));
            }
        }
        this.jPanel = new JPanel();
        this.jPanel.setLayout(null);
        this.setTitle("Add Friends");
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
        }
        for (int i = 0; i < this.add.size(); i++) {
            this.add.get(i).setSize(150, 20);
            this.add.get(i).setLocation(100, (i * 20) + 30);
        }
    }

    private void addAll() {
        for (int i = 0; i < this.names.size(); i++) {
            this.jPanel.add(this.names.get(i));
        }
        for (int i = 0; i < this.add.size(); i++) {
            this.jPanel.add(this.add.get(i));
        }
    }

    private void addActionListeners() {
        for (int i = 0; i < this.add.size(); i++) {
            this.add.get(i).addActionListener(new ActionListener() {
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
        this.studentHandler.getInitializer().getUpdates().add("INSERT INTO Friend VALUES(" + this.studentHandler.getInitializer().getUsername() + ", " + this.studentHandler.getInitializer().getNewFriends().get(1).get(index + 1) + ");");
//        this.names.get(index).setVisible(false);
//        this.add.get(index).setVisible(false);
        if (this.studentHandler.getInitializer().getFriendList() == null) {
            this.studentHandler.getInitializer().initializeFriendList();
        }
        if (!this.studentHandler.getInitializer().getFriendList().contains(this.names.get(index).getText())) {
            this.studentHandler.getInitializer().getFriendList().add(this.names.get(index).getText());
        }
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
            this.add.get(i).setLocation(100, (i * 20) + 30);
        }
        this.repaint();
    }
}
