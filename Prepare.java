public class Prepare {
    private Initializer initializer;

    public Prepare(Initializer initializer) {
        this.initializer = initializer;
    }

    public void prepareStudent() {
        this.initializer.getQuery().prepare("setCourses", "SELECT major, id FROM Course NATURAL JOIN Takes WHERE student_id = ?", "READ UNCOMMITTED");
        this.initializer.getQuery().prepare("setFriendsCourse", "SELECT name FROM Friend JOIN Student ON target = student_id WHERE main = ? AND target IN (SELECT student_id FROM Course NATURAL JOIN Takes WHERE major = ? AND id = ?", "READ UNCOMMITTED");
        this.initializer.getQuery().prepare("setGradesCourse", "SELECT grade FROM Course NATURAL JOIN Takes WHERE student_id = ? AND major = ? AND id = ?", "READ UNCOMMITTED");
        this.initializer.getQuery().prepare("setDueDatesAssignments", "SELECT description, due_date FROM Assignment NATURAL JOIN Course WHERE major = ? AND id = ?", "READ UNCOMMITTED");
        this.initializer.getQuery().prepare("setFriendList", "SELECT name FROM Student JOIN Friend ON target = student_id WHERE main = ?", "READ UNCOMMITTED");
        this.initializer.getQuery().prepare("setFriendListClasses", "SELECT major, id FROM Student NATURAL JOIN Takes NATURAL JOIN Course WHERE name = ?", "READ UNCOMMITTED");
        this.initializer.getQuery().prepare("findFriends", "SELECT name, student_id FROM Student WHERE student_id != ? AND student_id NOT IN (SELECT target FROM Friend WHERE main = ?)", "READ UNCOMMITTED");;
    }

    public void setVariables(String variables) {
        this.initializer.getQuery().setVariables(variables, "READ UNCOMMITTED");
    }

    public void deallocateStudent() {
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE setCourses;", "READ UNCOMMITTED");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE setFriendsCourse;", "READ UNCOMMITTED");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE setGradesCourse;", "READ UNCOMMITTED");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE setDueDatesAssignments;", "READ UNCOMMITTED");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE setFriendList;", "READ UNCOMMITTED");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE setFriendListClasses;", "READ UNCOMMITTED");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE findFriends;", "READ UNCOMMITTED");
    }

    public void prepareProfessor() {
        this.initializer.getQuery().prepare("getCourses", "SELECT major, id FROM Professor NATURAL JOIN Teaches NATURAL JOIN Course WHERE professor_id = ?", "READ UNCOMMITTED");
        this.initializer.getQuery().prepare("findTas", "SELECT student_id, name FROM Student NATURAL JOIN TA NATURAL JOIN Course WHERE major = ? AND id = ?", "READ UNCOMMITTED");
        this.initializer.getQuery().prepare("getEligibleTas", "SELECT student_id, name FROM Student NATURAL JOIN Takes NATURAL JOIN Course WHERE grade = \"A\" AND major = ? AND id = ?", "READ UNCOMMITTED");
        this.initializer.getQuery().prepare("getCrns", "SELECT CRN FROM Course WHERE major = ? AND id = ?", "READ UNCOMMITTED");
    }

    public void deallocateProfessor() {
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE getCourses;", "READ UNCOMMITTED");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE findTas;", "READ UNCOMMITTED");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE getEligibleTas", "READ UNCOMMITTED");
    }
}
