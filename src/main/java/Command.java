import java.time.LocalDateTime;

public class Command {
    private final CommandType commandType;
    private final int id;
    private final String description;
    private final LocalDateTime firstTime;
    private final LocalDateTime secondTime;

    public Command(CommandType commandType, int id, String description, LocalDateTime firstTime, LocalDateTime secondTime) {
        this.commandType = commandType;
        this.id = id;
        this.description = description;
        this.firstTime = firstTime;
        this.secondTime = secondTime;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public int getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getFirstTime() {
        return this.firstTime;
    }

    public LocalDateTime getSecondTime() {
        return this.secondTime;
    }
}
