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
            for (int i = 1; i < Math.min(this.studentHandler.getInitializer().getNewFriends().get(0).size(), 15); i++) {
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
        this.studentHandler.getInitializer().getUpdates().add("INSERT INTO Friend VALUES(" + this.studentHandler.getInitializer().getUsername() + ", " + this.studentHandler.getInitializer().getNewFriends().get(1).get(index + 1) + ");");
        if (this.studentHandler.getInitializer().getFriendList() == null) {
            this.studentHandler.getInitializer().initializeFriendList();
        }
        if (!this.studentHandler.getInitializer().getFriendList().contains(this.names.get(index).getText())) {
            this.studentHandler.getInitializer().getFriendList().add(this.names.get(index).getText());
            this.studentHandler.getInitializer().getUpdates().add("INSERT INTO Friend VALUES(" + this.studentHandler.getInitializer().getUsername() + ", " + this.studentHandler.getInitializer().getNewFriends().get(1).get(index + 1) + ");");
            this.studentHandler.getInitializer().getQuery().prepare("insertFriend", "INSERT INTO Friend VALUES(?, ?)", "READ UNCOMMITTED");
            this.studentHandler.getInitializer().getQuery().setVariables("@friendname," + this.studentHandler.getInitializer().getNewFriends().get(1).get(index + 1), "READ UNCOMMITTED");
            this.studentHandler.getInitializer().getQuery().sendQuery("EXECUTE insertFriend USING @username,@friendname;", "READ COMMITTED");
        }
    }
}
