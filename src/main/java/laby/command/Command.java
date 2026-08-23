package laby.command;

import java.time.LocalDateTime;

/** Stores the parsed action and arguments for one user command. */
public class Command {
    private final CommandType commandType;
    private final int id;
    private final String description;
    private final LocalDateTime firstTime;
    private final LocalDateTime secondTime;

    /**
     * Creates a command with its optional task and time arguments.
     *
     * @param commandType Type of command.
     * @param id Zero-based task index.
     * @param description Task description, if applicable.
     * @param firstTime First time argument, if applicable.
     * @param secondTime Second time argument, if applicable.
     */
    public Command(CommandType commandType, int id, String description, LocalDateTime firstTime, LocalDateTime secondTime) {
        this.commandType = commandType;
        this.id = id;
        this.description = description;
        this.firstTime = firstTime;
        this.secondTime = secondTime;
    }

    /**
     * Returns the command's type.
     *
     * @return Command type.
     */
    public CommandType getCommandType() {
        return this.commandType;
    }

    /**
     * Returns the zero-based task index associated with the command.
     *
     * @return Zero-based task index.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the task description, when one was supplied.
     *
     * @return Task description, or {@code null} when none was supplied.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the first time argument, when one was supplied.
     *
     * @return First time argument, or {@code null} when none was supplied.
     */
    public LocalDateTime getFirstTime() {
        return this.firstTime;
    }

    /**
     * Returns the second time argument, when one was supplied.
     *
     * @return Second time argument, or {@code null} when none was supplied.
     */
    public LocalDateTime getSecondTime() {
        return this.secondTime;
    }
}
