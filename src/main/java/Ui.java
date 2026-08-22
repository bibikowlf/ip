public class Ui {
    private static final String banner = """
            #       ###   ####   #   #
            #      #   #  #   #   # #
            #      #####  ####     #
            #      #   #  #   #    #
            #####  #   #  ####     #
            """;
    private static final String divider = "____________________________________________________________\n\n";
    private static final String openMsg = "Hello Chief. Laby is your personal assistant.\n";
    private static final String askMsg =  "What orders do you have today?\n";
    private static final String exitMsg = "Goodbye. Switching to rest mode.\n";
    private static final String listMsg = "Here are the tasks in your list:\n";
    private static final String markMsg = "Understood. Laby has marked the task as done.\n";
    private static final String unmarkMsg = "Understood. Laby has marked the task as not done.\n";
    private static final String addMsg = "Laby has added the task. Make sure to rest, Chief :o\n";
    private static final String deleteMsg = "Laby has deleted the task. Glad to see you resting ;)\n";

    public void displayReadFileError(LabyException labyException) {
        System.out.print(divider + "System crashing... " + labyException.getMessage() + "\nUsing new file..." + divider);
    }

    public void displayError(LabyException labyException) {
        System.out.print(divider + "System crashing... " + labyException.getMessage() + "\n" + divider);
    }

    public void displayWelcomeBanner() {
        System.out.print(divider + banner + divider + openMsg + askMsg + divider);
    }

    public void displayNumberOfTasks(int numberOfTasks) {
        System.out.print("There is a total of " + numberOfTasks + " task" + (numberOfTasks > 1 ? "s" : "")
                + " in your list.\n" + divider);
    }

    public void displayTasks(TaskList taskList) {
        System.out.print(divider);
        System.out.print(listMsg);
        System.out.print(taskList.toString());
        System.out.print(divider);
    }

    public void displayMarkTask(TaskList taskList, int taskId) {
        System.out.print(divider + markMsg + "  " + taskList.taskToString(taskId) + "\n" + divider);
    }

    public void displayUnmarkTask(TaskList taskList, int taskId) {
        System.out.print(divider + unmarkMsg + "  " + taskList.taskToString(taskId) + "\n" + divider);
    }

    public void displayDeleteTask(TaskList taskList, int taskId) {
        System.out.print(divider + deleteMsg + "  " + taskList.taskToString(taskId) + "\n");
    }

    public void displayAddTask(TaskList taskList) {
        System.out.print(divider + addMsg + "  " + taskList.lastTaskToString() + "\n");
    }

    public void displayExitMessage() {
        System.out.print(divider + exitMsg + divider);
    }
}
