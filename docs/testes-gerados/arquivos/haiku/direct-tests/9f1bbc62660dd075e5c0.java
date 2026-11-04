/*
 * @test
 * @summary Verify DateTimeFormatterBuilder.parseDefaulting default application, override behavior, builder chaining and null field handling
 * @run main DateTimeFormatterBuilderWitupTest
 */
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testDefaultUsedWhenFieldAbsent();
        testParsedValueOverridesDefault();
        testReturnsSameBuilder();
        testNullFieldThrowsNullPointerException();
    }

    static void testDefaultUsedWhenFieldAbsent() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 6)
                .appendValue(ChronoField.YEAR, 4)
                .toFormatter();
        LocalDate d = LocalDate.parse("2020", f);
        check(d.equals(LocalDate.of(2020, 6, 1)),
                "defaults should fill month and day, got " + d);
    }

    static void testParsedValueOverridesDefault() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 4)
                .appendLiteral('-')
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 6)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        LocalDate d = LocalDate.parse("2020-03", f);
        check(d.equals(LocalDate.of(2020, 3, 1)),
                "parsed month should override default, got " + d);
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder r = b.parseDefaulting(ChronoField.DAY_OF_MONTH, 1);
        check(r == b, "parseDefaulting should return this builder");
    }

    static void testNullFieldThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().parseDefaulting(null, 1L);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null field should throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
