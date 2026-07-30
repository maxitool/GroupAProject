package console.readers;

import org.example.console.readers.BooleanConsoleReader;
import org.example.console.readers.StringConsoleReader;
import org.example.console.readers.responses.BooleanResponse;
import org.example.console.readers.responses.StringResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BooleanConsoleReaderTest {
    @AfterEach
    public void closeScanner() {
        StringConsoleReader.closeScanner();
    }

    @AfterAll
    public static void clearSystemIn() {
        System.setIn(StringConsoleReaderTest.SYSTEM_IN_BACKUP);
    }

    @Test
    public void getBooleanData_withBooleanAndSpaces_okState() {
        StringConsoleReaderTest.provideInput(" tr ue ");
        BooleanResponse response = BooleanConsoleReader.getBooleanData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getBooleanData_withLowerCaseTrueBoolean_okState() {
        StringConsoleReaderTest.provideInput("true");
        BooleanResponse response = BooleanConsoleReader.getBooleanData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getBooleanData_withUpperCaseTrueBoolean_okState() {
        StringConsoleReaderTest.provideInput("True");
        BooleanResponse response = BooleanConsoleReader.getBooleanData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getBooleanData_withLowerCaseFalseBoolean_okState() {
        StringConsoleReaderTest.provideInput("false");
        BooleanResponse response = BooleanConsoleReader.getBooleanData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getBooleanData_withUpperCaseFalseBoolean_okState() {
        StringConsoleReaderTest.provideInput("False");
        BooleanResponse response = BooleanConsoleReader.getBooleanData();
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getBooleanData_withLowerCaseYesTrueValue_okState() {
        StringConsoleReaderTest.provideInput("yes");
        BooleanResponse response = BooleanConsoleReader.getBooleanData("yes", "no");
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getBooleanData_withUpperCaseYesTrueValue_okState() {
        StringConsoleReaderTest.provideInput("Yes");
        BooleanResponse response = BooleanConsoleReader.getBooleanData("yes", "no");
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getBooleanData_withYesTrueValueAndSpacesOutside_okState() {
        StringConsoleReaderTest.provideInput(" yes ");
        BooleanResponse response = BooleanConsoleReader.getBooleanData("yes", "no");
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getBooleanData_withYesTrueValueAndSpacesInside_cantConvertState() {
        StringConsoleReaderTest.provideInput("y e s");
        BooleanResponse response = BooleanConsoleReader.getBooleanData("yes", "no");
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }

    @Test
    public void getBooleanData_withLowerCaseNoFalseValue_okState() {
        StringConsoleReaderTest.provideInput("no");
        BooleanResponse response = BooleanConsoleReader.getBooleanData("yes", "no");
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getBooleanData_withUpperCaseNoFalseValue_okState() {
        StringConsoleReaderTest.provideInput("No");
        BooleanResponse response = BooleanConsoleReader.getBooleanData("yes", "no");
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getBooleanData_withNoTrueValueAndSpacesOutside_okState() {
        StringConsoleReaderTest.provideInput(" no ");
        BooleanResponse response = BooleanConsoleReader.getBooleanData("yes", "no");
        Assertions.assertEquals(StringResponse.States.OK, response.state);
    }

    @Test
    public void getBooleanData_withNoTrueValueAndSpacesInside_cantConvertState() {
        StringConsoleReaderTest.provideInput("n o");
        BooleanResponse response = BooleanConsoleReader.getBooleanData("yes", "no");
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }

    @Test
    public void getBooleanData_withAnotherTrueOrFalseValues_cantConvertState() {
        StringConsoleReaderTest.provideInput("another");
        BooleanResponse response = BooleanConsoleReader.getBooleanData("yes", "no");
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }

    @Test
    public void getBooleanData_withYesNullValues_cantConvertState() {
        StringConsoleReaderTest.provideInput("yes");
        BooleanResponse response = BooleanConsoleReader.getBooleanData(null, null);
        Assertions.assertEquals(StringResponse.States.NONE, response.state);
    }

    @Test
    public void getBooleanData_withEmpty_badResponseState() {
        StringConsoleReaderTest.provideInput("");
        BooleanResponse response = BooleanConsoleReader.getBooleanData();
        Assertions.assertEquals(StringResponse.States.BAD_RESPONSE, response.state);
    }

    @Test
    public void getBooleanData_withString_cantConvertState() {
        StringConsoleReaderTest.provideInput("data");
        BooleanResponse response = BooleanConsoleReader.getBooleanData();
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }

    @Test
    public void getBooleanData_withInt_cantConvertState() {
        StringConsoleReaderTest.provideInput("123");
        BooleanResponse response = BooleanConsoleReader.getBooleanData();
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }

    @Test
    public void getBooleanData_withDouble_cantConvertState() {
        StringConsoleReaderTest.provideInput("123.23");
        BooleanResponse response = BooleanConsoleReader.getBooleanData();
        Assertions.assertEquals(StringResponse.States.CANT_CONVERT, response.state);
    }
}
