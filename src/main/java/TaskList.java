import java.time.LocalDateTime;
import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return this.tasks.size();
    }

    public void markTask(int index) throws LabyException {
        try {
            this.tasks.get(index).markAsDone();
        } catch (IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    public void unmarkTask(int index) throws LabyException {
        try {
            this.tasks.get(index).markAsUndone();
        } catch (IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    public void deleteTask(int index) throws LabyException {
        try {
            this.tasks.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    public void addTodo(String description) {
        Task task = new Todo(description);
        this.tasks.add(task);
    }

    public void addDeadline(String description, LocalDateTime deadline) {
        Task task = new Deadline(description, deadline);
        this.tasks.add(task);
    }

    public void addEvent(String description, LocalDateTime startTime, LocalDateTime endTime) {
        Task task = new Event(description, startTime, endTime);
        this.tasks.add(task);
    }

    public String taskToString(int index) {
        return this.tasks.get(index).toString();
    }

    public String lastTaskToString() {
        return this.taskToString(this.tasks.size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            result.append((i + 1)).append(".").append(this.tasks.get(i).toString()).append("\n");
        }
        return result.toString();
    }

    public String toFileString() {
        StringBuilder result = new StringBuilder();
        for (Task task : this.tasks) {
            result.append(task.toFileString());
        }
        return result.toString();
    }
}
