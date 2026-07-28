package org.example.console.readers;

import org.example.console.readers.responses.IntResponse;
import org.example.console.readers.responses.StringResponse;

public class IntConsoleReader extends StringConsoleReader {
    public static synchronized IntResponse getIntData() {
        StringResponse stringResponse = getStringData();
        IntResponse longResponse = new IntResponse(stringResponse);
        if (longResponse.state != StringResponse.States.OK)
            return longResponse;
        try {
            longResponse.intData = Integer.parseInt(longResponse.stringData.replace(" ",""));
        } catch (NumberFormatException e) {
            System.out.println("Can't convert the wrote data to int. " + e.getMessage());
            longResponse.errorMessage = e.getMessage();
            longResponse.state = StringResponse.States.CANT_CONVERT;
        }
        return longResponse;
    }
}
