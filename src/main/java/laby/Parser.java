package laby;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import laby.command.Command;
import laby.command.CommandType;
import laby.task.Deadline;
import laby.task.Event;
import laby.task.Task;
import laby.task.Todo;

/** Converts user-entered command text into structured commands. */
public class Parser {
    public static final String FIELD_SEPARATOR = "|";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String DESCRIPTION_FIELD = "description";

    /**
     * Parses a complete input line or throws an error for invalid syntax.
     *
     * @param input User-entered command line.
     * @return Parsed command.
     * @throws LabyException If the input does not follow a supported command format.
     */
    public static Command parseInput(String input) throws LabyException {
        if (!isValidInput(input)) {
            throw new LabyException("invalid input.");
        }
        String[] parts = input.trim().split("\\s+", 2);
        CommandType commandType = CommandType.from(parts[0]);

        return switch (commandType) {
            case BYE, LIST -> parseByeOrList(parts);
            case MARK, UNMARK, DELETE -> parseModifyTask(parts);
            case TODO, DEADLINE, EVENT -> parseTask(parts);
            case FIND -> parseFind(parts);
            default -> throw new LabyException("please input the correct commands.");
        };
    }

    /**
     * Validates a command that must not have additional arguments.
     *
     * @param parts Command words to validate.
     * @return Parsed command.
     * @throws LabyException If additional arguments are present.
     */
    private static Command parseByeOrList(String[] parts) throws LabyException {
        if (parts.length != 1) {
            throw new LabyException("please enter a valid command.");
        }

        return new Command(CommandType.from(parts[0]), 0, null, null, null);
    }

    /**
     * Parses a command that targets a task by its one-based user index.
     *
     * @param parts Command words containing the task index.
     * @return Parsed task-modification command.
     * @throws LabyException If the task index is missing or invalid.
     */
    private static Command parseModifyTask(String[] parts) throws LabyException {
        if (parts.length != 2) {
            throw new LabyException("please enter a valid task index.");
        }

        try {
            int id = Integer.parseInt(parts[1]) - 1;
            return new Command(CommandType.from(parts[0]), id, null, null, null);
        } catch (NumberFormatException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    private static Command parseTask(String[] parts) throws LabyException {
        if (parts.length != 2) {
            throw new LabyException(getErrorMessage(DESCRIPTION_FIELD));
        }

        CommandType commandType = CommandType.from(parts[0]);
        return switch (commandType) {
            case TODO -> parseTodo(parts);
            case DEADLINE -> parseDeadline(parts);
            case EVENT -> parseEvent(parts);
            default -> throw new LabyException("please input the correct commands.");
        };
    }

    /**
     * Parses a todo command and validates its description.
     *
     * @param parts Command words containing the task description.
     * @return Parsed todo command.
     * @throws LabyException If the task description is missing.
     */
    private static Command parseTodo(String[] parts) throws LabyException {
        String description = parseField(parts[1], 0, parts[1].length(),
                DESCRIPTION_FIELD);

        return new Command(CommandType.from(parts[0]), 0, description, null, null);
    }

    /**
     * Parses a deadline command containing a description and deadline time.
     *
     * @param parts Command words containing the task description and deadline.
     * @return Parsed deadline command.
     * @throws LabyException If the description, marker, or deadline is invalid.
     */
    private static Command parseDeadline(String[] parts) throws LabyException {
        String deadlineIndicator = "/by";
        int deadlineIndex = parts[1].indexOf(deadlineIndicator);
        int deadlineBeginIndex = deadlineIndex + deadlineIndicator.length();

        String description = parseField(parts[1], 0, deadlineIndex,
                DESCRIPTION_FIELD);

        LocalDateTime deadline = parseDateTimeField(
                parts[1], deadlineBeginIndex, parts[1].length(), "deadline");

        return new Command(CommandType.from(parts[0]), 0, description, deadline, null);
    }

    /**
     * Parses an event command containing a description, start time, and end time.
     *
     * @param parts Command words containing the task description and event times.
     * @return Parsed event command.
     * @throws LabyException If the description, markers, or event times are invalid.
     */
    private static Command parseEvent(String[] parts) throws LabyException {
        String startIndicator = "/from";
        int startIndex = parts[1].indexOf(startIndicator);
        int startBeginIndex = startIndex + startIndicator.length();

        String endIndicator = "/to";
        int endIndex = parts[1].indexOf(endIndicator, startBeginIndex);
        int endBeginIndex = endIndex + endIndicator.length();

        String description = parseField(parts[1], 0, startIndex,
                DESCRIPTION_FIELD);

        LocalDateTime startTime = parseDateTimeField(
                parts[1], startBeginIndex, endIndex, "starting time");
        LocalDateTime endTime = parseDateTimeField(
                parts[1], endBeginIndex, parts[1].length(), "ending time");

        return new Command(CommandType.from(parts[0]), 0, description, startTime, endTime);
    }

    private static Command parseFind(String[] parts) throws LabyException {
        if (parts.length != 2 || parts[1].trim().isEmpty()) {
            throw new LabyException(getErrorMessage("search input"));
        }

        String description = parts[1].trim();
        return new Command(CommandType.from(parts[0]), 0, description, null, null);
    }

    private static LocalDateTime parseDateTime(String input) throws LabyException {
        if (input.isEmpty()) {
            throw new LabyException(getErrorMessage("time"));
        }
        try {
            return LocalDateTime.parse(input, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new LabyException("time format must be yyyy-MM-dd HH:mm.");
        }
    }

    /**
     * Extracts and parses a non-empty date-time field from a command string.
     *
     * @param input Input containing the date-time field.
     * @param startIndex Inclusive start index of the field.
     * @param endIndex Exclusive end index of the field.
     * @param fieldType Name used in the missing-field error message.
     * @return Parsed date and time.
     * @throws LabyException If the field is empty, malformed, or its indexes are invalid.
     */
    private static LocalDateTime parseDateTimeField(String input, int startIndex,
                                                     int endIndex, String fieldType)
            throws LabyException {
        String timeText = parseField(input, startIndex, endIndex, fieldType);
        return parseDateTime(timeText);
    }

    /**
     * Extracts a non-empty field from a command string.
     *
     * @param input Input containing the field.
     * @param startIndex Inclusive start index of the field.
     * @param endIndex Exclusive end index of the field.
     * @param fieldType Name used in the missing-field error message.
     * @return Trimmed field value.
     * @throws LabyException If the field is empty or its indexes are invalid.
     */
    private static String parseField(String input, int startIndex, int endIndex,
                                     String fieldType) throws LabyException {
        try {
            String field = input.substring(startIndex, endIndex).trim();
            if (field.isEmpty()) {
                throw new LabyException(getErrorMessage(fieldType));
            }
            return field;
        } catch (IndexOutOfBoundsException e) {
            throw new LabyException(getErrorMessage(fieldType));
        }
    }

    /**
     * Creates the standard missing-field error message.
     *
     * @param missingField Name of the missing field.
     * @return Formatted missing-field error message.
     */
    private static String getErrorMessage(String missingField) {
        return missingField + " cannot be empty.";
    }

    /**
     * Parses one serialized task record from the application's data file.
     *
     * @param input Serialized task record.
     * @return Task represented by the record.
     * @throws LabyException If the record does not follow the storage format.
     */
    public static Task parseTaskFromFile(String input) throws LabyException {
        try {
            String[] parts = input.trim().split("\\|");
            if (!parts[1].equals("0") && !parts[1].equals("1")) {
                throw new LabyException("invalid file format");
            }

            final String todoSymbol = "T";
            final String deadlineSymbol = "D";
            final String eventSymbol = "E";
            final String doneSymbol = "1";

            boolean isTaskDone = parts[1].equals(doneSymbol);

            return switch (parts[0]) {
                case todoSymbol -> new Todo(parts[2], isTaskDone);
                case deadlineSymbol -> new Deadline(parts[2],
                        LocalDateTime.parse(parts[3], DATE_TIME_FORMATTER), isTaskDone);
                case eventSymbol -> new Event(parts[2], LocalDateTime.parse(parts[3], DATE_TIME_FORMATTER),
                            LocalDateTime.parse(parts[4], DATE_TIME_FORMATTER), isTaskDone);
                default -> throw new LabyException("invalid file format");
            };
        } catch (IndexOutOfBoundsException | DateTimeParseException e) {
            throw new LabyException("invalid file format");
        }
    }

    /**
     * Checks whether a input is valid.
     *
     * @param field Field value to validate.
     * @return Whether the field is non-empty and contains no record delimiters.
     */
    public static boolean isValidInput(String field) {
        return field != null && !field.trim().isEmpty()
                && !field.contains(FIELD_SEPARATOR);
    }
}
