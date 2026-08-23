package laby.task;

import laby.LabyException;

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

    public String markTask(int index) throws LabyException {
        try {
            Task task = this.tasks.get(index);
            task.setDone(true);
            return task.toString();
        } catch (IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    public String unmarkTask(int index) throws LabyException {
        try {
            Task task = this.tasks.get(index);
            task.setDone(false);
            return task.toString();
        } catch (IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    public String deleteTask(int index) throws LabyException {
        try {
            Task task = this.tasks.get(index);
            this.tasks.remove(index);
            return task.toString();
        } catch (IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    public String addTodo(String description) {
        Task task = new Todo(description);
        this.tasks.add(task);
        return task.toString();
    }

    public String addDeadline(String description, LocalDateTime deadline) {
        Task task = new Deadline(description, deadline);
        this.tasks.add(task);
        return task.toString();
    }

    public String addEvent(String description, LocalDateTime startTime, LocalDateTime endTime) {
        Task task = new Event(description, startTime, endTime);
        this.tasks.add(task);
        return task.toString();
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
