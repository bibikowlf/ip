package laby;

import laby.task.TaskList;

public class Ui {
    private static final String banner = """
            #       ###   ####   #   #
            #      #   #  #   #   # #
            #      #####  ####     #
            #      #   #  #   #    #
            #####  #   #  ####     #
            """;
    private static final String divider = "____________________________________________________________\n\n";
    private static final String openMsg = "Hello Chief. laby.Laby is your personal assistant.\n";
    private static final String askMsg =  "What orders do you have today?\n";
    private static final String exitMsg = "Goodbye. Switching to rest mode.\n";
    private static final String listMsg = "Here are the tasks in your list:\n";
    private static final String markMsg = "Understood. laby.Laby has marked the task as done.\n";
    private static final String unmarkMsg = "Understood. laby.Laby has marked the task as not done.\n";
    private static final String addMsg = "laby.Laby has added the task. Make sure to rest, Chief :o\n";
    private static final String deleteMsg = "laby.Laby has deleted the task. Glad to see you resting ;)\n";

    public static void displayReadFileError(LabyException labyException) {
        System.out.print(divider + "System crashing... " + labyException.getMessage() + "\nUsing new file..." + divider);
    }

    public static void displayError(LabyException labyException) {
        System.out.print(divider + "System crashing... " + labyException.getMessage() + "\n" + divider);
    }

    public static void displayWelcomeBanner() {
        System.out.print(divider + banner + divider + openMsg + askMsg + divider);
    }

    public static void displayNumberOfTasks(int numberOfTasks) {
        System.out.print("There is a total of " + numberOfTasks + " task" + (numberOfTasks > 1 ? "s" : "")
                + " in your list.\n" + divider);
    }

    public static void displayTasks(TaskList taskList) {
        System.out.print(divider);
        System.out.print(listMsg);
        System.out.print(taskList.toString());
        System.out.print(divider);
    }

    public static void displayMarkTask(String task) {
        System.out.print(divider + markMsg + "  " + task + "\n" + divider);
    }

    public static void displayUnmarkTask(String task) {
        System.out.print(divider + unmarkMsg + "  " + task + "\n" + divider);
    }

    public static void displayDeleteTask(String task) {
        System.out.print(divider + deleteMsg + "  " + task + "\n");
    }

    public static void displayAddTask(String task) {
        System.out.print(divider + addMsg + "  " + task + "\n");
    }

    public static void displayExitMessage() {
        System.out.print(divider + exitMsg + divider);
    }
}
