package laby;

import laby.task.Task;
import laby.task.TaskList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Tests persistence of tasks and rejection of malformed storage data. */
class StorageTest {

    private static final LocalDateTime DEADLINE = LocalDateTime.of(2026, 8, 23, 10, 0);
    private static final LocalDateTime EVENT_START = LocalDateTime.of(2026, 8, 23, 11, 0);
    private static final LocalDateTime EVENT_END = LocalDateTime.of(2026, 8, 23, 12, 0);

    @Test
    void readTasksFromFile_missingFile_createsEmptyFile(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("nested").resolve("laby.txt");

        List<Task> tasks = new Storage(file.toString()).readTasksFromFile();

        assertEquals(0, tasks.size());
        assertFalse(Files.notExists(file));
    }

    @Test
    void writeTasksToFile_thenReadTasksFromFile_roundTripsAllTaskTypes(@TempDir Path tempDir)
            throws Exception {
        Path file = tempDir.resolve("laby.txt");
        TaskList taskList = new TaskList(new java.util.ArrayList<>());
        taskList.addTodo("read book");
        taskList.addDeadline("return book", DEADLINE);
        taskList.addEvent("project meeting", EVENT_START, EVENT_END);
        taskList.markTask(0);

        Storage storage = new Storage(file.toString());
        storage.writeTasksToFile(taskList);
        List<Task> tasks = storage.readTasksFromFile();

        assertEquals(3, tasks.size());
        assertEquals("[T][X] read book", tasks.get(0).toString());
        assertEquals("[D][ ] return book (by: Aug 23 2026 10:00)", tasks.get(1).toString());
        assertEquals("[E][ ] project meeting (from: Aug 23 2026 11:00 to: Aug 23 2026 12:00)",
                tasks.get(2).toString());
    }

    @Test
    void readTasksFromFile_unknownTaskType_exceptionThrown(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("laby.txt");
        Files.writeString(file, "X|0|unknown\n");

        LabyException exception = assertThrows(LabyException.class,
                () -> new Storage(file.toString()).readTasksFromFile());

        assertEquals("invalid file format", exception.getMessage());
    }

    @Test
    void readTasksFromFile_invalidCompletionFlag_exceptionThrown(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("laby.txt");
        Files.writeString(file, "T|2|read book\n");

        LabyException exception = assertThrows(LabyException.class,
                () -> new Storage(file.toString()).readTasksFromFile());

        assertEquals("invalid file format", exception.getMessage());
    }

    @Test
    void readTasksFromFile_missingTaskFields_exceptionThrown(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("laby.txt");
        Files.writeString(file, "D|0|return book\n");

        LabyException exception = assertThrows(LabyException.class,
                () -> new Storage(file.toString()).readTasksFromFile());

        assertEquals("invalid file format", exception.getMessage());
    }
}
