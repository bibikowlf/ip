package laby;

import laby.task.TaskList;

/** Handles all console output produced by the application. */
public class Ui {
    private static final String MSG_BANNER = """
            #       ###   ####   #   #
            #      #   #  #   #   # #
            #      #####  ####     #
            #      #   #  #   #    #
            #####  #   #  ####     #
            """;
    private static final String MSG_DIVIDER = "____________________________________________________________\n\n";
    private static final String MSG_OPEN = "Hello Chief. Laby is your personal assistant.\n";
    private static final String MSG_ASK = "What orders do you have today?\n";
    private static final String MSG_EXIT = "Goodbye. Switching to rest mode.\n";
    private static final String MSG_LIST = "Here are the tasks in your list:\n";
    private static final String MSG_FILTER = "Here are the matching tasks in your list:\n";
    private static final String MSG_MARK = "Understood. Laby has marked the task as done.\n";
    private static final String MSG_UNMARK = "Understood. Laby has marked the task as not done.\n";
    private static final String MSG_ADD = "Laby has added the task. Make sure to rest, Chief :o\n";
    private static final String MSG_DELETE = "Laby has deleted the task. Glad to see you resting ;)\n";

    /**
     * Displays a file-loading error and tells the user that a new file will be used.
     *
     * @param labyException File-loading error to display.
     */
    public static String getReadFileError(LabyException labyException) {
        return MSG_DIVIDER + "System crashing... " + labyException.getMessage()
                + "\nUsing new file..." + MSG_DIVIDER;
    }

    /**
     * Displays an error message without terminating the application.
     *
     * @param labyException Application error to display.
     */
    public static String getError(LabyException labyException) {
        return "System crashing... " + labyException.getMessage() + "\n";
    }

    /**
     * Displays the application banner and opening prompt.
     */
    public static String getWelcomeBanner() {
        return MSG_DIVIDER + MSG_BANNER + MSG_DIVIDER + MSG_OPEN + MSG_ASK + MSG_DIVIDER;
    }

    /**
     * Displays the current number of tasks.
     *
     * @param numberOfTasks Number of tasks to display.
     */
    public static String getNumberOfTasks(int numberOfTasks) {
        return "There is a total of " + numberOfTasks + " task" + (numberOfTasks > 1 ? "s" : "")
                + " in your list.\n";
    }

    /**
     * Displays every task in the supplied task list.
     *
     * @param taskList Task list to display.
     */
    public static String getTasks(TaskList taskList) {
        return MSG_LIST + taskList;
    }

    /**
     * Displays confirmation that a task was marked as done.
     *
     * @param task Display text of the marked task.
     */
    public static String getMarkTask(String task) {
        return MSG_MARK + "  " + task + "\n";
    }

    /**
     * Displays confirmation that a task was marked as not done.
     *
     * @param task Display text of the unmarked task.
     */
    public static String getUnmarkTask(String task) {
        return MSG_UNMARK + "  " + task + "\n";
    }

    /**
     * Displays confirmation that a task was deleted.
     *
     * @param task Display text of the deleted task.
     */
    public static String getDeleteTask(String task) {
        return MSG_DELETE + "  " + task + "\n";
    }

    /**
     * Displays confirmation that a task was added.
     *
     * @param task Display text of the added task.
     */
    public static String getAddTask(String task) {
        return MSG_ADD + "  " + task + "\n";
    }

    /**
     * Displays the application exit message.
     */
    public static String getExitMessage() {
        return MSG_EXIT;
    }

    /**
     * Displays list of tasks matching the input.
     *
     * @param taskList List of tasks.
     * @param input Input which tasks are filtered by.
     */
    public static String getFilteredTasks(TaskList taskList, String input) {
        return MSG_FILTER + taskList.filterTasks(input);
    }

    /**
     * Adds console divider lines around a response.
     *
     * @param response Response to format for the console.
     * @return Response surrounded by console dividers.
     */
    public static String addDivider(String response) {
        return MSG_DIVIDER + response + MSG_DIVIDER;
    }
}
