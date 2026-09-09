package laby;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import laby.command.Command;
import laby.command.CommandType;
import laby.contact.ContactList;
import laby.task.Task;
import laby.task.TaskList;

/** Coordinates input parsing, task operations, storage, and console output. */
public class Laby {
    private final Storage storage;
    private final TaskList taskList;
    private final ContactList contactList;

    /**
     * Creates an application instance and loads tasks from the given file.
     *
     * @param filePath Path of the task data file.
     */
    public Laby(String filePath) {
        this.storage = new Storage(filePath);
        this.contactList = new ContactList(new ArrayList<>());
        List<Task> tempTasks;

        try {
            tempTasks = this.storage.readTasks();
        } catch (LabyException e) {
            System.out.print(Ui.getReadFileError(e));
            tempTasks = new ArrayList<>();
        }

        this.taskList = new TaskList(tempTasks);
    }

    /**
     * Modifies a task's completion status, displays the result, and saves the updated list.
     *
     * @param command Command containing the zero-based task index and requested status change.
     * @throws LabyException If the task does not exist or cannot be saved.
     */
    private String modifyTaskStatus(Command command) throws LabyException {
        boolean isDone = switch (command.getCommandType()) {
            case MARK -> true;
            case UNMARK -> false;
            default -> throw new LabyException("invalid command.");
        };

        String task = this.taskList.modifyTaskStatus(command.getId(), isDone);
        this.saveTasks();
        return isDone ? Ui.getMarkTask(task) : Ui.getUnmarkTask(task);
    }

    /**
     * Saves a task-list change and formats the corresponding add or delete response.
     *
     * @param taskText Display text of the changed task.
     * @return Formatted response describing the change and current task count.
     * @throws LabyException If the updated task list cannot be saved.
     */
    private String addTask(String taskText) throws LabyException {
        this.saveTasks();
        return Ui.getAddTask(taskText) + numberOfTasksMessage();
    }

    /**
     * Deletes a task, displays the result, and saves the updated list.
     *
     * @param taskId Zero-based index of the task to delete.
     * @throws LabyException If the task does not exist or cannot be saved.
     */
    private String deleteTask(int taskId) throws LabyException {
        String task = this.taskList.deleteTask(taskId);
        this.saveTasks();
        return Ui.getDeleteTask(task) + numberOfTasksMessage();
    }

    /**
     * Adds a todo, displays the result, and saves the updated list.
     *
     * @param description Description of the todo task.
     * @throws LabyException If the task cannot be saved.
     */
    private String addTodo(String description) throws LabyException {
        String task = this.taskList.addTodo(description);
        return this.addTask(task);
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
        return this.addTask(task);
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
        return this.addTask(task);
    }

    /**
     * Filters tasks based on input.
     *
     * @param input Input which tasks are filtered by.
     */
    private String filterTasks(String input) {
        return Ui.getFilteredTasks(this.taskList, input);
    }

    /**
     * Returns the formatted task-count message used after task additions and deletions.
     *
     * @return Formatted task-count message.
     */
    private String numberOfTasksMessage() {
        return Ui.getNumberOfTasks(this.taskList.size());
    }

    /**
     * Persists the current task list.
     *
     * @throws LabyException If the task list cannot be saved.
     */
    private void saveTasks() throws LabyException {
        this.storage.writeFile(this.taskList, this.contactList);
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
            assert command != null : "parser must return a command";
            assert command.getCommandType() != null && command.getCommandType() != CommandType.UNKNOWN
                    : "parser must return a supported command";
            return switch (command.getCommandType()) {
                case BYE -> Ui.getExitMessage();
                case LIST -> Ui.getTasks(this.taskList);
                case MARK, UNMARK -> this.modifyTaskStatus(command);
                case DELETE -> this.deleteTask(command.getId());
                case TODO -> this.addTodo(command.getDescription());
                case DEADLINE -> this.addDeadline(command.getDescription(), command.getFirstTime());
                case EVENT -> this.addEvent(command.getDescription(), command.getFirstTime(), command.getSecondTime());
                case FIND -> this.filterTasks(command.getDescription());
                default -> throw new LabyException("invalid command.");
            };
        } catch (LabyException e) {
            return Ui.getError(e);
        }
    }

    /**
     * Starts the command loop and processes input until the user exits.
     */
    public void run() {
        System.out.print(Ui.getWelcomeBanner());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            String response = executeCommand(input);
            System.out.print(Ui.addDivider(response));
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
                System.out.print(Ui.getReadFileError(new LabyException("cannot create temporary test file")));
            }
        }
        new Laby(filePath).run();
    }
}
