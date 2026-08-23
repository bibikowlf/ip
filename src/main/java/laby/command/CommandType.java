package laby.command;

/** Lists the commands understood by the application. */
public enum CommandType {
    BYE,
    LIST,
    MARK,
    UNMARK,
    DELETE,
    TODO,
    DEADLINE,
    EVENT,
    UNKNOWN;

    /**
     * Converts a command word to its type, or {@link #UNKNOWN} if unsupported.
     *
     * @param commandWord User-entered command word.
     * @return Matching command type.
     */
    public static CommandType from(String commandWord) {
        return switch (commandWord) {
            case "bye" -> BYE;
            case "list" -> LIST;
            case "mark" -> MARK;
            case "unmark" -> UNMARK;
            case "delete" -> DELETE;
            case "todo" -> TODO;
            case "deadline" -> DEADLINE;
            case "event" -> EVENT;
            default -> UNKNOWN;
        };
    }
}
