import javax.swing.*;
import java.util.ArrayList;

public class FriendSchedule extends JFrame {
    private StudentHandler studentHandler;
    private JPanel jPanel;
    private ArrayList<JLabel> friendClasses;

    public FriendSchedule(StudentHandler studentHandler) {
        this.studentHandler = studentHandler;
        this.friendClasses = new ArrayList<>();
        this.jPanel = new JPanel();
        this.jPanel.setLayout(null);
        this.setTitle("Friends' Classes");
        this.setLocationRelativeTo(null);
        this.setLabels(this.studentHandler.getInitializer().getFriendList(), this.studentHandler.getInitializer().getFriendListClasses());
        this.setSizeLocation();
        this.addAll();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().add(this.jPanel);
        this.setVisible(true);
    }

    private void setLabels(ArrayList<String> friendList, ArrayList<ArrayList<ArrayList<String>>> friendListClasses) {
        if (friendList != null && friendListClasses == null) {
            for (int i = 1; i < friendList.size(); i++) {
                this.friendClasses.add(new JLabel(friendList.get(i) + ": No Classes"));
            }
            return;
        } else if (friendList == null) {
            this.friendClasses.add(new JLabel("No Friends"));
            return;
        }
        for (int i = 1; i < friendList.size(); i++) {
            String temp = this.studentHandler.getInitializer().addSpaces(friendList.get(i) + ": ");
            for (int j = 1; j < friendListClasses.get(i - 1).size(); j++) {
                temp += friendListClasses.get(i - 1).get(0).get(j) + " " + friendListClasses.get(i - 1).get(1).get(j);
                if (j != friendListClasses.get(i - 1).size() - 1) {
                    temp += ", ";
                }
            }
            this.friendClasses.add(new JLabel(temp));
        }
    }

    private void setSizeLocation() {
        this.setSize(500, 400);
        for (int i = 0; i < this.friendClasses.size(); i++) {
            this.friendClasses.get(i).setSize(400, 20);
            this.friendClasses.get(i).setLocation(10, (i * 20) + 50);
        }
    }

    private void addAll() {
        for (int i = 0; i < this.friendClasses.size(); i++) {
            this.jPanel.add(this.friendClasses.get(i));
        }
    }
}
