/*
 * @test
 * @summary Verifies DateTimeFormatterBuilder.appendInstant argument validation and observable formatting behavior
 * @run main DateTimeFormatterBuilderWitupTest
 */
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterBuilderWitupTest {

    public static void main(String[] args) {
        testInvalidFractionalDigitsThrow();
        testValidFractionalDigitsReturnSameBuilder();
        testFormattingWithThreeFractionalDigits();
    }

    static void testInvalidFractionalDigitsThrow() {
        int[] invalid = {-2, -100, 10, 100};
        for (int digits : invalid) {
            boolean thrown = false;
            try {
                new DateTimeFormatterBuilder().appendInstant(digits);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for fractionalDigits=" + digits);
            }
        }
    }

    static void testValidFractionalDigitsReturnSameBuilder() {
        for (int digits = -1; digits <= 9; digits++) {
            DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
            DateTimeFormatterBuilder result = builder.appendInstant(digits);
            if (result != builder) {
                throw new RuntimeException("appendInstant must return this builder for fractionalDigits=" + digits);
            }
        }
    }

    static void testFormattingWithThreeFractionalDigits() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendInstant(3).toFormatter();
        String actual = formatter.format(Instant.ofEpochSecond(0, 123456789));
        String expected = "1970-01-01T00:00:00.123Z";
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
