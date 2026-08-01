package org.example.console.readers.primitives;

import org.example.console.readers.primitives.responses.StringResponse;

import java.util.Scanner;

public class StringConsoleReader {
    public static final String GO_BACK_COMMAND = "back";
    private volatile static Scanner scanner;

    public static synchronized void openScanner() {
        if (scanner != null)
            return;
        scanner = new Scanner(System.in);
    }

    public static synchronized void closeScanner() {
        if (scanner == null)
            return;
        scanner.close();
        scanner = null;
    }

    public static synchronized StringResponse getStringData() {
        if (scanner == null)
            openScanner();
        StringResponse response = new StringResponse();
        try {
            response.stringData = scanner.nextLine();
            if (response.stringData.equals(GO_BACK_COMMAND)) {
                response.state = StringResponse.States.BACK_COMMAND;
                return response;
            }
            response.state = StringResponse.States.OK;
        } catch (Exception e) {
            System.out.println("Can't get the data you wrote, reason: " + e.getMessage());
            response.state = StringResponse.States.BAD_RESPONSE;
            response.errorMessage = e.getMessage();
        }
        return response;
    }
}
