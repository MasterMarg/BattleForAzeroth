package sample.Service;

public class Painter {
    public enum Paint {
        ANSI_RESET("\u001B[0m"),
        ANSI_BOLD_RED("\u001B[31;1m"),
        ANSI_BOLD_BLUE("\u001B[34;1m"),
        ANSI_BOLD("\u001B[0;1m"),
        ANSI_BOLD_YELLOW("\u001B[33;1m"),
        ANSI_WHITE_BOLD("\u001B[30;1m"),
        ANSI_GREEN_BOLD("\u001B[32;1m"),
        ANSI_VIOLET("\u001B[35m"),
        ANSI_CYAN_BOLD("\u001B[36;1m"),
        ANSI_RED("\u001B[31m"),
        ANSI_BLUE("\u001B[34m");

        private final String color;

        Paint(String color) {
            this.color = color;
        }
    }

    public static String getPainted(String string, Paint paint) {
        //return (paint.color + string + Paint.ANSI_RESET.color);
        return string;
    }
}
