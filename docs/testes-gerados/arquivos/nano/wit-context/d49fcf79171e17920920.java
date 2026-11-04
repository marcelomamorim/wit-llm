/* @test    @summary Test for LocalDateTime.ofInstant method to verify exception paths and normal behavior    @run main LocalDateTimeWitupTest */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.LocalDateTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testOfInstant_NullInstant();
        testOfInstant_NullZone();
        testOfInstant_ValidInputs();
    }

    public static void testOfInstant_NullInstant() {
        try {
            LocalDateTime.ofInstant(null, ZoneId.systemDefault());
            throw new AssertionError("Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testOfInstant_NullZone() {
        Instant now = Instant.now();
        try {
            LocalDateTime.ofInstant(now, null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testOfInstant_ValidInputs() {
        Instant instant = Instant.ofEpochSecond(1609459200L, 500000000L); // 2021-01-01T00:00:00.5Z
        ZoneId zone = ZoneId.of("UTC");
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, zone);
        if (ldt == null) {
            throw new AssertionError("LocalDateTime should not be null for valid inputs");
        }
        // Additional observable checks can be added if needed
    }
}
