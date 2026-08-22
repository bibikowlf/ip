import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Laby {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final List<Task> tasks;
    private final Storage storage;
    private final Ui ui;

    public Laby(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        List<Task> tempTasks = new ArrayList<>();

        try {
            tempTasks = this.storage.readTasksFromFile();
        } catch (LabyException e) {
            this.ui.displayReadFileError(e);
            tempTasks = new ArrayList<>();
        } finally {
            this.tasks = tempTasks;
        }
    }

    private void markTask(String input) throws LabyException {
        try {
            int taskId = Integer.parseInt(input.substring(5)) - 1;
            this.tasks.get(taskId).markAsDone();
            this.ui.displayMarkTask(this.tasks, taskId);
            this.storage.writeTasksToFile(this.tasks);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    private void unmarkTask(String input) throws LabyException {
        try {
            int taskId = Integer.parseInt(input.substring(7)) - 1;
            this.tasks.get(taskId).markAsUndone();
            this.ui.displayUnmarkTask(this.tasks, taskId);
            this.storage.writeTasksToFile(this.tasks);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    private void deleteTask(String input) throws LabyException {
        try {
            int taskId = Integer.parseInt(input.substring(7)) - 1;
            this.tasks.remove(taskId);
            this.ui.displayDeleteTask(this.tasks, taskId);
            this.ui.displayNumberOfTasks(this.tasks.size());
            this.storage.writeTasksToFile(this.tasks);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    private void addTodo(String input) throws LabyException {
        String description = input.substring(5);
        if (description.trim().isEmpty()) {
            throw new LabyException("task description cannot be empty.");
        }
        Task task = new Todo(description);
        this.tasks.add(task);
        this.ui.displayAddTask(this.tasks, this.tasks.size() - 1);
        this.ui.displayNumberOfTasks(this.tasks.size());
        this.storage.writeTasksToFile(this.tasks);
    }

    private void addDeadline(String input) throws LabyException {
        try {
            int deadlineIndex = input.indexOf(" /by ");
            if (deadlineIndex == -1) {
                throw new LabyException("please enter a deadline with /by.");
            } else if (deadlineIndex < 9) {
                throw new LabyException("task description cannot be empty.");
            }
            String description = input.substring(9, deadlineIndex);
            if (description.trim().isEmpty()) {
                throw new LabyException("task description cannot be empty.");
            }
            LocalDateTime deadline = LocalDateTime.parse(input.substring(deadlineIndex + 5).trim(), formatter);
            Task task = new Deadline(description, deadline);
            this.tasks.add(task);
            this.ui.displayAddTask(this.tasks, this.tasks.size() - 1);
            this.ui.displayNumberOfTasks(this.tasks.size());
            this.storage.writeTasksToFile(this.tasks);
        } catch (NumberFormatException e) {
            throw new LabyException("time format must be yyyy-MM-dd HH:mm");
        }
    }

    private void addEvent(String input) throws LabyException {
        try {
            int startIndex = input.indexOf(" /from ");
            if (startIndex == -1) {
                throw new LabyException("please enter a starting time with /from.");
            } else if (startIndex < 6) {
                throw new LabyException("task description cannot be empty.");
            }
            int endIndex = input.indexOf(" /to ");
            if (endIndex == -1) {
                throw new LabyException("please enter an ending time with /to.");
            } else if (endIndex < startIndex + 7) {
                throw new LabyException("task starting time cannot be empty.");
            }
            String description = input.substring(6, startIndex);
            if (description.trim().isEmpty()) {
                throw new LabyException("task description cannot be empty.");
            }
            LocalDateTime startTime = LocalDateTime.parse(input.substring(startIndex + 7, endIndex).trim(), formatter);
            LocalDateTime endTime = LocalDateTime.parse(input.substring(endIndex + 5).trim(), formatter);
            Task task = new Event(description, startTime, endTime);
            this.tasks.add(task);
            this.ui.displayAddTask(this.tasks, this.tasks.size() - 1);
            this.ui.displayNumberOfTasks(this.tasks.size());
            this.storage.writeTasksToFile(this.tasks);
        } catch (DateTimeParseException e) {
            throw new LabyException("time format must be yyyy-MM-dd HH:mm");
        }
    }

    public void run() {
        this.ui.displayWelcomeBanner();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            String[] parts = input.trim().split("\\s+", 2);
            CommandType command = CommandType.from(parts[0]);

            try {
                switch (command) {
                    case BYE:
                        if (parts.length != 1) {
                            throw new LabyException("please enter a valid command.");
                        }
                        this.ui.displayExitMessage();
                        break;
                    case LIST:
                        if (parts.length != 1) {
                            throw new LabyException("please enter a valid command.");
                        }
                        this.ui.displayTasks(this.tasks);
                        break;
                    case MARK:
                        if (parts.length != 2) {
                            throw new LabyException("please enter a valid task index.");
                        }
                        this.markTask(input);
                        break;
                    case UNMARK:
                        if (parts.length != 2) {
                            throw new LabyException("please enter a valid task index.");
                        }
                        this.unmarkTask(input);
                        break;
                    case DELETE:
                        if (parts.length != 2) {
                            throw new LabyException("please enter a valid task index.");
                        }
                        this.deleteTask(input);
                        break;
                    case TODO:
                        if (parts.length != 2) {
                            throw new LabyException("task description cannot be empty.");
                        }
                        this.addTodo(input);
                        break;
                    case DEADLINE:
                        if (parts.length != 2) {
                            throw new LabyException("task description cannot be empty.");
                        }
                        this.addDeadline(input);
                        break;
                    case EVENT:
                        if (parts.length != 2) {
                            throw new LabyException("task description cannot be empty.");
                        }
                        this.addEvent(input);
                        break;
                    case UNKNOWN:
                        throw new LabyException("please input the correct commands.");
                    default:
                        break;
                }
                if (input.equals("bye")) {
                    break;
                }
            } catch (LabyException e) {
                this.ui.displayError(e);
            }
        }
    }

    static void main(String[] args) {
        new Laby(Paths.get("data", "laby.txt").toString()).run();
    }
}
