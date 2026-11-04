/*
 * @test
 * @summary Test DateTimeFormatter.ofLocalizedTime with various FormatStyle values
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testOfLocalizedTimeWithFull();
        testOfLocalizedTimeWithLong();
        testOfLocalizedTimeWithMedium();
        testOfLocalizedTimeWithShort();
        testOfLocalizedTimeWithNull();
    }

    static void testOfLocalizedTimeWithFull() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL);
            if (formatter == null) {
                throw new AssertionError("Formatter should not be null for FULL style");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for FULL style", e);
        }
    }

    static void testOfLocalizedTimeWithLong() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG);
            if (formatter == null) {
                throw new AssertionError("Formatter should not be null for LONG style");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for LONG style", e);
        }
    }

    static void testOfLocalizedTimeWithMedium() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
            if (formatter == null) {
                throw new AssertionError("Formatter should not be null for MEDIUM style");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for MEDIUM style", e);
        }
    }

    static void testOfLocalizedTimeWithShort() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
            if (formatter == null) {
                throw new AssertionError("Formatter should not be null for SHORT style");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for SHORT style", e);
        }
    }

    static void testOfLocalizedTimeWithNull() {
        try {
            DateTimeFormatter.ofLocalizedTime(null);
            throw new AssertionError("Expected NullPointerException for null FormatStyle");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null FormatStyle", e);
        }
    }
}
