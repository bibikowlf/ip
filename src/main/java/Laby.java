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
    private final Parser parser;

    public Laby(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        this.parser = new Parser();
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

    private void markTask(int taskId) throws LabyException {
        try {
            this.tasks.get(taskId).markAsDone();
            this.ui.displayMarkTask(this.tasks, taskId);
            this.storage.writeTasksToFile(this.tasks);
        } catch (IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    private void unmarkTask(int taskId) throws LabyException {
        try {
            this.tasks.get(taskId).markAsUndone();
            this.ui.displayUnmarkTask(this.tasks, taskId);
            this.storage.writeTasksToFile(this.tasks);
        } catch (IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    private void deleteTask(int taskId) throws LabyException {
        try {
            this.tasks.remove(taskId);
            this.ui.displayDeleteTask(this.tasks, taskId);
            this.ui.displayNumberOfTasks(this.tasks.size());
            this.storage.writeTasksToFile(this.tasks);
        } catch (IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    private void addTodo(String description) throws LabyException {
        Task task = new Todo(description);
        this.tasks.add(task);
        this.ui.displayAddTask(this.tasks, this.tasks.size() - 1);
        this.ui.displayNumberOfTasks(this.tasks.size());
        this.storage.writeTasksToFile(this.tasks);
    }

    private void addDeadline(String description, LocalDateTime deadline) throws LabyException {
        Task task = new Deadline(description, deadline);
        this.tasks.add(task);
        this.ui.displayAddTask(this.tasks, this.tasks.size() - 1);
        this.ui.displayNumberOfTasks(this.tasks.size());
        this.storage.writeTasksToFile(this.tasks);
    }

    private void addEvent(String description, LocalDateTime startTime, LocalDateTime endTime) throws LabyException {
        Task task = new Event(description, startTime, endTime);
        this.tasks.add(task);
        this.ui.displayAddTask(this.tasks, this.tasks.size() - 1);
        this.ui.displayNumberOfTasks(this.tasks.size());
        this.storage.writeTasksToFile(this.tasks);
    }

    public void run() {
        this.ui.displayWelcomeBanner();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            try {
                Command command = Parser.parseInput(input);
                switch (command.getCommandType()) {
                    case BYE:
                        this.ui.displayExitMessage();
                        break;
                    case LIST:
                        this.ui.displayTasks(this.tasks);
                        break;
                    case MARK:
                        this.markTask(command.getId());
                        break;
                    case UNMARK:
                        this.unmarkTask(command.getId());
                        break;
                    case DELETE:
                        this.deleteTask(command.getId());
                        break;
                    case TODO:
                        this.addTodo(command.getDescription());
                        break;
                    case DEADLINE:
                        this.addDeadline(command.getDescription(), command.getFirstTime());
                        break;
                    case EVENT:
                        this.addEvent(command.getDescription(), command.getFirstTime(), command.getSecondTime());
                        break;
                }
                if (command.getCommandType() == CommandType.BYE) {
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
