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

        System.out.println(divider + banner + divider + openMsg + askMsg + divider);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println(divider + exitMsg + divider);
                break;
            }
            System.out.println(divider + input + "\n" + divider);
        }
    }
}
