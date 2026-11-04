/*
 * @test
 * @summary Verifies observable padding behavior of DateTimeFormatterBuilder.padNext(int)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testPadNextPadsWithSpaces();
        testPadNextWithExactWidth();
        testPadNextAppliesOnlyToNextElement();
    }

    static void testPadNextPadsWithSpaces() {
        LocalDate date = LocalDate.of(2018, 1, 5);
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .padNext(4)
                .appendValue(ChronoField.DAY_OF_MONTH)
                .toFormatter();
        check("   5".equals(f.format(date)), "padNext(4) should pad day 5 to width 4 with spaces");
    }

    static void testPadNextWithExactWidth() {
        LocalDate date = LocalDate.of(2018, 1, 15);
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .padNext(2)
                .appendValue(ChronoField.DAY_OF_MONTH)
                .toFormatter();
        check("15".equals(f.format(date)), "padNext(2) on a two-digit value should not add padding");
    }

    static void testPadNextAppliesOnlyToNextElement() {
        LocalDate date = LocalDate.of(2018, 1, 5);
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .padNext(3)
                .appendValue(ChronoField.DAY_OF_MONTH)
                .appendLiteral('-')
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .toFormatter();
        check("  5-1".equals(f.format(date)), "padNext should apply only to the next appended value");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
