import java.text.SimpleDateFormat;
import java.util.Date;

public class AppConstants {
    // file name for serialization/deserialization
    // this will be stored in db/user.ser file

    public static final String USER_FILE_NAME = "db/users.ser";
    public static final String GROUP_FILE_NAME = "db/groups.txt";

    // broker url for ActiveMQ
    public static final String BROKER_URL = "tcp://localhost:61616";

    // Color codes
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    // public static VillageUser currentUser = AppUsers.getCurrentUser();
    public static VillageUser currentUser;

    // prints the string with a color specified by the user
    public static void println(String text, String color) {
        String colorCode = getANSIColorCode(color);
        System.out.println(colorCode + text + ANSI_RESET);
    }
    // prints the string with a color specified by the user
    public static void printError(String text) {
        String colorCode = getANSIColorCode("red");
        System.out.println(colorCode + text + ANSI_RESET);
    }

    // prints with default yellow color and new line at the end
    public static void println(String text) {
        String colorCode = getANSIColorCode("yellow");
        System.out.println(colorCode + text + ANSI_RESET);
    }

    // this is for printing without a new line
    public static void print(String text) {
        String colorCode = getANSIColorCode("yellow");
        System.out.print(colorCode + text + ANSI_RESET);
    }
    // this is for printing without a new line
    public static void printSuccess(String text) {
        String colorCode = getANSIColorCode("green");
        System.out.print(colorCode + text + ANSI_RESET);
    }

    // this is for printing without a new line
    public static void print(String text, String color) {
        String colorCode = getANSIColorCode(color);
        System.out.print(colorCode + text + ANSI_RESET);
    }

    private static String getANSIColorCode(String color) {
        switch (color.toLowerCase()) {
            case "black":
                return ANSI_BLACK;
            case "red":
                return ANSI_RED;
            case "green":
                return ANSI_GREEN;
            case "yellow":
                return ANSI_YELLOW;
            case "blue":
                return ANSI_BLUE;
            case "purple":
                return ANSI_PURPLE;
            case "cyan":
                return ANSI_CYAN;
            case "white":
                return ANSI_WHITE;
            default:
                return ANSI_YELLOW;
        }
    }

    // get the current time
    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

}
