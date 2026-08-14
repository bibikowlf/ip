public class Laby {
    public static void main(String[] args) {
        String banner = "#       ###   ####   #   #\n" +
                "#      #   #  #   #   # #\n" +
                "#      #####  ####     #\n" +
                "#      #   #  #   #    #\n" +
                "#####  #   #  ####     #\n";
        String divider = "____________________________________________________________\n\n";
        String openMsg = "Hello Chief. Laby is your personal assistant.\nWhat orders do you have today?\n";
        String exitMsg = "Goodbye. Switching to rest mode.\n";

        System.out.println(divider + banner + divider + openMsg + divider + exitMsg);
    }
}
