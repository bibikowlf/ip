package laby.task;

/** Represents a task description and whether the task is complete. */
public class Task {
    private final String description;
    private boolean done;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    /**
     * Creates a task with the given description and completion state.
     *
     * @param description Description of the task.
     * @param done Whether the task is complete.
     */
    public Task(String description, Boolean done) {
        this.description = description;
        this.done = done;
    }

    /**
     * Returns the task in the format shown in the console.
     */
    @Override
    public String toString() {
        return "[" + (this.done ? "X" : " ") + "] " + this.description;
    }

    /**
     * Marks this task as complete.
     */
    public void markAsDone() {
        this.done = true;
    }

    /**
     * Marks this task as incomplete.
     */
    public void markAsUndone() {
        this.done = false;
    }

    /**
     * Returns the task in the format used for persistence.
     *
     * @return Serialized representation of the task.
     */
    public String toFileString(){
        return (this.done ? "1|" : "0|") + this.description;
    }
}
