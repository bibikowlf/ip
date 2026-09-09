package laby;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import laby.command.Command;
import laby.command.CommandType;
import laby.contact.Contact;
import laby.task.Task;

/** Tests command parsing, including the validation rules used by the application. */
class ParserTest {

    private static final LocalDateTime DEADLINE = LocalDateTime.of(2026, 8, 23, 10, 0);
    private static final LocalDateTime EVENT_START = LocalDateTime.of(2026, 8, 23, 11, 0);
    private static final LocalDateTime EVENT_END = LocalDateTime.of(2026, 8, 23, 12, 0);

    @Test
    void parseInput_byeAndList_commandsParsed() throws LabyException {
        assertEquals(CommandType.BYE, Parser.parseInput("bye").getCommandType());
        assertEquals(CommandType.LIST, Parser.parseInput("list").getCommandType());
    }

    @Test
    void parseInput_modifyCommand_convertsOneBasedIndexToZeroBasedId() throws LabyException {
        Command command = Parser.parseInput("mark 3");

        assertEquals(CommandType.MARK, command.getCommandType());
        assertEquals(2, command.getId());
    }

    @Test
    void parseInput_addContactCommand_parsesAllContactFields() throws LabyException {
        Command command = Parser.parseInput(
                "addcontact John Doe /p 91234567 /e john@example.com");

        assertEquals(CommandType.ADD_CONTACT, command.getCommandType());
        assertEquals("John Doe", command.getDescription());
        assertEquals("91234567", command.getPhone());
        assertEquals("john@example.com", command.getEmail());
    }

    @Test
    void parseInput_deleteContactCommand_convertsOneBasedIndexToZeroBasedId() throws LabyException {
        Command command = Parser.parseInput("deletecontact 3");

        assertEquals(CommandType.DELETE_CONTACT, command.getCommandType());
        assertEquals(2, command.getId());
    }

    @Test
    void parseInput_missingAddContactPhone_throwsException() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("addcontact John Doe /e john@example.com"));

        assertEquals("phone number cannot be empty.", exception.getMessage());
    }

    @Test
    void parseInput_todoWithExtraSpaces_trimsDescription() throws LabyException {
        Command command = Parser.parseInput("todo   read book  ");

        assertEquals(CommandType.TODO, command.getCommandType());
        assertEquals("read book", command.getDescription());
    }

    @Test
    void parseInput_findCommand_trimsSearchInput() throws LabyException {
        Command command = Parser.parseInput("find   book  ");

        assertEquals(CommandType.FIND, command.getCommandType());
        assertEquals("book", command.getDescription());
    }

    @Test
    void parseInput_deadlineCommand_parsesDescriptionAndTime() throws LabyException {
        Command command = Parser.parseInput("deadline return book /by 2026-08-23 10:00");

        assertEquals(CommandType.DEADLINE, command.getCommandType());
        assertEquals("return book", command.getDescription());
        assertEquals(DEADLINE, command.getFirstTime());
    }

    @Test
    void parseInput_eventCommand_parsesDescriptionAndTimes() throws LabyException {
        Command command = Parser.parseInput("event project meeting /from 2026-08-23 11:00 /to 2026-08-23 12:00");

        assertEquals(CommandType.EVENT, command.getCommandType());
        assertEquals("project meeting", command.getDescription());
        assertEquals(EVENT_START, command.getFirstTime());
        assertEquals(EVENT_END, command.getSecondTime());
    }

    @Test
    void parseInput_unknownCommand_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("archive"));

        assertEquals("please input the correct commands.", exception.getMessage());
    }

    @Test
    void parseInput_missingTodoDescription_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("todo   "));

        assertEquals("description cannot be empty.", exception.getMessage());
    }

    @Test
    void parseInput_emptyFindInput_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("find   "));

        assertEquals("search input cannot be empty.", exception.getMessage());
    }

    @Test
    void parseInput_invalidTaskIndex_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("delete abc"));

        assertEquals("please enter a valid index.", exception.getMessage());
    }

    @Test
    void parseInput_missingDeadlineMarker_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("deadline return book"));

        assertEquals("deadline cannot be empty.", exception.getMessage());
    }

    @Test
    void parseInput_invalidDeadlineTime_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("deadline return book /by tomorrow"));

        assertEquals("time format must be yyyy-MM-dd HH:mm.", exception.getMessage());
    }

    @Test
    void parseInput_eventWithoutDescription_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("event /from 2026-08-23 11:00 /to 2026-08-23 12:00"));

        assertEquals("description cannot be empty.", exception.getMessage());
    }

    @Test
    void parseInput_eventWithoutEndTime_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("event project meeting /from 2026-08-23 11:00 /to "));

        assertEquals("ending time cannot be empty.", exception.getMessage());
    }

    @Test
    void parseInput_deadlineWithoutDescription_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("deadline /by Friday"));

        assertEquals("description cannot be empty.", exception.getMessage());
    }

    @Test
    void parseInput_deadlineWithoutValue_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("deadline buy milk /by "));

        assertEquals("deadline cannot be empty.", exception.getMessage());
    }

    @Test
    void parseInput_missingEventMarker_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("event project meeting /from 2026-08-23 11:00"));

        assertEquals("ending time cannot be empty.", exception.getMessage());
    }

    @Test
    void parseInput_emptyEventStartTime_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("event project meeting /from  /to 2026-08-23 12:00"));

        assertEquals("starting time cannot be empty.", exception.getMessage());
    }

    @Test
    void parseTaskFromFile_validDeadlineRecord_taskParsed() throws LabyException {
        Task task = Parser.parseTaskFromFile("D|1|return book|2026-08-23 10:00");

        assertEquals("[D][X] return book (by: Aug 23 2026 10:00)", task.toString());
    }

    @Test
    void parseTaskFromFile_validEventRecord_taskParsed() throws LabyException {
        Task task = Parser.parseTaskFromFile(
                "E|0|project meeting|2026-08-23 11:00|2026-08-23 12:00");

        assertEquals("[E][ ] project meeting (from: Aug 23 2026 11:00 to: Aug 23 2026 12:00)",
                task.toString());
    }

    @Test
    void parseContactFromFile_validRecord_contactParsed() throws LabyException {
        Contact contact = Parser.parseContactFromFile("C|John Doe|91234567|john@example.com");

        assertEquals("John Doe", contact.getName());
        assertEquals("91234567", contact.getPhone());
        assertEquals("john@example.com", contact.getEmail());
    }

    @Test
    void parseContactFromFile_malformedRecord_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseContactFromFile("C|John Doe|91234567"));

        assertEquals("invalid file format", exception.getMessage());
    }

    @Test
    void parseTaskFromFile_malformedRecord_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseTaskFromFile("D|0|return book"));

        assertEquals("invalid file format", exception.getMessage());
    }
}
