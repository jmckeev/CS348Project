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
            AddTa addTa = new AddTa(this);
        } else if (state == ProfessorDriver.PROFESSOR_STATE.remove) {
            RemoveTa removeTa = new RemoveTa(this);
        } else if (state == ProfessorDriver.PROFESSOR_STATE.updateName) {
            UpdateProfessor updateProfessor = new UpdateProfessor(this);
            this.professorDriver.resetName();
        }
    }

    public Initializer getInitializer() {
        return this.initializer;
    }
}
