/*
 * @test
 * @summary Verifies DateTimeFormatterBuilder.appendInstant argument validation and observable Instant formatting
 * @run main DateTimeFormatterBuilderWitupTest
 */

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testValidDigitsReturnSameBuilder();
        testInvalidDigitsThrow();
        testFractionalDigitsFormatting();
        testVariableDigitsFormatting();
    }

    static void testValidDigitsReturnSameBuilder() {
        DateTimeFormatterBuilder b = new DateTimeFormatterBuilder();
        for (int d = -1; d <= 9; d++) {
            if (b.appendInstant(d) != b) {
                throw new AssertionError("appendInstant(" + d + ") must return this");
            }
        }
    }

    static void testInvalidDigitsThrow() {
        int[] invalid = {-2, -100, 10, 100};
        for (int d : invalid) {
            boolean thrown = false;
            try {
                new DateTimeFormatterBuilder().appendInstant(d);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException for " + d);
            }
        }
    }

    static void testFractionalDigitsFormatting() {
        DateTimeFormatter f = new DateTimeFormatterBuilder().appendInstant(3).toFormatter();
        Instant i = Instant.ofEpochSecond(0, 123000000L);
        String s = f.format(i);
        if (!"1970-01-01T00:00:00.123Z".equals(s)) {
            throw new AssertionError("Unexpected output: " + s);
        }
    }

    static void testVariableDigitsFormatting() {
        DateTimeFormatter f = new DateTimeFormatterBuilder().appendInstant(-1).toFormatter();
        Instant i = Instant.ofEpochSecond(0);
        String s = f.format(i);
        if (!"1970-01-01T00:00:00Z".equals(s)) {
            throw new AssertionError("Unexpected output: " + s);
        }
    }
}
