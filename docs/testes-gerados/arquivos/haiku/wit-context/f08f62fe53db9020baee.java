/*
 * @test
 * @summary Witup-generated checks for Date.parse(String) exception paths and a basic GMT parse
 * @run main DateWitupTest
 */

import java.util.Date;

public class DateWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    static void expectIllegalArgument(String input, String label) {
        try {
            Date.parse(input);
            check(false, label + " should throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        expectIllegalArgument(null, "null input");
        expectIllegalArgument("", "empty input");
        expectIllegalArgument("@@@", "unsupported characters");
        expectIllegalArgument("1 Jan", "missing year");

        long expected = 946684800000L;
        long actual = Date.parse("1 Jan 2000 00:00:00 GMT");
        check(actual == expected, "GMT parse expected " + expected + " but was " + actual);

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
