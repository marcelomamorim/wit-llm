/*
 * @test
 * @summary Verify padNext validation and padding behavior of DateTimeFormatterBuilder
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testPadWidthZeroRejected();
        testPadWidthNegativeRejected();
        testPadNextReturnsSameBuilder();
        testPadNextPadsNextField();
        testPadNextAppliesOnlyToNextField();
        testPadWidthOneDoesNotPad();
    }

    private static void testPadWidthZeroRejected() {
        try {
            new DateTimeFormatterBuilder().padNext(0, '0');
            throw new RuntimeException("Expected IllegalArgumentException for padWidth 0");
        } catch (IllegalArgumentException ex) {
            String msg = ex.getMessage();
            if (msg == null || !msg.contains("must be at least one")) {
                throw new RuntimeException("Unexpected message: " + msg, ex);
            }
        }
    }

    private static void testPadWidthNegativeRejected() {
        try {
            new DateTimeFormatterBuilder().padNext(-1, ' ');
            throw new RuntimeException("Expected IllegalArgumentException for padWidth -1");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    private static void testPadNextReturnsSameBuilder() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder r = b.padNext(3, '0');
        if (r != b) {
            throw new RuntimeException("padNext must return the same builder");
        }
    }

    private static void testPadNextPadsNextField() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .padNext(4, '0')
                .appendValue(ChronoField.DAY_OF_MONTH)
                .toFormatter();
        String s = f.format(LocalDate.of(2020, 1, 7));
        if (!"0007".equals(s)) {
            throw new RuntimeException("Expected 0007 but was " + s);
        }
    }

    private static void testPadNextAppliesOnlyToNextField() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .padNext(3, '0')
                .appendValue(ChronoField.DAY_OF_MONTH)
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .toFormatter();
        String s = f.format(LocalDate.of(2020, 1, 7));
        if (!"0071".equals(s)) {
            throw new RuntimeException("Expected 0071 but was " + s);
        }
    }

    private static void testPadWidthOneDoesNotPad() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .padNext(1, '0')
                .appendValue(ChronoField.DAY_OF_MONTH)
                .toFormatter();
        String s = f.format(LocalDate.of(2020, 1, 12));
        if (!"12".equals(s)) {
            throw new RuntimeException("Expected 12 but was " + s);
        }
    }
}
