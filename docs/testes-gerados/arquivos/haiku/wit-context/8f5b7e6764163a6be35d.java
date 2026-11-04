/*
 * @test
 * @summary Witup-derived checks for LocalTime.parse(CharSequence)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testValidParseWithSeconds();
        testValidParseWithoutSeconds();
        testValidParseWithNanos();
        testNullTextThrowsNPE();
        testInvalidHourThrowsDateTimeParseException();
        testNonNumericTextThrowsDateTimeParseException();
        testEmptyTextThrowsDateTimeParseException();
    }

    private static void testValidParseWithSeconds() {
        LocalTime actual = LocalTime.parse("10:15:30");
        LocalTime expected = LocalTime.of(10, 15, 30);
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    private static void testValidParseWithoutSeconds() {
        LocalTime actual = LocalTime.parse("10:15");
        LocalTime expected = LocalTime.of(10, 15);
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    private static void testValidParseWithNanos() {
        LocalTime actual = LocalTime.parse("10:15:30.123456789");
        if (actual.getNano() != 123456789) {
            throw new RuntimeException("Expected nano 123456789 but got " + actual.getNano());
        }
        if (actual.getHour() != 10 || actual.getMinute() != 15 || actual.getSecond() != 30) {
            throw new RuntimeException("Unexpected time fields: " + actual);
        }
    }

    private static void testNullTextThrowsNPE() {
        try {
            LocalTime.parse((CharSequence) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null text");
    }

    private static void testInvalidHourThrowsDateTimeParseException() {
        expectParseException("25:00");
    }

    private static void testNonNumericTextThrowsDateTimeParseException() {
        expectParseException("not-a-time");
    }

    private static void testEmptyTextThrowsDateTimeParseException() {
        expectParseException("");
    }

    private static void expectParseException(String text) {
        try {
            LocalTime result = LocalTime.parse(text);
            throw new RuntimeException("Expected DateTimeParseException for '" + text + "' but got " + result);
        } catch (DateTimeParseException e) {
            return;
        }
    }
}
