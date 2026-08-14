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

    private static final List<String> tasks = new ArrayList<>();

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
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
                System.out.print(divider);
                continue;
            }
            tasks.add(input);
            System.out.print(divider + "added: " + input + "\n" + divider);
        }
    }
}
