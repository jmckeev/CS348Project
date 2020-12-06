import java.util.ArrayList;

public class Query {
    private Connection connection;

    public Query(Connection connection) {
        this.connection = connection;
    }

    public String sendQuery(String command) {
        this.connection.getShellStream().println(command);
        this.connection.getShellStream().flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new String(this.connection.getBaos().toByteArray());
    }

    public ArrayList<String> getColumns(String output) {
        if (output.contains("Empty set")) {
            return null;
        }
        ArrayList<String> ret = new ArrayList<>();
        output = output.substring(output.indexOf("+"));
        output = output.substring(output.indexOf("|"));
        int i = 0;
        String temp = "";
        while (output.charAt(i) != '\n') {
            if (output.charAt(i) != ' ' && output.charAt(i) != '|') {
                temp += output.charAt(i);
            } else {
                if (!temp.equals("")) {
                    ret.add(temp);
                }
                temp = "";
            }
            i++;
        }
        return ret;
    }

    public ArrayList<ArrayList<String>> getTable(String command) {
        this.connection.getBaos().reset();
        ArrayList<String> columns = this.getColumns(this.sendQuery(command));
        if (columns == null) {
            return null;
        }
        ArrayList<ArrayList<String>> ret = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            ret.add(new ArrayList<>());
            ret.get(i).add(columns.get(i));
        }
        String output = this.sendQuery(command);
        for (int i = 0; i < 2; i++) {
            output = output.substring(output.indexOf("+"));
            output = output.substring(output.indexOf("|"));
        }
        String temp = "";
        int i = 1;
        int index = 0;
        while (output.charAt(i) != '+') {
            if (output.charAt(i) == '|') {
                if (!temp.equals("")) {
                    ret.get(index).add(temp);
                    index++;
                    temp = "";
                }
            } else if (output.charAt(i) == '\n') {
                temp = "";
                index = 0;
            } else if (output.charAt(i) != ' ') {
                temp += output.charAt(i);
            }
            i++;
        }
        return ret;
    }

    public ArrayList<String> getTableColumn(String command, String column) {
        ArrayList<ArrayList<String>> table = this.getTable(command);
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).get(0).equals(column)) {
                return table.get(i);
            }
        }
        return null;
    }
}
