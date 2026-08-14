import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Laby {
    public static void main(String[] args) {
        String banner = "#       ###   ####   #   #\n" +
                "#      #   #  #   #   # #\n" +
                "#      #####  ####     #\n" +
                "#      #   #  #   #    #\n" +
                "#####  #   #  ####     #\n";
        String divider = "____________________________________________________________\n\n";
        String openMsg = "Hello Chief. Laby is your personal assistant.\n";
        String askMsg =  "What orders do you have today?\n";
        String exitMsg = "Goodbye. Switching to rest mode.\n";

        List<String> tasks = new ArrayList<>();

        System.out.print(divider + banner + divider + openMsg + askMsg + divider);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.print(divider + exitMsg + divider);
                break;
            } else if (input.equals("list")) {
                System.out.println(divider);
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
