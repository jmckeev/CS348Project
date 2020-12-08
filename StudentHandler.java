public class StudentHandler {
    private StudentDriver studentDriver;
    private Initializer initializer;

    public StudentHandler(StudentDriver driver, Initializer initializer) {
        this.studentDriver = driver;
        this.initializer = initializer;
    }

    public void handle(StudentDriver.STUDENT_STATE state) {
        if (state == StudentDriver.STUDENT_STATE.userClasses) {
            UserClasses userClasses = new UserClasses(this);
        } else if (state == StudentDriver.STUDENT_STATE.friendSchedule) {
            FriendSchedule friendSchedule = new FriendSchedule(this);
        } else if (state == StudentDriver.STUDENT_STATE.add) {
            Add add = new Add(this);
        } else if (state == StudentDriver.STUDENT_STATE.remove) {
            Remove remove = new Remove(this);
        } else if (state == StudentDriver.STUDENT_STATE.updateName) {
            Update update = new Update(this);
            this.studentDriver.resetName();
        }
    }

    public Initializer getInitializer() {
        return this.initializer;
    }
}
