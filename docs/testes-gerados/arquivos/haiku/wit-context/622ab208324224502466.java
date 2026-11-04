/*
 * @test
 * @summary Verifies appendPattern null handling, fluent return value and observable formatting behavior
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullPatternThrowsNpe();
        testReturnsSameBuilder();
        testPatternAffectsFormatting();
        testEmptyPatternAddsNothing();
    }

    static void testNullPatternThrowsNpe() {
        try {
            new DateTimeFormatterBuilder().appendPattern(null);
            throw new RuntimeException("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendPattern("yyyy-MM-dd");
        if (result != builder) {
            throw new RuntimeException("appendPattern must return the same builder instance");
        }
    }

    static void testPatternAffectsFormatting() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .toFormatter();
        String s = f.format(LocalDate.of(2018, 9, 1));
        if (!"2018-09-01".equals(s)) {
            throw new RuntimeException("Unexpected formatted value: " + s);
        }
    }

    static void testEmptyPatternAddsNothing() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendPattern("")
                .toFormatter();
        String s = f.format(LocalDate.of(2018, 9, 1));
        if (!"".equals(s)) {
            throw new RuntimeException("Empty pattern should produce no output, got: " + s);
        }
    }
}
