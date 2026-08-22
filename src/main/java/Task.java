public class Task {
    private final String description;
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

    public void markAsUndone() {
        this.done = false;
    }

    public String toFileString(){
        return (this.done ? "1|" : "0|") + this.description;
    }
}
