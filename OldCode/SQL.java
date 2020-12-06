import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Yizhen Yuan
 * @version 1.0
 * This file is the first version of the Group Due
 * Please use this with appropriate SQL file
 * first change
 */
public class SQL {
    static Statement statement;
    //TODO: Please fill in your user name and password, for password, a encryption is appreciated.
    static final String USERNAME = "yuan226";
    static final String PASSWORD = "shujuku";
   // static final String INSTANCE_CONNECTION_NAME ="striped-torus-292003:us-central1:cs348";
   // static final String DATABASE_NAME ="p1012901937431-d9svp8@gcp-sa-cloud-sql.iam.gserviceaccount.com";
    static boolean bugged=false;
    /*
        Some initialized steps for SQL server
     */
    static {
        try {
            statement = DriverManager.getConnection("jdbc:mysql://mydb.itap.purdue.edu/" + USERNAME + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", USERNAME, PASSWORD).createStatement();
            //statement=DriverManager.getConnection("jdbc:mysql:///"+DATABASE_NAME+"?cloudSqlInstance="+ INSTANCE_CONNECTION_NAME +"&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user="+USERNAME+"&password="+PASSWORD).createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Make sure you have turned on VPN");
            System.exit(1);
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        run("use " + USERNAME + ";");
    }

    /*
        @author Yizhen Yuan
        Removes all the data in the SQL server
     */
    static void clean() {
        try {
            run("SET FOREIGN_KEY_CHECKS=0;");
            ResultSet resultSet = getResult("show tables;");
            List<String> tables = new ArrayList<>();
            while (resultSet.next()) {
                tables.add(resultSet.getString(1) + ";");
            }
            tables.forEach(e -> run("drop table " + e + ";"));
            run("SET FOREIGN_KEY_CHECKS=1;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*
        @autor Yizhen Yuan
        @param the command you are going to execute in SQL server
        Support multiple lines, and auto coding standard to meet the requirement for MySQL
        make the error information more readable
     */
    static void run(String command) {
        if (command.length() == 0) return;
        command = command.replace("\n", " ");
        String[] commands = command.split(";");
        Arrays.stream(commands).map(e -> e + ";").map(e -> {
            for (; e.contains("  "); e = e.replace("  ", " ")) ;
            return e;
        }).forEach(sql -> {
            try {
                statement.execute(sql);
            } catch (SQLException throwables) {
                bugged=true;
                String anon = throwables.getMessage();
                if (!anon.contains("already exists")) {
                    if (anon.contains("You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near")) {
                        anon = anon.substring(133);
                        anon = "error at" + anon;
                    }
                    System.err.println(anon);
                    System.err.println("ERROR AT" +sql);
                }
            }
        });
    }

    /*
           @autor Yizhen Yuan
           @param the File contains SQL commands
    */
    static void run(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String anon = bufferedReader.readLine(); anon != null; anon = bufferedReader.readLine())
                stringBuilder.append(anon);
            run(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
         @autor Yizhen Yuan
         @param the command you are going to execute in SQL server
         Does not support multiple commands since there would be multiple ResultSet
     */
    static ResultSet getResult(String command) {
        assert command != null;
        try {
            return statement.executeQuery(command);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    /*
        @author Yizhen Yuan
        @param the ResultSet you get from getResult
        @see getResult
        @return An array consist of all columns name
        This should not be used in real Application
     */
    static String[] getColumns(ResultSet resultSet) throws SQLException {
        assert resultSet != null;
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        String[] answer = new String[resultSetMetaData.getColumnCount()];
        IntStream.range(0, answer.length).forEach(e -> {
            try {
                answer[e] = resultSetMetaData.getColumnName(e + 1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        return answer;
    }
    public static void main(String[] args) throws SQLException {
        ResultSet temp=getResult("Select * from Student;");
        Arrays.stream(getColumns(temp)).forEach(System.out::println);

        while (temp.next()){
            System.out.println(temp.getObject(1));
        }


        if(bugged){
            System.out.println("There is still a long way to go...");
        }else {
            System.out.println("Dataset initialized!");
        }
    }
}
