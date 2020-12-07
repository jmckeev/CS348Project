public class Prepare {
    private Initializer initializer;

    public Prepare(Initializer initializer) {
        this.initializer = initializer;
    }

    public void prepareAll() {
        this.initializer.getQuery().prepare("setCourses", "SELECT major, id FROM Course NATURAL JOIN Takes WHERE student_id = ?");
        this.initializer.getQuery().prepare("setFriendsCourse", "SELECT name FROM Friend JOIN Student ON target = student_id WHERE main = ? AND target IN (SELECT student_id FROM Course NATURAL JOIN Takes WHERE major = ? AND id = ?");
        this.initializer.getQuery().prepare("setGradesCourse", "SELECT grade FROM Course NATURAL JOIN Takes WHERE student_id = ? AND major = ? AND id = ?");
        this.initializer.getQuery().prepare("setDueDatesAssignments", "SELECT description, due_date FROM Assignment NATURAL JOIN Course WHERE major = ? AND id = ?");
        this.initializer.getQuery().prepare("setFriendList", "SELECT name FROM Student JOIN Friend ON target = student_id WHERE main = ?");
        this.initializer.getQuery().prepare("setFriendListClasses", "SELECT major, id FROM Student NATURAL JOIN Takes NATURAL JOIN Course WHERE name = ?");
        this.initializer.getQuery().prepare("findFriends", "SELECT name, student_id FROM Student WHERE student_id != ? AND student_id NOT IN (SELECT target FROM Friend WHERE main = ?)");
    }

    public void setVariables(String variables) {
        this.initializer.getQuery().setVariables(variables);
    }

    public void deallocateAll() {
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE setCourses");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE setFriendsCourse");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE setGradesCourse");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE setDueDatesAssignments");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE setFriendList");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE setFriendListClasses");
        this.initializer.getQuery().sendQuery("DEALLOCATE PREPARE findFriends");
    }
}
