import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Initializer initializer = new Initializer();
        //initializer.setCourses(initializer.getQuery().getTable("SELECT major, id, FROM Course NATURAL JOIN Takes WHERE student_id = \"jmckeev\""));
//        ArrayList<ArrayList<String>> temp = initializer.getQuery().getTable("SELECT * FROM Student;");
//        for (int i = 0; i < temp.size(); i++) {
//            for (int j = 0; j < temp.get(i).size(); j++) {
//                System.out.println(temp.get(i).get(j));
//            }
//        }
//
//        System.out.println("Break");
//
//        ArrayList<ArrayList<String>> temp2 = initializer.getQuery().getTable("SELECT * FROM Professor;");
//        for (int i = 0; i < temp2.size(); i++) {
//            for (int j = 0; j < temp2.get(i).size(); j++) {
//                System.out.println(temp2.get(i).get(j));
//            }
//        }


        ArrayList<ArrayList<String>> temp = initializer.getQuery().getTable("SELECT major, id FROM Course NATURAL JOIN Takes WHERE student_id = \"" + initializer.getUsername() + "\";");
        initializer.setCourses(temp);
        for (int i = 1; i < initializer.getCourses().get(0).size(); i++) {
            temp = initializer.getQuery().getTable("SELECT name FROM Friend JOIN Student ON target = student_id WHERE main = \"" + initializer.getUsername() + "\" AND target IN (SELECT student_id FROM Course NATURAL JOIN Takes WHERE major = \"" + initializer.getCourses().get(0).get(i) + "\" AND id = \"" + initializer.getCourses().get(1).get(i) + "\");");
            if (temp == null) {
                initializer.getFriendsCourse().add(null);
            } else {
                for (int j = 0; j < temp.size(); j++) {
                    System.out.println(temp.get(j));
                    initializer.getFriendsCourse().add(temp.get(j));
                }
            }

            temp = initializer.getQuery().getTable("SELECT grade FROM Course NATURAL JOIN Takes WHERE student_id = \"" + initializer.getUsername() + "\" AND major = \"" + initializer.getCourses().get(0).get(i) + "\" AND id = \"" + initializer.getCourses().get(1).get(i) + "\";");
            if (temp == null) {
                initializer.getGradesCourse().add(null);
            } else {
                for (int j = 0; j < temp.size(); j++) {
                    initializer.getGradesCourse().add(temp.get(j));
                }
            }

            temp = initializer.getQuery().getTable("SELECT description, due_date FROM Assignment NATURAL JOIN Course WHERE major = \"" + initializer.getCourses().get(0).get(i) + "\" AND id = \"" + initializer.getCourses().get(1).get(i) + "\";");
            if (temp == null) {
                initializer.getDueDatesAssignments().add(null);
            } else {
                for (int j = 0; j < temp.size(); j++) {
                    initializer.getDueDatesAssignments().add(temp);
                }
            }
        }

        //temp = initializer.getQuery().getTable("SELECT name FROM Student JOIN Friend ON target = student_id WHERE main = \"" + initializer.getUsername() + "\";");
        temp = initializer.getQuery().getTable("SELECT name FROM Student JOIN Friend ON target = student_id WHERE main = \"yuan226\";");
        if (temp == null) {
            initializer.setFriendList(null);
        } else {
            initializer.setFriendList(temp.get(0));
        }

        for (int i = 1; i < initializer.getFriendList().size(); i++) {
            System.out.println(initializer.getFriendList().get(i));
            temp = initializer.getQuery().getTable("SELECT major, id FROM Student NATURAL JOIN Takes NATURAL JOIN Course WHERE name = \"" + initializer.getFriendList().get(i) + "\";");
            initializer.getFriendListClasses().add(temp);
        }

        if (initializer.getFriendListClasses() != null) {
            for (int i = 0; i < initializer.getFriendListClasses().size(); i++) {
                if (initializer.getFriendListClasses().get(i) != null) {
                    for (int j = 0; j < initializer.getFriendListClasses().get(i).size(); j++) {
                        for (int k = 0; k < initializer.getFriendListClasses().get(i).get(j).size(); k++) {
                            System.out.println(initializer.getFriendListClasses().get(i).get(j).get(k));
                        }
                    }
                }
            }
        }

//        for (int i = 0; i < initializer.getFriendsCourse().size(); i++) {
//            for (int j = 0; j < initializer.getFriendsCourse().get(i).size(); j++) {
//                System.out.println(initializer.getFriendsCourse().get(i).get(j));
//            }
        //}


        //ArrayList<String> arrayList = initializer.getQuery().getTableColumn();
//        System.out.println("----------");
//        System.out.println(initializer.getQuery().sendQuery("SELECT * FROM Student;"));
//        System.out.println("----------");
//        ArrayList<String> list = initializer.getQuery().getColumns(initializer.getQuery().sendQuery("SELECT * FROM Student;"));
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
        //System.out.println(initializer.getQuery().getTable("SELECT * FROM Book;"));
        if (initializer.option.equals("Student")) {
            //ArrayList<String> arrayList = initializer.getQuery().getTableColumn("SELECT name FROM Student WHERE student_id = \"jmckeev\";", "name");
//            for (int i = 0; i < arrayList.size(); i++) {
//                System.out.println(arrayList.get(i));
//            }
            StudentDriver studentDriver = new StudentDriver(initializer);
        } else {

        }
        initializer.cleanup();
    }
}
