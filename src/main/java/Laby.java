import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Laby {
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
    private static final List<Task> tasks = new ArrayList<>();

    private static void printNumberOfTasks() {
        System.out.print("There is a total of " + tasks.size() + " task" + (tasks.size() > 1 ? "s" : "") + " in your list.\n" );
    }

    private static void printTasks() {
        System.out.print(divider);
        System.out.print(listMsg);
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i).toString());
        }
        System.out.print(divider);
    }

    private static void markTask(String input) throws LabyException {
        try {
            int taskId = Integer.parseInt(input.substring(5)) - 1;
            tasks.get(taskId).markAsDone();
            System.out.print(divider + markMsg + "  " + tasks.get(taskId).toString() + "\n" + divider);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    private static void unmarkTask(String input) throws LabyException {
        try {
            int taskId = Integer.parseInt(input.substring(7)) - 1;
            tasks.get(taskId).markAsUndone();
            System.out.print(divider + unmarkMsg + "  " + tasks.get(taskId).toString() + "\n" + divider);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    private static void deleteTask(String input) throws LabyException {
        try {
            int taskId = Integer.parseInt(input.substring(7)) - 1;
            Task task = tasks.get(taskId);
            tasks.remove(taskId);
            System.out.print(divider + deleteMsg + "  " + task.toString() + "\n");
            Laby.printNumberOfTasks();
            System.out.print(divider);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new LabyException("please enter a valid task index.");
        }
    }

    private static void addTodo(String input) throws LabyException {
        String description = input.substring(5);
        if (description.trim().isEmpty()) {
            throw new LabyException("task description cannot be empty.");
        }
        Task task = new Todo(description);
        tasks.add(task);
        System.out.print(divider + addMsg + "  " + task + "\n");
        Laby.printNumberOfTasks();
        System.out.print(divider);
    }

    private static void addDeadline(String input) throws LabyException {
        int deadlineIndex = input.indexOf(" /by ");
        if (deadlineIndex == -1) {
            throw new LabyException("please enter a deadline with /by.");
        } else if (deadlineIndex < 9) {
            throw new LabyException("task description cannot be empty.");
        }
        String description = input.substring(9, deadlineIndex);
        if (description.trim().isEmpty()) {
            throw new LabyException("task description cannot be empty.");
        }
        String deadline = input.substring(deadlineIndex + 5);
        if (deadline.trim().isEmpty()) {
            throw new LabyException("task deadline cannot be empty.");
        }
        Task task = new Deadline(description, deadline);
        tasks.add(task);
        System.out.print(divider + addMsg + "  " + task + "\n");
        Laby.printNumberOfTasks();
        System.out.print(divider);
    }

    private static void addEvent(String input) throws LabyException {
        int startIndex = input.indexOf(" /from ");
        if (startIndex == -1) {
            throw new LabyException("please enter a starting time with /from.");
        } else if (startIndex < 6) {
            throw new LabyException("task description cannot be empty.");
        }
        int endIndex = input.indexOf(" /to ");
        if (endIndex == -1) {
            throw new LabyException("please enter an ending time with /to.");
        } else if (endIndex < startIndex + 7) {
            throw new LabyException("task starting time cannot be empty.");
        }
        String description = input.substring(6, startIndex);
        if (description.trim().isEmpty()) {
            throw new LabyException("task description cannot be empty.");
        }
        String startTime = input.substring(startIndex + 7, endIndex);
        if (startTime.trim().isEmpty()) {
            throw new LabyException("task starting time cannot be empty.");
        }
        String endTime = input.substring(endIndex + 5);
        if (endTime.trim().isEmpty()) {
            throw new LabyException("task ending time cannot be empty.");
        }
        Task task = new Event(description, startTime, endTime);
        tasks.add(task);
        System.out.print(divider + addMsg + "  " + task + "\n");
        Laby.printNumberOfTasks();
        System.out.print(divider);
    }

    static void main(String[] args) {
        System.out.print(divider + banner + divider + openMsg + askMsg + divider);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            try {
                if (input.equals("bye")) {
                    System.out.print(divider + exitMsg + divider);
                    break;
                } else if (input.equals("list")) {
                    Laby.printTasks();
                } else if (input.equals("todo") || input.equals("deadline") || input.equals("event")) {
                    throw new LabyException("task description cannot be empty.");
                } else if (input.equals("mark") || input.equals("unmark") || input.equals("delete")) {
                    throw new LabyException("please enter a valid task index.");
                } else if (input.startsWith("mark ")) {
                    Laby.markTask(input);
                } else if (input.startsWith("unmark ")) {
                    Laby.unmarkTask(input);
                } else if (input.startsWith("delete ")) {
                    Laby.deleteTask(input);
                } else if (input.startsWith("todo ")) {
                    Laby.addTodo(input);
                } else if (input.startsWith("deadline ")) {
                    Laby.addDeadline(input);
                } else if (input.startsWith("event ")) {
                    Laby.addEvent(input);
                } else {
                    throw new LabyException("please input the correct commands.");
                }
            } catch (LabyException e) {
                System.out.print(divider + "System crashing... " + e.getMessage() + "\n" + divider);
            }
        }
    }
}
