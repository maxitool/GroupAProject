package org.example;

import org.example.CmdReaders.CmdLongReader;
import org.example.CmdReaders.Responses.LongResponse;
import org.example.CmdReaders.Responses.StringResponse;

public class Main {
    private static final String MAIN_GUI = "\nGUI interface:\n" +
                                        "1. Fill data;\n" +
                                        "2. Get data;\n" +
                                        "3. Sort data;\n" +
                                        "4. Write data to file;\n" +
                                        "back. Stop program.";

    public static void main(String[] args) {
        System.out.println("\nGroup A program is running.");
        LongResponse answer;
        while (true)
        {
            System.out.println(MAIN_GUI);
            do {
                answer = CmdLongReader.getLongData();
            } while (answer.state != StringResponse.States.BACK_COMMAND && answer.state != StringResponse.States.OK);
            if (answer.state == StringResponse.States.BACK_COMMAND) {
                return;
            }
            switch ((int)answer.longData) {
                case 1:
                    // to do
                    break;
                case 2:
                    // to do
                    break;
                case 3:
                    // to do
                    break;
                case 4:
                    // to do
                    break;
                default:
                    System.out.println("Can't recognize wrote command");
                    continue;
            }
        }
    }
}