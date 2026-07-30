package console.readers;

import org.example.console.readers.StringConsoleReader;
import org.example.console.readers.responses.StringResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StringConsoleReaderTest {
    public static final InputStream SYSTEM_IN_BACKUP = System.in;

    public static void provideInput(String data){
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @AfterEach
    public void closeScanner() {
        StringConsoleReader.closeScanner();
    }

    @AfterAll
    public static void clearSystemIn() {
        System.setIn(StringConsoleReaderTest.SYSTEM_IN_BACKUP);
    }

    @Test
    public void getStringData_withEmpty_badResponseState() {
        provideInput("");
        StringResponse response = StringConsoleReader.getStringData();
        Assertions.assertEquals(StringResponse.States.BAD_RESPONSE, response.state);
    }

    @Test
    public void getStringData_withString_okState() {
        provideInput("data");
        StringResponse response = StringConsoleReader.getStringData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getStringData_withBackCommand_backCommandState() {
        provideInput("back");
        StringResponse response = StringConsoleReader.getStringData();
        Assertions.assertEquals(StringResponse.States.BACK_COMMAND, response.state);
    }

    @Test
    public void getStringData_withBackCommandAndSpacesInside_okState() {
        provideInput("b a c k");
        StringResponse response = StringConsoleReader.getStringData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getStringData_withBackCommandAndSpacesOutside_backCommandState() {
        provideInput(" back ");
        StringResponse response = StringConsoleReader.getStringData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }
}
