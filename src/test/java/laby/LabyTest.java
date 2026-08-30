package laby;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Tests command execution through the application facade. */
class LabyTest {

    @Test
    void executeCommand_addAndListTask_returnsResponsesAndPersistsTask(@TempDir Path tempDir) {
        Laby laby = new Laby(tempDir.resolve("laby.txt").toString());

        String addResponse = laby.executeCommand("todo read book");
        String listResponse = laby.executeCommand("list");

        assertTrue(addResponse.contains("[T][ ] read book"));
        assertTrue(addResponse.contains("There is a total of 1 task in your list."));
        assertTrue(listResponse.contains("1.[T][ ] read book"));
    }

    @Test
    void executeCommand_modifyTask_updatesTaskAndPersistsChange(@TempDir Path tempDir) {
        Laby laby = new Laby(tempDir.resolve("laby.txt").toString());
        laby.executeCommand("todo read book");

        String markResponse = laby.executeCommand("mark 1");
        String unmarkResponse = laby.executeCommand("unmark 1");
        String deleteResponse = laby.executeCommand("delete 1");

        assertTrue(markResponse.contains("[T][X] read book"));
        assertTrue(unmarkResponse.contains("[T][ ] read book"));
        assertTrue(deleteResponse.contains("Laby has deleted the task"));
        assertTrue(laby.executeCommand("list").contains("Here are the tasks in your list:"));
        assertFalse(laby.executeCommand("list").contains("read book"));
    }

    @Test
    void executeCommand_invalidCommand_returnsFormattedError(@TempDir Path tempDir) {
        Laby laby = new Laby(tempDir.resolve("laby.txt").toString());

        String response = laby.executeCommand("archive");

        assertEquals("____________________________________________________________\n\n"
                + "System crashing... please input the correct commands.\n"
                + "____________________________________________________________\n\n", response);
    }

    @Test
    void executeCommand_bye_returnsExitMessageWithoutTerminatingProcess(@TempDir Path tempDir) {
        Laby laby = new Laby(tempDir.resolve("laby.txt").toString());

        String response = laby.executeCommand("bye");

        assertTrue(response.contains("Goodbye. Switching to rest mode."));
    }
}
