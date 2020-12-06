import com.jcraft.jsch.JSchException;

import java.io.*;
import java.util.ArrayList;

public class Initializer {
    private String username;
    private String password;
    private String host;
    private String[] setupCommands = new String[3];
    private Connection connection;
    private Query query;
    private final String[] FILES = {"src/droptables2.txt", "src/tables2.txt", "src/data2.txt"};
    private final String[] TABLES = {"Student", "Professor", "Course", "Takes", "Teaches", "TA", "Due",
            "Team", "Friend"};
    private final int COMPONENTS = 2;

    public String option;

    public Initializer() {
        Authentication authentication = new Authentication(null);
        while (true) {
            this.setUserPassHost(authentication);
            try {
                this.connection = new Connection(this.username, this.password, this.host);
                this.option = authentication.getOption();
                break;
            } catch (JSchException e) {
                authentication.incorrect();
            }
        }
        authentication.cleanup();
        this.login();
        this.query = new Query(this.connection);
        //this.loadDatabase();
    }

    private void setUserPassHost(Authentication authentication) {
        String[] loginInformation = authentication.getLoginInformation();
        while (loginInformation[0] == null) {
            loginInformation = authentication.getLoginInformation();
        }
        String[] tokens = loginInformation[0].split("@");
        if (tokens.length == COMPONENTS) {
            this.host = tokens[1];
            this.username = tokens[0];
            this.password = loginInformation[1];
            this.setupCommands[0] = "mysql -h mydb.itap.purdue.edu -p";
            this.setupCommands[1] = password;
            this.setupCommands[2] = "use " + username;
        }
    }

    private void login() {
        for (int i = 0; i < this.setupCommands.length; i++) {
            this.connection.getShellStream().println(this.setupCommands[i]);
            this.connection.getShellStream().flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("baos = " + new String(this.connection.getBaos().toByteArray()));
        this.connection.getBaos().reset();
    }

    private void loadDatabase() {
        Loader loader = new Loader("Loading", "Cleaning old tables...");
        for (int i = 0; i < this.FILES.length; i++) {
            if (i == 1) {
                loader.updateTitle("Creating tables...");
            } else if (i == 2) {
                loader.updateTitle("Importing data...");
            }
            this.runFile(this.FILES[i]);
        }
        loader.cleanup();
    }

    private void runFile(String fileName) {
        File file = new File(fileName);
        StringBuilder end = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (true) {
                String temp = bufferedReader.readLine();
                if (temp == null) {
                    break;
                }
                end.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] tokens = end.toString().split(";");
        for (int i = 0; i < tokens.length; i++) {
            System.out.println(this.query.sendQuery(tokens[i] + ";\n"));
        }
    }

    private void writeToFile() {
        Loader loader = new Loader("Saving Data", "Writing to file...");
        File file = new File("src/data3.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < this.TABLES.length; i++) {
                this.connection.getBaos().reset();
                ArrayList<ArrayList<String>> output = this.query.getTable("SELECT * FROM " + this.TABLES[i] + ";");
                if (output != null) {
                    String temp = "INSERT INTO " + this.TABLES[i] + " VALUES\n";
                    for (int j = 1; j < output.get(0).size(); j++) {
                        temp += "(";
                        for (int k = 0; k < output.size(); k++) {
                            try {
                                int check = Integer.parseInt(output.get(k).get(j));
                                if (check < 10000) {
                                    temp += check;
                                } else {
                                    temp += "\"" + check + "\"";
                                }
                            } catch (NumberFormatException e) {
                                temp += "\"" + this.addSpaces(output.get(k).get(j)) + "\"";
                                //System.out.println("NEW TEMP = " + this.addSpaces(output.get(k).get(j)));
                            }
                            if (k != output.size() - 1) {
                                temp += ", ";
                            }
                        }
                        temp += ")";
                        if (j == output.get(0).size() - 1) {
                            bufferedWriter.write(temp + ";\n\n");
                        } else {
                            temp += ",\n";
                        }
                    }
                }
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            return;
        }
        loader.cleanup();
    }

    private String addSpaces(String string) {
        if (string.toUpperCase().equals(string)) {
            return string;
        }
        String ret = "";
        for (int i = 0; i < string.length(); i++) {
            if ((i > 0) && ((string.charAt(i) > 64 && string.charAt(i) < 91)) && (string.charAt(i - 1) != '-')) {
                ret += " ";
            }
            ret += string.charAt(i);
        }
        return ret;
    }

    public void cleanup() {
        //this.writeToFile();
        this.connection.cleanup();
    }

    public Query getQuery() {
        return this.query;
    }

    public String getUsername() { return this.username; }

    public String getPassword() { return this.password; }
}