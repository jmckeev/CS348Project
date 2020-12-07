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
            //TODO: write FriendSchedule class (GUI) and instantiate an object here
        } else if (state == StudentDriver.STUDENT_STATE.addRemove) {
            //TODO: write FriendSchedule class (GUI) and instantiate an object here
        }
    }

    public Initializer getInitializer() {
        return this.initializer;
    }
}
