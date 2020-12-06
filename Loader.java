import javax.swing.*;

public class Loader extends JFrame {
    private JPanel jPanel;
    private JLabel loadLabel;

    public Loader(String title, String message) {
        this.setTitle(title);
        this.jPanel = new JPanel();
        this.loadLabel = new JLabel(message);

        this.setSize(400, 200);
        this.jPanel.setLayout(null);
        this.setLocationRelativeTo(null);

        this.loadLabel.setLocation(100, 20);
        this.loadLabel.setSize(295, 100);
        this.jPanel.add(this.loadLabel);

        this.getContentPane().add(this.jPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void updateTitle(String text) {
        this.loadLabel.setText(text);
    }

    public void cleanup() {
        this.dispose();
    }
}
