package laby;

import laby.command.Command;
import laby.task.Task;
import laby.task.TaskList;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    private void markTask(int taskId) throws LabyException {
        Ui.displayMarkTask(this.taskList.markTask(taskId));
        this.storage.writeFile(this.taskList);
    }

    /**
     * Unmarks a task, displays the result, and saves the updated list.
     *
     * @param taskId Zero-based index of the task to unmark.
     * @throws LabyException If the task does not exist or cannot be saved.
     */
    private void unmarkTask(int taskId) throws LabyException {
        Ui.displayUnmarkTask(this.taskList.unmarkTask(taskId));
        this.storage.writeFile(this.taskList);
    }

    /**
     * Deletes a task, displays the result, and saves the updated list.
     *
     * @param taskId Zero-based index of the task to delete.
     * @throws LabyException If the task does not exist or cannot be saved.
     */
    private void deleteTask(int taskId) throws LabyException {
        Ui.displayDeleteTask(this.taskList.deleteTask(taskId));
        Ui.displayNumberOfTasks(this.taskList.size());
        this.storage.writeFile(this.taskList);
    }

    /**
     * Adds a todo, displays the result, and saves the updated list.
     *
     * @param description Description of the todo task.
     * @throws LabyException If the task cannot be saved.
     */
    private void addTodo(String description) throws LabyException {
        Ui.displayAddTask(this.taskList.addTodo(description));
        Ui.displayNumberOfTasks(this.taskList.size());
        this.storage.writeFile(this.taskList);
    }

    /**
     * Adds a deadline, displays the result, and saves the updated list.
     *
     * @param description Description of the deadline task.
     * @param deadline Time by which the task should be completed.
     * @throws LabyException If the task cannot be saved.
     */
    private void addDeadline(String description, LocalDateTime deadline) throws LabyException {
        Ui.displayAddTask(this.taskList.addDeadline(description, deadline));
        Ui.displayNumberOfTasks(this.taskList.size());
        this.storage.writeFile(this.taskList);
    }

    /**
     * Adds an event, displays the result, and saves the updated list.
     *
     * @param description Description of the event task.
     * @param startTime Start of the event.
     * @param endTime End of the event.
     * @throws LabyException If the task cannot be saved.
     */
    private void addEvent(String description, LocalDateTime startTime, LocalDateTime endTime) throws LabyException {
        Ui.displayAddTask(this.taskList.addEvent(description, startTime, endTime));
        Ui.displayNumberOfTasks(this.taskList.size());
        this.storage.writeFile(this.taskList);
    }

    /**
     * Filters tasks based on input.
     *
     * @param input Input which tasks are filtered by.
     */
    private void filterTasks(String input) {
        Ui.displayFilteredTasks(this.taskList, input);
    }

    /**
     * Starts the command loop and processes input until the user exits.
     */
    public void run() {
        Ui.displayWelcomeBanner();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            try {
                Command command = Parser.parseInput(input);
                switch (command.getCommandType()) {
                    case BYE:
                        Ui.displayExitMessage();
                        System.exit(0);
                        break;
                    case LIST:
                        Ui.displayTasks(this.taskList);
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
                    case FIND:
                        this.filterTasks(command.getDescription());
                        break;
                }
            } catch (LabyException e) {
                Ui.displayError(e);
            }
        }
    }

    /**
     * Starts laby using its default data file.
     *
     * @param args Unused command-line arguments.
     */
    static void main(String[] args) {
        new Laby(Paths.get("data", "laby.txt").toString()).run();
    }
}
