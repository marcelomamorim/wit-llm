/* @test    @summary Test Integer.parseInt with valid decimal strings    @run main IntegerWitupTest */

import java.lang.Integer;

public class IntegerWitupTest {
    public static void main(String[] args) {
        testParseIntValid();
        testParseIntInvalid();
    }

    static void testParseIntValid() {
        int value1 = Integer.parseInt("0");
        assert value1 == 0 : "Expected 0, got " + value1;
        int value2 = Integer.parseInt("12345");
        assert value2 == 12345 : "Expected 12345, got " + value2;
        int value3 = Integer.parseInt("-987");
        assert value3 == -987 : "Expected -987, got " + value3;
        int value4 = Integer.parseInt("2147483647"); // MAX_VALUE
        assert value4 == Integer.MAX_VALUE : "Expected MAX_VALUE, got " + value4;
        int value5 = Integer.parseInt("-2147483648"); // MIN_VALUE
        assert value5 == Integer.MIN_VALUE : "Expected MIN_VALUE, got " + value5;
    }

    static void testParseIntInvalid() {
        boolean exceptionThrown = false;
        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NumberFormatException for 'abc";

        exceptionThrown = false;
        try {
            Integer.parseInt("");
        } catch (NumberFormatException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NumberFormatException for empty string";

        exceptionThrown = false;
        try {
            Integer.parseInt("123abc");
        } catch (NumberFormatException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NumberFormatException for '123abc";

        exceptionThrown = false;
        try {
            Integer.parseInt("  ");
        } catch (NumberFormatException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NumberFormatException for whitespace";
    }
}
