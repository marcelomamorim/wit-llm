/*
 * @test
 * @summary Witup generated checks for DateTimeFormatterBuilder.padNext(int)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testRejectsNonPositiveWidth(0);
        testRejectsNonPositiveWidth(-1);
        testRejectsNonPositiveWidth(Integer.MIN_VALUE);
        testAcceptsPositiveWidthAndReturnsSameBuilder();
        testPadsNextElement();
    }

    static void testRejectsNonPositiveWidth(int padWidth) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        try {
            builder.padNext(padWidth);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for padWidth=" + padWidth);
    }

    static void testAcceptsPositiveWidthAndReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.padNext(1);
        if (result != builder) {
            throw new RuntimeException("padNext(1) should return the same builder instance");
        }
    }

    static void testPadsNextElement() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .padNext(4)
                .appendValue(ChronoField.DAY_OF_MONTH)
                .toFormatter();
        String text = formatter.format(LocalDate.of(2018, 1, 5));
        if (!"   5".equals(text)) {
            throw new RuntimeException("Unexpected output: '" + text + "'");
        }
    }
}
