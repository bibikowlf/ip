import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Laby {
    private static final String banner = "#       ###   ####   #   #\n" +
            "#      #   #  #   #   # #\n" +
            "#      #####  ####     #\n" +
            "#      #   #  #   #    #\n" +
            "#####  #   #  ####     #\n";
    private static final String divider = "____________________________________________________________\n\n";
    private static final String openMsg = "Hello Chief. Laby is your personal assistant.\n";
    private static final String askMsg =  "What orders do you have today?\n";
    private static final String exitMsg = "Goodbye. Switching to rest mode.\n";
    private static final String listMsg = "Here are the tasks in your list:\n";
    private static final String markMsg = "Understood. Laby has marked the task as done.\n";
    private static final String unmarkMsg = "Understood. Laby has marked the task as not done.\n";
    private static final List<Task> tasks = new ArrayList<>();

    private static void printTasks() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i).toString());
        }
    }

    static void main(String[] args) {
        System.out.print(divider + banner + divider + openMsg + askMsg + divider);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.print(divider + exitMsg + divider);
                break;
            } else if (input.equals("list")) {
                System.out.print(divider);
                System.out.print(listMsg);
                printTasks();
                System.out.print(divider);
            } else if (input.startsWith("mark")) {
                int taskId = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks.get(taskId).markAsDone();
                System.out.print(divider + markMsg + "  " + tasks.get(taskId).toString() + "\n" + divider);
            } else if (input.startsWith("unmark")) {
                int taskId = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks.get(taskId).markAsUndone();
                System.out.print(divider + unmarkMsg + "  " + tasks.get(taskId).toString() + "\n" + divider);
            } else {
                tasks.add(new Task(input));
                System.out.print(divider + "added: " + input + "\n" + divider);
            }
        }
    }
}
