/*
 * @test
 * @summary Tests for java.time.LocalTime.ofInstant(Instant, ZoneId)
 * @run main LocalTimeWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalTime;
import java.util.Objects;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testOfInstant_NullInstant();
        testOfInstant_NullZone();
        testOfInstant_ValidInputs();
    }

    private static void testOfInstant_NullInstant() {
        try {
            ZoneId zone = ZoneId.systemDefault();
            LocalTime.ofInstant(null, zone);
            throw new AssertionError("Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testOfInstant_NullZone() {
        try {
            Instant instant = Instant.now();
            LocalTime.ofInstant(instant, null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testOfInstant_ValidInputs() {
        Instant instant = Instant.ofEpochSecond(0, 0);
        ZoneId zone = ZoneId.of("UTC");
        LocalTime time = LocalTime.ofInstant(instant, zone);
        if (time == null) {
            throw new AssertionError("LocalTime.ofInstant returned null");
        }
        // Additional observable checks can be added if needed
    }
}
