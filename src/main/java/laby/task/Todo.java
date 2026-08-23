package laby.task;

/** Represents a task without a deadline or event period. */
public class Todo extends Task {
    /**
     * Creates an incomplete todo task.
     *
     * @param description Description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Creates a todo task with the supplied completion state.
     *
     * @param description Description of the task.
     * @param isDone Whether the task is complete.
     */
    public Todo(String description, Boolean isDone) {
        super(description, isDone);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the todo in the format used for persistence.
     */
    @Override
    public String toFileString() {
        return "T|" + super.toFileString() + "\n";
    }
}
