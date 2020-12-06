import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class Connection {
    private Channel channel;
    private Session session;
    private PrintStream shellStream;
    private ByteArrayOutputStream baos;

    public Connection(String username, String password, String host) throws JSchException {
        try {
            JSch jSch = new JSch();
            this.session = jSch.getSession(username, host, 22);
            this.session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            this.session.setConfig(config);
            this.session.connect();

            this.channel = this.session.openChannel("shell");
            this.baos = new ByteArrayOutputStream();
            this.channel.setOutputStream(baos);
            this.shellStream = new PrintStream(this.channel.getOutputStream());
            this.channel.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cleanup() {
        this.channel.disconnect();
        this.session.disconnect();
    }

    public PrintStream getShellStream() {
        return this.shellStream;
    }

    public ByteArrayOutputStream getBaos() {
        return this.baos;
    }
}
