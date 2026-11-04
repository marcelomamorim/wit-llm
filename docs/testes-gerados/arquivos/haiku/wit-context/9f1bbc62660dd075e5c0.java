/*
 * @test
 * @summary Verifies parseDefaulting rejects a null field and applies defaults only when a field is absent from the parsed input
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullFieldThrowsNpe();
        testReturnsSameBuilder();
        testDefaultAppliedWhenAbsent();
        testParsedValueTakesPrecedence();
    }

    static void testNullFieldThrowsNpe() {
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().parseDefaulting(null, 1L);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null field");
        }
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder r = b.parseDefaulting(ChronoField.HOUR_OF_DAY, 12L);
        if (r != b) {
            throw new AssertionError("parseDefaulting should return the same builder");
        }
    }

    static void testDefaultAppliedWhenAbsent() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 3L)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 5L)
                .toFormatter();
        LocalDate d = LocalDate.parse("2020", f);
        if (!d.equals(LocalDate.of(2020, 3, 5))) {
            throw new AssertionError("unexpected date: " + d);
        }
    }

    static void testParsedValueTakesPrecedence() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR)
                .appendLiteral('-')
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 3L)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 5L)
                .toFormatter();
        LocalDate d = LocalDate.parse("2020-07", f);
        if (!d.equals(LocalDate.of(2020, 7, 5))) {
            throw new AssertionError("parsed month should win over default: " + d);
        }
    }
}
