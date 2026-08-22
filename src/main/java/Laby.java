import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Laby {
    private final Storage storage;
    private final Ui ui;
    private final TaskList taskList;

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
            this.taskList = new TaskList(tempTasks);
        }
    }

    private void markTask(int taskId) throws LabyException {
        this.taskList.markTask(taskId);
        this.ui.displayMarkTask(this.taskList, taskId);
        this.storage.writeTasksToFile(this.taskList);
    }

    private void unmarkTask(int taskId) throws LabyException {
        this.taskList.unmarkTask(taskId);
        this.ui.displayUnmarkTask(this.taskList, taskId);
        this.storage.writeTasksToFile(this.taskList);
    }

    private void deleteTask(int taskId) throws LabyException {
        this.taskList.deleteTask(taskId);
        this.ui.displayDeleteTask(this.taskList, taskId);
        this.ui.displayNumberOfTasks(this.taskList.size());
        this.storage.writeTasksToFile(this.taskList);
    }

    private void addTodo(String description) throws LabyException {
        this.taskList.addTodo(description);
        this.ui.displayAddTask(this.taskList);
        this.ui.displayNumberOfTasks(this.taskList.size());
        this.storage.writeTasksToFile(this.taskList);
    }

    private void addDeadline(String description, LocalDateTime deadline) throws LabyException {
        this.taskList.addDeadline(description, deadline);
        this.ui.displayAddTask(this.taskList);
        this.ui.displayNumberOfTasks(this.taskList.size());
        this.storage.writeTasksToFile(this.taskList);
    }

    private void addEvent(String description, LocalDateTime startTime, LocalDateTime endTime) throws LabyException {
        this.taskList.addEvent(description, startTime, endTime);
        this.ui.displayAddTask(this.taskList);
        this.ui.displayNumberOfTasks(this.taskList.size());
        this.storage.writeTasksToFile(this.taskList);
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
                        this.ui.displayTasks(this.taskList);
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
