package laby;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import laby.command.Command;
import laby.task.Task;
import laby.task.TaskList;

/** Coordinates input parsing, task operations, storage, and console output. */
public class Laby {
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Creates an application instance and loads tasks from the given file.
     *
     * @param filePath Path of the task data file.
     */
    public Laby(String filePath) {
        this.storage = new Storage(filePath);
        List<Task> tempTasks = new ArrayList<>();

        try {
            tempTasks = this.storage.readFile();
        } catch (LabyException e) {
            Ui.displayReadFileError(e);
            tempTasks = new ArrayList<>();
        } finally {
            this.taskList = new TaskList(tempTasks);
        }
    }

    /**
     * Marks a task, displays the result, and saves the updated list.
     *
     * @param taskId Zero-based index of the task to mark.
     * @throws LabyException If the task does not exist or cannot be saved.
     */
    private String markTask(int taskId) throws LabyException {
        String task = this.taskList.markTask(taskId);
        this.storage.writeFile(this.taskList);
        return Ui.displayMarkTask(task);
    }

    /**
     * Unmarks a task, displays the result, and saves the updated list.
     *
     * @param taskId Zero-based index of the task to unmark.
     * @throws LabyException If the task does not exist or cannot be saved.
     */
    private String unmarkTask(int taskId) throws LabyException {
        String task = this.taskList.unmarkTask(taskId);
        this.storage.writeFile(this.taskList);
        return Ui.displayUnmarkTask(task);
    }

    /**
     * Deletes a task, displays the result, and saves the updated list.
     *
     * @param taskId Zero-based index of the task to delete.
     * @throws LabyException If the task does not exist or cannot be saved.
     */
    private String deleteTask(int taskId) throws LabyException {
        String task = this.taskList.deleteTask(taskId);
        this.storage.writeFile(this.taskList);
        return Ui.displayDeleteTask(task) + numberOfTasksMessage();
    }

    /**
     * Adds a todo, displays the result, and saves the updated list.
     *
     * @param description Description of the todo task.
     * @throws LabyException If the task cannot be saved.
     */
    private String addTodo(String description) throws LabyException {
        String task = this.taskList.addTodo(description);
        this.storage.writeFile(this.taskList);
        return Ui.displayAddTask(task) + numberOfTasksMessage();
    }

    /**
     * Adds a deadline, displays the result, and saves the updated list.
     *
     * @param description Description of the deadline task.
     * @param deadline Time by which the task should be completed.
     * @throws LabyException If the task cannot be saved.
     */
    private String addDeadline(String description, LocalDateTime deadline) throws LabyException {
        String task = this.taskList.addDeadline(description, deadline);
        this.storage.writeFile(this.taskList);
        return Ui.displayAddTask(task) + numberOfTasksMessage();
    }

    /**
     * Adds an event, displays the result, and saves the updated list.
     *
     * @param description Description of the event task.
     * @param startTime Start of the event.
     * @param endTime End of the event.
     * @throws LabyException If the task cannot be saved.
     */
    private String addEvent(String description, LocalDateTime startTime, LocalDateTime endTime) throws LabyException {
        String task = this.taskList.addEvent(description, startTime, endTime);
        this.storage.writeFile(this.taskList);
        return Ui.displayAddTask(task) + numberOfTasksMessage();
    }

    /**
     * Filters tasks based on input.
     *
     * @param input Input which tasks are filtered by.
     */
    private String filterTasks(String input) {
        return Ui.displayFilteredTasks(this.taskList, input);
    }

    /**
     * Returns the formatted task-count message used after task additions and deletions.
     *
     * @return Formatted task-count message.
     */
    private String numberOfTasksMessage() {
        return Ui.displayNumberOfTasks(this.taskList.size());
    }

    /**
     * Parses and executes one command without directly writing to the console.
     *
     * @param input User-entered command.
     * @return Formatted response for the command.
     */
    public String executeCommand(String input) {
        try {
            Command command = Parser.parseInput(input);
            return switch (command.getCommandType()) {
                case BYE -> Ui.displayExitMessage();
                case LIST -> Ui.displayTasks(this.taskList);
                case MARK -> this.markTask(command.getId());
                case UNMARK -> this.unmarkTask(command.getId());
                case DELETE -> this.deleteTask(command.getId());
                case TODO -> this.addTodo(command.getDescription());
                case DEADLINE -> this.addDeadline(command.getDescription(), command.getFirstTime());
                case EVENT -> this.addEvent(command.getDescription(), command.getFirstTime(), command.getSecondTime());
                case FIND -> this.filterTasks(command.getDescription());
                default -> throw new LabyException("invalid command.");
            };
        } catch (LabyException e) {
            return Ui.displayError(e);
        }
    }

    /**
     * Starts the command loop and processes input until the user exits.
     */
    public void run() {
        System.out.print(Ui.displayWelcomeBanner());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            String response = executeCommand(input);
            System.out.print(response);
            if (input.trim().equalsIgnoreCase("bye")) {
                break;
            }
        }
    }

    /**
     * Starts laby using its default data file.
     *
     * @param args Unused command-line arguments.
     */
    static void main(String[] args) {
        String filePath = Paths.get("data", "laby.txt").toString();
        if (args.length == 1 && args[0].equals("--fresh")) {
            try {
                filePath = Files.createTempFile("laby-ui-test-", ".txt").toString();
            } catch (IOException e) {
                Ui.displayReadFileError(new LabyException("cannot create temporary test file"));
            }
        }
        new Laby(filePath).run();
    }
}
