/*
 * @test
 * @summary Test OffsetTime.parse(CharSequence, DateTimeFormatter) for null arguments and normal parsing
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class OffsetTimeWitupTest {
    public static void main(String[] args) throws Exception {
        testParseWithNullFormatter();
        testParseWithNullText();
        testParseWithValidInput();
    }

    private static void testParseWithNullFormatter() {
        try {
            OffsetTime.parse("10:15:30+01:00", null);
            throw new AssertionError("Expected NullPointerException for null formatter");
        } catch (NullPointerException npe) {
            // Expected
        }
    }

    private static void testParseWithNullText() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_TIME;
        OffsetTime result = OffsetTime.parse(null, formatter);
        if (result != null) {
            throw new AssertionError("Expected null result when parsing null text with non-null formatter");
        }
    }

    private static void testParseWithValidInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_TIME;
        String validTime = "10:15:30+01:00";
        OffsetTime ot = OffsetTime.parse(validTime, formatter);
        if (ot == null) {
            throw new AssertionError("Parsing valid input returned null");
        }
        // Check that the parsed time matches expected hour, minute, second, offset
        if (ot.getHour() != 10 || ot.getMinute() != 15 || ot.getSecond() != 30) {
            throw new AssertionError("Parsed time fields do not match expected values");
        }
        if (!"+01:00".equals(ot.getOffset().toString())) {
            throw new AssertionError("Parsed offset does not match expected value");
        }
    }
}
