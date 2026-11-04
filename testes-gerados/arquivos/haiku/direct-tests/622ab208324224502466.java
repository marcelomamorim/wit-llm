/*
 * @test
 * @summary Verifies DateTimeFormatterBuilder.appendPattern observable behavior
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testNullPatternThrowsNPE();
        testReturnsSameBuilder();
        testDatePatternFormatsExpectedText();
        testLiteralInPatternFormatsExpectedText();
        testEmptyPatternFormatsEmptyText();
    }

    static void testNullPatternThrowsNPE() {
        boolean thrown = false;
        try {
            new DateTimeFormatterBuilder().appendPattern(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null pattern");
        }
    }

    static void testReturnsSameBuilder() {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatterBuilder result = builder.appendPattern("yyyy-MM-dd");
        if (result != builder) {
            throw new RuntimeException("appendPattern must return this builder");
        }
    }

    static void testDatePatternFormatsExpectedText() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .toFormatter();
        String s = f.format(LocalDate.of(2018, 9, 15));
        if (!"2018-09-15".equals(s)) {
            throw new RuntimeException("Unexpected output: " + s);
        }
    }

    static void testLiteralInPatternFormatsExpectedText() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd'T'HH:mm")
                .toFormatter();
        String s = f.format(LocalDateTime.of(2018, 9, 15, 10, 30));
        if (!"2018-09-15T10:30".equals(s)) {
            throw new RuntimeException("Unexpected output: " + s);
        }
    }

    static void testEmptyPatternFormatsEmptyText() {
        DateTimeFormatter f = new DateTimeFormatterBuilder()
                .appendPattern("")
                .toFormatter();
        String s = f.format(LocalDate.of(2018, 9, 15));
        if (!s.isEmpty()) {
            throw new RuntimeException("Expected empty output but got: " + s);
        }
    }
}
