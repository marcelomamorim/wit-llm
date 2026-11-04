/*
 * @test
 * @summary Tests DateTimeFormatter.ofPattern(String) for null rejection and basic observable formatting
 * @run main DateTimeFormatterWitupTest
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testNullPatternThrowsNullPointerException();
        testNonNullPatternReturnsFormatter();
        testSimplePatternFormatsDate();
    }

    static void testNullPatternThrowsNullPointerException() {
        try {
            DateTimeFormatter.ofPattern(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null pattern");
    }

    static void testNonNullPatternReturnsFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (formatter == null) {
            throw new RuntimeException("ofPattern returned null");
        }
    }

    static void testSimplePatternFormatsDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String result = formatter.format(LocalDate.of(2020, 1, 2));
        if (!"2020-01-02".equals(result)) {
            throw new RuntimeException("Unexpected format result: " + result);
        }
    }
}
