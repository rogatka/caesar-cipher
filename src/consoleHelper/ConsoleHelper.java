package consoleHelper;

import exception.MyInterruptedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String readString() throws IOException, MyInterruptedException {
        String line = "";
        line = reader.readLine();
        if (line.equalsIgnoreCase("exit")) {
            throw new MyInterruptedException();
        }
        return line;
    }

    public static int readInt() throws IOException, MyInterruptedException {
        return Integer.parseInt(readString());
    }

    public static void writeMessage(String message) {
        System.out.print(message);
    }
}
