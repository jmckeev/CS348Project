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
    private final String[] FILES = {"droptables2.txt", "tables.txt", "data.txt", "index.txt"};
    private final String[] TABLES = {"Student", "Professor", "Course", "Takes", "Teaches", "TA", "Due",
            "Team", "Friend"};
    private final int COMPONENTS = 2;
    private ArrayList<ArrayList<String>> courses;

    private ArrayList<ArrayList<String>> friendsCourse;
    private ArrayList<ArrayList<String>> gradesCourse;
    private ArrayList<ArrayList<ArrayList<String>>> dueDatesAssignments;
    private ArrayList<String> friendList;
    private ArrayList<ArrayList<ArrayList<String>>> friendListClasses;
    private ArrayList<ArrayList<String>> newFriends;
    private ArrayList<String> updates;

    private ArrayList<ArrayList<String>> professorCourses;
    private ArrayList<ArrayList<ArrayList<String>>> tas;
    private ArrayList<ArrayList<ArrayList<String>>> eligibleTas;
    private ArrayList<String> crns;
    private String name;

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
        this.friendsCourse = new ArrayList<>();
        this.gradesCourse = new ArrayList<>();
        this.dueDatesAssignments = new ArrayList<>();
        this.friendList = new ArrayList<>();
        this.friendListClasses = new ArrayList<>();
        this.newFriends = new ArrayList<>();
        this.updates = new ArrayList<>();

        this.professorCourses = new ArrayList<>();
        this.tas = new ArrayList<>();
        this.eligibleTas = new ArrayList<>();
        this.crns = new ArrayList<>();
        this.loadDatabase();
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
            this.setupCommands[2] = "use " + username + ";";
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
        StringBuilder fullQuery = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (true) {
                String temp = bufferedReader.readLine();
                if (temp == null) {
                    break;
                }
                fullQuery.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile() {
        Loader loader = new Loader("Saving Data", "Writing to file...");
        File file = new File("data.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < this.TABLES.length; i++) {
                this.connection.getBaos().reset();
                ArrayList<ArrayList<String>> output = this.query.getTable("SELECT * FROM " + this.TABLES[i] + ";", "READ UNCOMMITTED");
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
            for (int i = 0; i < this.updates.size(); i++) {
                bufferedWriter.write(this.updates.get(i) + "\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            return;
        }
        loader.cleanup();
    }

    public String addSpaces(String string) {
        if (string.equals("RileyMcKeever")) {
            return "Riley McKeever";
        }
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
        this.updateAll();
        this.writeToFile();
        this.connection.cleanup();
    }

    private void updateAll() {
        for (int i = 0; i < this.updates.size(); i++) {
            this.query.sendQuery(this.updates.get(i), "READ UNCOMMITTED");
        }
    }

    public void initializeFriendList() {
        this.friendList = new ArrayList<>();
        this.friendList.add("name");
    }

    public Query getQuery() {
        return this.query;
    }

    public String getUsername() { return this.username; }

    public void setCourses(ArrayList<ArrayList<String>> courses) {
        this.courses = courses;
    }

    public ArrayList<ArrayList<String>> getCourses() {
        return this.courses;
    }

    public ArrayList<ArrayList<String>> getFriendsCourse() {
        return friendsCourse;
    }

    public ArrayList<ArrayList<String>> getGradesCourse() {
        return gradesCourse;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getDueDatesAssignments() {
        return dueDatesAssignments;
    }


    public ArrayList<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<String> friendList) {
        this.friendList = friendList;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getFriendListClasses() {
        return friendListClasses;
    }

    public void setFriendListClasses(ArrayList<ArrayList<ArrayList<String>>> friendListClasses) {
        this.friendListClasses = friendListClasses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<ArrayList<String>> getNewFriends() {
        return newFriends;
    }

    public void setNewFriends(ArrayList<ArrayList<String>> newFriends) {
        this.newFriends = newFriends;
    }

    public ArrayList<String> getUpdates() {
        return updates;
    }

    public ArrayList<ArrayList<String>> getProfessorCourses() {
        return professorCourses;
    }

    public void setProfessorCourses(ArrayList<ArrayList<String>> professorCourses) {
        this.professorCourses = professorCourses;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getTas() {
        return tas;
    }

    public void setTas(ArrayList<ArrayList<ArrayList<String>>> tas) {
        this.tas = tas;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getEligibleTas() {
        return eligibleTas;
    }

    public ArrayList<String> getCrns() {
        return crns;
    }

    public void setCrns(ArrayList<String> crns) {
        this.crns = crns;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
