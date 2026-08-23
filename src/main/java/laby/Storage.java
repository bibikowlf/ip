package laby;

import laby.task.Deadline;
import laby.task.Event;
import laby.task.Task;
import laby.task.TaskList;
import laby.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** Reads tasks from and writes tasks to the application's data file. */
public class Storage {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final String filePath;

    /**
     * Creates storage backed by the file at {@code filePath}.
     *
     * @param filePath Path of the task data file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Creates the data file and its parent directories when they do not exist.
     *
     * @throws LabyException If the file or its parent directories cannot be created.
     */
    private void createFile () throws LabyException {
        try {
            File file = new File(filePath);

            if (!file.exists()) {
                boolean success = true;
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    success = file.getParentFile().mkdirs();
                }
                if (!success || !file.createNewFile()) {
                    throw new LabyException("cannot create file");
                }
            }
        } catch (IOException e) {
            throw new LabyException("cannot create file");
        }
    }

    /**
     * Persists every task in the supplied task list.
     *
     * @param taskList Task list to persist.
     * @throws LabyException If the data file cannot be created or written.
     */
    public void writeTasksToFile(TaskList taskList) throws LabyException {
        try {
            this.createFile();
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(taskList.toFileString());
            }
        } catch (IOException e) {
            throw new LabyException("cannot write to file");
        }
    }

    /**
     * Loads all persisted tasks, validating their stored format.
     *
     * @return Tasks loaded from the data file.
     * @throws LabyException If the data file cannot be read or has invalid content.
     */
    public List<Task> readTasksFromFile() throws LabyException {
        List<Task> tasks = new ArrayList<>();

        try {
            this.createFile();
            File file = new File(filePath);
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String current = scanner.nextLine();
                    String[] parts = current.trim().split("\\|");
                    if (!parts[1].equals("0") && !parts[1].equals("1")) {
                        throw new LabyException("invalid file format");
                    }
                    boolean isDone = parts[1].equals("1");
                    switch (parts[0]) {
                        case "T":
                            tasks.add(new Todo(parts[2], isDone));
                            break;
                        case "D":
                            tasks.add(new Deadline(parts[2], LocalDateTime.parse(parts[3], formatter), isDone));
                            break;
                        case "E":
                            tasks.add(new Event(parts[2], LocalDateTime.parse(parts[3], formatter),
                                    LocalDateTime.parse(parts[4], formatter), isDone));
                            break;
                        default:
                            throw new LabyException("invalid file format");
                    }
                }
            }
        } catch (IOException e) {
            throw new LabyException("cannot read from file");
        } catch (IndexOutOfBoundsException | DateTimeParseException e) {
            throw new LabyException("invalid file format");
        }

        return tasks;
    }
}
