/*
 * @test
 * @summary WITUP-derived tests for DateTimeFormatterBuilder.append(DateTimeFormatter)
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testAppendNullFormatterThrowsNPE();
        testAppendReturnsSameBuilder();
        testAppendedFormatterIsUsed();
    }

    static void testAppendNullFormatterThrowsNPE() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        boolean thrown = false;
        try {
            builder.append(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null formatter");
        }
    }

    static void testAppendReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.append(DateTimeFormatter.ISO_LOCAL_DATE);
        if (result != builder) {
            throw new RuntimeException("append(DateTimeFormatter) must return the same builder");
        }
    }

    static void testAppendedFormatterIsUsed() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .toFormatter();
        String text = formatter.format(LocalDate.of(2018, 9, 15));
        if (!"2018-09-15".equals(text)) {
            throw new RuntimeException("Unexpected formatted text: " + text);
        }
    }
}
