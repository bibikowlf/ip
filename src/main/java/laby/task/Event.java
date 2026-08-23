package laby.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Represents a task occurring between a start time and an end time. */
public class Event extends Task {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    /**
     * Creates an incomplete event task.
     *
     * @param description Description of the task.
     * @param startTime Start of the event.
     * @param endTime End of the event.
     */
    public Event(String description, LocalDateTime startTime, LocalDateTime endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Creates an event task with the supplied completion state.
     *
     * @param description Description of the task.
     * @param startTime Start of the event.
     * @param endTime End of the event.
     * @param isDone Whether the task is complete.
     */
    public Event(String description, LocalDateTime startTime, LocalDateTime endTime, boolean isDone) {
        super(description, isDone);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the event in the format shown in the console.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
        return "[E]" + super.toString() + " (from: " + this.startTime.format(formatter)
                + " to: " + this.endTime.format(formatter) + ")";
    }

    /**
     * Returns the event in the format used for persistence.
     */
    @Override
    public String toFileString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "E|" + super.toFileString() + "|" + this.startTime.format(formatter)
                + "|" + this.endTime.format(formatter) + "\n";
    }
}
