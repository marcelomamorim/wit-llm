/*
 * @test
 * @summary Test java.time.OffsetTime.parse(CharSequence) with valid and invalid inputs
 * @run main OffsetTimeWitupTest
 */

import java.time.OffsetTime;
import java.time.format.DateTimeParseException;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testParseValid();
        testParseInvalid();
    }

    private static void testParseValid() {
        // Valid ISO_OFFSET_TIME strings
        String[] validInputs = {
            "10:15:30+01:00",
            "10:15:30.123+01:00",
            "10:15:30.123456789+01:00",
            "23:59:59.999999999-05:00",
            "00:00:00Z"
        };
        for (String input : validInputs) {
            OffsetTime ot = OffsetTime.parse(input);
            if (ot == null) {
                throw new AssertionError("Parsing returned null for valid input: " + input);
            }
            // Check that toString contains the offset
            if (!ot.toString().contains("+") && !ot.toString().contains("-") && !ot.toString().endsWith("Z")) {
                throw new AssertionError("Parsed OffsetTime does not contain offset for input: " + input);
            }
        }
    }

    private static void testParseInvalid() {
        // Invalid inputs that should throw DateTimeParseException
        String[] invalidInputs = {
            "",
            "not-a-time",
            "25:00:00+01:00", // invalid hour
            "10:61:00+01:00", // invalid minute
            "10:15:61+01:00", // invalid second
            "10:15+01:00", // missing seconds
            "10:15:30", // missing offset
            "10:15:30+25:00", // invalid offset hour
            "10:15:30+01:60" // invalid offset minute
        };
        for (String input : invalidInputs) {
            boolean thrown = false;
            try {
                OffsetTime.parse(input);
            } catch (DateTimeParseException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected DateTimeParseException for input: " + input);
            }
        }
    }
}
