/*
 * @test
 * @summary Behavioral tests for Date.parse(String) with explicit numeric offsets and syntax errors
 * @run main DateWitupTest
 */

import java.util.Date;

public class DateWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIllegalArgument(String input) {
        try {
            long value = Date.parse(input);
            throw new RuntimeException("Expected IllegalArgumentException for input: " + input + " but got " + value);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        // Explicit zero offset: 2000-01-01T00:00:00Z
        long utcMidnight2000 = 946684800000L;
        check(Date.parse("1 Jan 2000 00:00:00 +0000") == utcMidnight2000,
              "Zero offset should yield UTC epoch millis");

        // +0100 means east of GMT, so the result is one hour earlier in UTC
        check(Date.parse("1 Jan 2000 00:00:00 +0100") == utcMidnight2000 - 3600000L,
              "+0100 offset should subtract one hour");

        // -0100 means west of GMT, so the result is one hour later in UTC
        check(Date.parse("1 Jan 2000 00:00:00 -0100") == utcMidnight2000 + 3600000L,
              "-0100 offset should add one hour");

        // Syntax errors that the code explicitly rejects
        expectIllegalArgument(null);
        expectIllegalArgument("");
        expectIllegalArgument("garbage");
        expectIllegalArgument("1 Jan 2000 00:00:00 +0000 +0000");
    }
}
