package laby.task;

public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "] " + this.description;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String toFileString() {
        return (this.isDone ? "1|" : "0|") + this.description;
    }
}
