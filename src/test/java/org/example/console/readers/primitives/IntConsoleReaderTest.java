package org.example.console.readers.primitives;

import org.example.console.readers.primitives.responses.IntResponse;
import org.example.console.readers.primitives.responses.StringResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntConsoleReaderTest {

    @AfterEach
    public void closeScanner() {
        StringConsoleReader.closeScanner();
    }

    @AfterAll
    public static void clearSystemIn() {
        System.setIn(StringConsoleReaderTest.SYSTEM_IN_BACKUP);
    }

    @Test
    public void getIntData_withInt_okState() {
        StringConsoleReaderTest.provideInput("123");
        IntResponse response = IntConsoleReader.getIntData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getIntData_withNegativeInt_okState() {
        StringConsoleReaderTest.provideInput("-123");
        IntResponse response = IntConsoleReader.getIntData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getIntData_withIntAndSpaces_okState() {
        StringConsoleReaderTest.provideInput(" 100 000 000 ");
        IntResponse response = IntConsoleReader.getIntData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getIntData_withBeyondRangeInt_cantConvertState() {
        StringConsoleReaderTest.provideInput("1232834823482394823948923");
        IntResponse response = IntConsoleReader.getIntData();
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }

    @Test
    public void getIntData_withDouble_cantConvertState() {
        StringConsoleReaderTest.provideInput("123.43");
        IntResponse response = IntConsoleReader.getIntData();
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }

    @Test
    public void getIntData_withEmpty_badResponseState() {
        StringConsoleReaderTest.provideInput("");
        IntResponse response = IntConsoleReader.getIntData();
        Assertions.assertEquals(StringResponse.States.BAD_RESPONSE, response.state);
    }

    @Test
    public void getIntData_withString_cantConvertState(){
        StringConsoleReaderTest.provideInput("data");
        IntResponse response = IntConsoleReader.getIntData();
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }

    @Test
    public void getIntData_withBoolean_cantConvertState() {
        StringConsoleReaderTest.provideInput("true");
        IntResponse response = IntConsoleReader.getIntData();
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }
}
