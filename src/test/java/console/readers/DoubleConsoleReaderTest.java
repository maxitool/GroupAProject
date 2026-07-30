package console.readers;

import org.example.console.readers.DoubleConsoleReader;
import org.example.console.readers.StringConsoleReader;
import org.example.console.readers.responses.DoubleResponse;
import org.example.console.readers.responses.StringResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DoubleConsoleReaderTest {
    @AfterEach
    public void closeScanner() {
        StringConsoleReader.closeScanner();
    }

    @AfterAll
    public static void clearSystemIn() {
        System.setIn(StringConsoleReaderTest.SYSTEM_IN_BACKUP);
    }

    @Test
    public void getDoubleData_withInt_okState() {
        StringConsoleReaderTest.provideInput("123");
        DoubleResponse response = DoubleConsoleReader.getDoubleData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getDoubleData_withDotDouble_okState() {
        StringConsoleReaderTest.provideInput("123.123");
        DoubleResponse response = DoubleConsoleReader.getDoubleData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getDoubleData_withEFormatDouble_okState() {
        StringConsoleReaderTest.provideInput("1.2e123d");
        DoubleResponse response = DoubleConsoleReader.getDoubleData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getDoubleData_withCommaDouble_okState() {
        StringConsoleReaderTest.provideInput("123,123");
        DoubleResponse response = DoubleConsoleReader.getDoubleData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getDoubleData_withDoubleAndSpaces_okState() {
        StringConsoleReaderTest.provideInput(" 1 123 . 123 ");
        DoubleResponse response = DoubleConsoleReader.getDoubleData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getDoubleData_withNegativeDouble_okState() {
        StringConsoleReaderTest.provideInput("-123.123");
        DoubleResponse response = DoubleConsoleReader.getDoubleData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getDoubleData_withBeyondRangeDouble_cantConvertState() {
        StringConsoleReaderTest.provideInput("1.8e50000d");
        DoubleResponse response = DoubleConsoleReader.getDoubleData();
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }

    @Test
    public void getDoubleData_with2DotsDouble_cantConvertState() {
        StringConsoleReaderTest.provideInput("123..123");
        DoubleResponse response = DoubleConsoleReader.getDoubleData();
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }

    @Test
    public void getDoubleData_with2CommasDouble_cantConvertState() {
        StringConsoleReaderTest.provideInput("123,,123");
        DoubleResponse response = DoubleConsoleReader.getDoubleData();
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }

    @Test
    public void getDoubleData_withStringDouble_cantConvertState() {
        StringConsoleReaderTest.provideInput("data");
        DoubleResponse response = DoubleConsoleReader.getDoubleData();
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }

    @Test
    public void getDoubleData_withEmpty_badResponseState() {
        StringConsoleReaderTest.provideInput("");
        DoubleResponse response = DoubleConsoleReader.getDoubleData();
        Assertions.assertEquals(StringResponse.States.BAD_RESPONSE, response.state);
    }

    @Test
    public void getDoubleData_withBoolean_cantConvertState() {
        StringConsoleReaderTest.provideInput("data");
        DoubleResponse response = DoubleConsoleReader.getDoubleData();
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }
}
