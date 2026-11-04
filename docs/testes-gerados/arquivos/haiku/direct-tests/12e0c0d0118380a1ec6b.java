/*
 * @test
 * @summary Tests public behavior of DateTimeFormatterBuilder.appendValue(TemporalField, int)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullFieldThrowsNPE();
        testInvalidWidthsThrowIAE();
        testBoundaryWidthsAccepted();
        testReturnsSameBuilder();
        testFixedWidthFormatting();
        testFixedWidthParsing();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFieldThrowsNPE() {
        try {
            new DateTimeFormatterBuilder().appendValue(null, 2);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInvalidWidthsThrowIAE() {
        int[] widths = {0, -1, 20, 100};
        for (int width : widths) {
            try {
                new DateTimeFormatterBuilder().appendValue(ChronoField.DAY_OF_MONTH, width);
                throw new RuntimeException("expected IllegalArgumentException for width " + width);
            } catch (IllegalArgumentException e) {
                check(e.getMessage().equals(
                        "The width must be from 1 to 19 inclusive but was " + width),
                        "unexpected message: " + e.getMessage());
            }
        }
    }

    static void testBoundaryWidthsAccepted() {
        new DateTimeFormatterBuilder().appendValue(ChronoField.DAY_OF_MONTH, 1);
        new DateTimeFormatterBuilder().appendValue(ChronoField.DAY_OF_MONTH, 19);
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        check(b.appendValue(ChronoField.YEAR, 4) == b, "appendValue must return this");
    }

    static void testFixedWidthFormatting() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.DAY_OF_MONTH, 3)
                .toFormatter();
        String s = f.format(LocalDate.of(2020, 1, 5));
        check(s.equals("005"), "expected 005 but was " + s);
    }

    static void testFixedWidthParsing() {
        DateTimeFormatter p = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .toFormatter();
        TemporalAccessor t = p.parse("05");
        check(t.getLong(ChronoField.DAY_OF_MONTH) == 5, "expected day 5");
    }
}
