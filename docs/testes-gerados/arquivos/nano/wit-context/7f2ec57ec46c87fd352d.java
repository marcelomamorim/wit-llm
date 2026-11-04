/*
 * @test
 * @summary Tests for OffsetDateTime.ofInstant method.
 * @run main OffsetDateTimeWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.OffsetDateTime;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testNullInstant();
        testNullZone();
        testValidInputs();
    }

    private static void testNullInstant() {
        try {
            OffsetDateTime.ofInstant(null, ZoneId.systemDefault());
            throw new AssertionError("Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testNullZone() {
        try {
            Instant now = Instant.now();
            OffsetDateTime.ofInstant(now, null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testValidInputs() {
        Instant instant = Instant.ofEpochSecond(1609459200L, 123456789L); // 2021-01-01T00:00:00.123456789Z
        ZoneId zone = ZoneId.of("UTC");
        OffsetDateTime odt = OffsetDateTime.ofInstant(instant, zone);
        if (odt == null) {
            throw new AssertionError("OffsetDateTime should not be null for valid inputs");
        }
        // Additional observable checks can be added here if needed
    }
}
