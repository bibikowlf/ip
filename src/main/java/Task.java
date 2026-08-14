public class Task {
    private String description;
    private boolean done;

    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    @Override
    public String toString() {
        return "[" + (this.done ? "X" : " ") + "] " + this.description;
    }

    public void markAsDone() {
        this.done = true;
    }
}
