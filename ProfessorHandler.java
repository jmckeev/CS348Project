public class ProfessorHandler {
    private ProfessorDriver professorDriver;
    private Initializer initializer;

    public ProfessorHandler(ProfessorDriver driver, Initializer initializer) {
        this.professorDriver = driver;
        this.initializer = initializer;
    }

    public void handle(ProfessorDriver.PROFESSOR_STATE state) {
        if (state == ProfessorDriver.PROFESSOR_STATE.taList) {
            TaList taList = new TaList(this);
        } else if (state == ProfessorDriver.PROFESSOR_STATE.add) {

        } else if (state == ProfessorDriver.PROFESSOR_STATE.remove) {

        }
    }

    public Initializer getInitializer() {
        return this.initializer;
    }
}
