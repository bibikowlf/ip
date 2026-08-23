package laby;

import laby.task.TaskList;

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
    private static final String MSG_MARK = "Understood. Laby has marked the task as done.\n";
    private static final String MSG_UNMARK = "Understood. Laby has marked the task as not done.\n";
    private static final String MSG_ADD = "Laby has added the task. Make sure to rest, Chief :o\n";
    private static final String MSG_DELETE = "Laby has deleted the task. Glad to see you resting ;)\n";

    public static void displayReadFileError(LabyException labyException) {
        System.out.print(MSG_DIVIDER + "System crashing... " + labyException.getMessage()
                + "\nUsing new file..." + MSG_DIVIDER);
    }

    public static void displayError(LabyException labyException) {
        System.out.print(MSG_DIVIDER + "System crashing... " + labyException.getMessage() + "\n" + MSG_DIVIDER);
    }

    public static void displayWelcomeBanner() {
        System.out.print(MSG_DIVIDER + MSG_BANNER + MSG_DIVIDER + MSG_OPEN + MSG_ASK + MSG_DIVIDER);
    }

    public static void displayNumberOfTasks(int numberOfTasks) {
        System.out.print("There is a total of " + numberOfTasks + " task" + (numberOfTasks > 1 ? "s" : "")
                + " in your list.\n" + MSG_DIVIDER);
    }

    public static void displayTasks(TaskList taskList) {
        System.out.print(MSG_DIVIDER);
        System.out.print(MSG_LIST);
        System.out.print(taskList.toString());
        System.out.print(MSG_DIVIDER);
    }

    public static void displayMarkTask(String task) {
        System.out.print(MSG_DIVIDER + MSG_MARK + "  " + task + "\n" + MSG_DIVIDER);
    }

    public static void displayUnmarkTask(String task) {
        System.out.print(MSG_DIVIDER + MSG_UNMARK + "  " + task + "\n" + MSG_DIVIDER);
    }

    public static void displayDeleteTask(String task) {
        System.out.print(MSG_DIVIDER + MSG_DELETE + "  " + task + "\n");
    }

    public static void displayAddTask(String task) {
        System.out.print(MSG_DIVIDER + MSG_ADD + "  " + task + "\n");
    }

    public static void displayExitMessage() {
        System.out.print(MSG_DIVIDER + MSG_EXIT + MSG_DIVIDER);
    }
}
