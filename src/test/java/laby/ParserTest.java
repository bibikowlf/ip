package laby;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import laby.command.Command;
import laby.command.CommandType;

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

        assertEquals("task description cannot be empty.", exception.getMessage());
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

        assertEquals("please enter a valid task index.", exception.getMessage());
    }

    @Test
    void parseInput_missingDeadlineMarker_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("deadline return book"));

        assertEquals("please enter a deadline with /by.", exception.getMessage());
    }

    @Test
    void parseInput_invalidDeadlineTime_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("deadline return book /by tomorrow"));

        assertEquals("time format must be yyyy-MM-dd HH:mm.", exception.getMessage());
    }

    @Test
    void parseInput_missingEventMarker_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("event project meeting /from 2026-08-23 11:00"));

        assertEquals("please enter an ending time with /to.", exception.getMessage());
    }

    @Test
    void parseInput_emptyEventStartTime_exceptionThrown() {
        LabyException exception = assertThrows(LabyException.class,
                () -> Parser.parseInput("event project meeting /from  /to 2026-08-23 12:00"));

        assertEquals("time format must be yyyy-MM-dd HH:mm.", exception.getMessage());
    }
}
