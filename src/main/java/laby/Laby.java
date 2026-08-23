package laby;

import laby.command.Command;
import laby.task.Task;
import laby.task.TaskList;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Laby {
    private final Storage storage;
    private final TaskList taskList;

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

    private void markTask(int taskId) throws LabyException {
        Ui.displayMarkTask(this.taskList.markTask(taskId));
        this.storage.writeFile(this.taskList);
    }

    private void unmarkTask(int taskId) throws LabyException {
        Ui.displayUnmarkTask(this.taskList.unmarkTask(taskId));
        this.storage.writeFile(this.taskList);
    }

    private void deleteTask(int taskId) throws LabyException {
        Ui.displayDeleteTask(this.taskList.deleteTask(taskId));
        Ui.displayNumberOfTasks(this.taskList.size());
        this.storage.writeFile(this.taskList);
    }

    private void addTodo(String description) throws LabyException {
        Ui.displayAddTask(this.taskList.addTodo(description));
        Ui.displayNumberOfTasks(this.taskList.size());
        this.storage.writeFile(this.taskList);
    }

    private void addDeadline(String description, LocalDateTime deadline) throws LabyException {
        Ui.displayAddTask(this.taskList.addDeadline(description, deadline));
        Ui.displayNumberOfTasks(this.taskList.size());
        this.storage.writeFile(this.taskList);
    }

    private void addEvent(String description, LocalDateTime startTime, LocalDateTime endTime) throws LabyException {
        Ui.displayAddTask(this.taskList.addEvent(description, startTime, endTime));
        Ui.displayNumberOfTasks(this.taskList.size());
        this.storage.writeFile(this.taskList);
    }

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
                }
            } catch (LabyException e) {
                Ui.displayError(e);
            }
        }
    }

    static void main(String[] args) {
        new Laby(Paths.get("data", "laby.txt").toString()).run();
    }
}
