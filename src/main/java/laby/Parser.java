package laby;

import laby.command.Command;
import laby.command.CommandType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Command parseInput(String input) throws LabyException {
        String[] parts = input.trim().split("\\s+", 2);
        CommandType commandType = CommandType.from(parts[0]);

        return switch (commandType) {
            case BYE, LIST -> parseByeOrList(parts);
            case MARK, UNMARK, DELETE -> parseModifyTask(parts);
            case TODO -> parseTodo(parts);
            case DEADLINE -> parseDeadline(parts);
            case EVENT -> parseEvent(parts);
            case UNKNOWN -> throw new LabyException("please input the correct commands.");
        };
    }

    private static Command parseByeOrList(String[] parts) throws LabyException {
        if (parts.length != 1) {
            throw new LabyException("please enter a valid command.");
        }

        return new Command(CommandType.from(parts[0]), 0, null, null, null);
    }

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

    private static Command parseTodo(String[] parts) throws LabyException {
        if (parts.length != 2) {
            throw new LabyException("task description cannot be empty.");
        }

        String description = parts[1].trim();
        if (description.isEmpty()) {
            throw new LabyException("task description cannot be empty.");
        }

        return new Command(CommandType.from(parts[0]), 0, description, null, null);
    }

    private static Command parseDeadline(String[] parts) throws LabyException {
        if (parts.length != 2) {
            throw new LabyException("task description cannot be empty.");
        }

        try {
            int deadlineIndex = parts[1].indexOf(" /by ");
            if (deadlineIndex == -1) {
                throw new LabyException("please enter a deadline with /by.");
            } else if (deadlineIndex < 1) {
                throw new LabyException("task description cannot be empty.");
            }

            String description = parts[1].substring(0, deadlineIndex).trim();
            if (description.isEmpty()) {
                throw new LabyException("task description cannot be empty.");
            }

            LocalDateTime deadline = LocalDateTime.parse(parts[1].substring(deadlineIndex + 5).trim(), formatter);
            return new Command(CommandType.from(parts[0]), 0, description, deadline, null);
        } catch (DateTimeParseException e) {
            throw new LabyException("time format must be yyyy-MM-dd HH:mm.");
        }
    }

    private static Command parseEvent(String[] parts) throws LabyException {
        if (parts.length != 2) {
            throw new LabyException("task description cannot be empty.");
        }

        try {
            int startIndex = parts[1].indexOf(" /from ");
            if (startIndex == -1) {
                throw new LabyException("please enter a starting time with /from.");
            } else if (startIndex < 1) {
                throw new LabyException("task description cannot be empty.");
            }

            int endIndex = parts[1].indexOf(" /to ");
            if (endIndex == -1) {
                throw new LabyException("please enter an ending time with /to.");
            } else if (endIndex < startIndex + 7) {
                throw new LabyException("task starting time cannot be empty.");
            }

            String description = parts[1].substring(0, startIndex).trim();
            if (description.isEmpty()) {
                throw new LabyException("task description cannot be empty.");
            }

            LocalDateTime startTime = LocalDateTime
                    .parse(parts[1].substring(startIndex + 7, endIndex).trim(), formatter);
            LocalDateTime endTime = LocalDateTime
                    .parse(parts[1].substring(endIndex + 5).trim(), formatter);
            return new Command(CommandType.from(parts[0]), 0, description, startTime, endTime);
        } catch (DateTimeParseException e) {
            throw new LabyException("time format must be yyyy-MM-dd HH:mm.");
        }
    }
}
