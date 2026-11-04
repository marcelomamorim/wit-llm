/*
 * @test
 * @summary Tests the static parse method of OffsetTime for various valid and invalid inputs.
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OffsetTimeWitupTest {

    public static void main(String[] args) {
        testParseValidInputs();
        testParseInvalidInputs();
    }

    static void testParseValidInputs() {
        // Test with ISO_OFFSET_TIME format
        String validTime1 = "10:15:30+01:00";
        OffsetTime result1 = OffsetTime.parse(validTime1);
        assert result1 != null : "Parsing valid ISO_OFFSET_TIME string failed";
        assert result1.getHour() == 10 : "Hour mismatch";
        assert result1.getMinute() == 15 : "Minute mismatch";
        assert result1.getSecond() == 30 : "Second mismatch";
        assert result1.getOffset().getTotalSeconds() == 3600 : "Offset seconds mismatch";

        // Test with different valid offset
        String validTime2 = "23:59:59-02:30";
        OffsetTime result2 = OffsetTime.parse(validTime2);
        assert result2 != null : "Parsing valid offset string failed";
        assert result2.getHour() == 23 : "Hour mismatch";
        assert result2.getMinute() == 59 : "Minute mismatch";
        assert result2.getSecond() == 59 : "Second mismatch";
        assert result2.getOffset().getTotalSeconds() == -9000 : "Offset seconds mismatch";
    }

    static void testParseInvalidInputs() {
        // Missing offset
        String invalidTime1 = "10:15:30";
        try {
            OffsetTime.parse(invalidTime1);
            assert false : "Expected DateTimeParseException for missing offset";
        } catch (DateTimeParseException e) {
            // expected
        }
        // Invalid format
        String invalidTime2 = "25:61:61+01:00";
        try {
            OffsetTime.parse(invalidTime2);
            assert false : "Expected DateTimeParseException for invalid time";
        } catch (DateTimeParseException e) {
            // expected
        }
        // Invalid offset
        String invalidTime3 = "10:15:30+25:00";
        try {
            OffsetTime.parse(invalidTime3);
            assert false : "Expected DateTimeParseException for invalid offset";
        } catch (DateTimeParseException e) {
            // expected
        }
    }
}
