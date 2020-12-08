import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Initializer initializer = new Initializer();
        Prepare prepare = new Prepare(initializer);

        if (initializer.option.equals("Student")) {
            //initializer.setUsername("yuan226");
            prepare.prepareStudent();
            prepare.setVariables("@username," + initializer.getUsername());

            initializer.setName(initializer.addSpaces(initializer.getQuery().getTableColumn("SELECT name FROM Student WHERE student_id = \"" + initializer.getUsername() +  "\";", "name", "READ UNCOMMITTED").get(1)));

            ArrayList<ArrayList<String>> temp = initializer.getQuery().getTable("EXECUTE setCourses USING @username;", "READ UNCOMMITTED");

            initializer.setCourses(temp);
            for (int i = 1; i < initializer.getCourses().get(0).size(); i++) {
                prepare.setVariables("@major" + i + "," + initializer.getCourses().get(0).get(i) + ";@id" + i + "," + initializer.getCourses().get(1).get(i));
            }

            for (int i = 1; i < initializer.getCourses().get(0).size(); i++) {
                temp = initializer.getQuery().getTable("EXECUTE setFriendsCourse USING @username,@major" + i + ",@id" + i + ";", "READ UNCOMMITTED");
                if (temp == null) {
                    initializer.getFriendsCourse().add(null);
                } else {
                    for (int j = 0; j < temp.size(); j++) {
                        initializer.getFriendsCourse().add(temp.get(j));
                    }
                }

                temp = initializer.getQuery().getTable("EXECUTE setGradesCourse USING @username,@major" + i + ",@id" + i + ";", "READ UNCOMMITTED");
                if (temp == null) {
                    initializer.getGradesCourse().add(null);
                } else {
                    for (int j = 0; j < temp.size(); j++) {
                        initializer.getGradesCourse().add(temp.get(j));
                    }
                }

                temp = initializer.getQuery().getTable("EXECUTE setDueDatesAssignments USING @major" + i + ",@id" + i + ";", "READ UNCOMMITTED");
                if (temp == null) {
                    initializer.getDueDatesAssignments().add(null);
                } else {
                    for (int j = 0; j < temp.size(); j++) {
                        initializer.getDueDatesAssignments().add(temp);
                    }
                }
            }

            temp = initializer.getQuery().getTable("EXECUTE setFriendList USING @username;", "READ UNCOMMITTED");
            if (temp == null) {
                initializer.setFriendList(null);
                initializer.setFriendListClasses(null);
            } else {
                initializer.setFriendList(temp.get(0));
                for (int i = 1; i < initializer.getFriendList().size(); i++) {
                    prepare.setVariables("@friend" + i + "," + initializer.addSpaces(initializer.getFriendList().get(i)));
                    temp = initializer.getQuery().getTable("EXECUTE setFriendListClasses USING @friend" + i + ";", "READ UNCOMMITTED");
                    initializer.getFriendListClasses().add(temp);
                }
            }

            temp = initializer.getQuery().getTable("EXECUTE findFriends USING @username,@username;", "READ UNCOMMITTED");
            if (temp == null) {
                initializer.setNewFriends(null);
            } else {
                initializer.setNewFriends(temp);
            }
            StudentDriver studentDriver = new StudentDriver(initializer);
            prepare.deallocateStudent();
        } else {
            initializer.setUsername("hbenotma");
            prepare.prepareProfessor();
            prepare.setVariables("@username," + initializer.getUsername());
            initializer.setName(initializer.addSpaces(initializer.getQuery().getTableColumn("SELECT name FROM Professor WHERE professor_id = \"" + initializer.getUsername() + "" + "\";", "name", "READ UNCOMMITTED").get(1)));
            ArrayList<ArrayList<String>> temp = initializer.getQuery().getTable("SELECT major, id FROM Professor NATURAL JOIN Teaches NATURAL JOIN Course WHERE professor_id = \"" + initializer.getUsername() + "\";", "READ UNCOMMITTED");
            if (temp == null) {
                initializer.setProfessorCourses(null);
                initializer.setTas(null);
            }
            else {
                initializer.setProfessorCourses(temp);
                for (int i = 1; i < initializer.getProfessorCourses().get(0).size(); i++) {
                    prepare.setVariables("@major" + i + "," + initializer.getProfessorCourses().get(0).get(i) + ";@id" + i + "," + initializer.getProfessorCourses().get(1).get(i));
                }

                for (int i = 1; i < initializer.getProfessorCourses().get(0).size(); i++) {
                    temp = initializer.getQuery().getTable("EXECUTE findTas USING @major" + i + ",@id" + i + ";", "READ UNCOMMITTED");
                    initializer.getTas().add(temp);

                    temp = initializer.getQuery().getTable("EXECUTE getEligibleTas USING @major" + i + ",@id" + i + ";", "READ UNCOMMITTED");
                    initializer.getEligibleTas().add(temp);

                    temp = initializer.getQuery().getTable("EXECUTE getCrns USING @major" + i + ",@id" + i + ";", "READ UNCOMMITTED");
                    initializer.setCrns(temp.get(0));
                }
            }

            ProfessorDriver professorDriver = new ProfessorDriver(initializer);
            prepare.deallocateProfessor();
        }
    }
}
