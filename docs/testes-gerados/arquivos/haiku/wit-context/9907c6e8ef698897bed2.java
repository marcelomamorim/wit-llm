/*
 * @test
 * @summary Witup-derived checks for DateTimeFormatterBuilder.appendOptional(DateTimeFormatter)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullFormatterThrowsNPE();
        testReturnsSameBuilderInstance();
        testOptionalSectionIsFormatted();
    }

    static void testNullFormatterThrowsNPE() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean thrown = false;
        try {
            builder.appendOptional(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("appendOptional(null) should throw NullPointerException");
        }
    }

    static void testReturnsSameBuilderInstance() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendOptional(DateTimeFormatter.ofPattern("MM"));
        if (result != builder) {
            throw new RuntimeException("appendOptional should return the same builder instance");
        }
    }

    static void testOptionalSectionIsFormatted() {
        String text = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR)
                .appendOptional(DateTimeFormatter.ofPattern("MM"))
                .toFormatter()
                .format(LocalDate.of(2020, 1, 1));
        if (!"202001".equals(text)) {
            throw new RuntimeException("Unexpected formatted text: " + text);
        }
    }
}
