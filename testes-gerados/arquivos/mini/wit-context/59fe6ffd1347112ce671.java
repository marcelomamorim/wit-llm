/*
 * @test
 * @summary Test OffsetTime.ofInstant(Instant, ZoneId) for null checks and basic correctness
 * @run main OffsetTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.Objects;

public class OffsetTimeWitupTest {
    public static void main(String[] args) {
        testNullInstant();
        testNullZone();
        testBasicOfInstant();
    }

    private static void testNullInstant() {
        try {
            OffsetTime.ofInstant(null, ZoneId.of("UTC"));
            throw new AssertionError("Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullZone() {
        Instant now = Instant.now();
        try {
            OffsetTime.ofInstant(now, null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testBasicOfInstant() {
        Instant instant = Instant.ofEpochSecond(0L, 0);
        ZoneId zone = ZoneId.of("UTC");
        OffsetTime ot = OffsetTime.ofInstant(instant, zone);
        if (ot == null) {
            throw new AssertionError("OffsetTime.ofInstant returned null");
        }
        // The offset should be zero for UTC
        if (!ot.getOffset().equals(ZoneOffset.UTC)) {
            throw new AssertionError("Expected offset UTC but got " + ot.getOffset());
        }
        // The time should be midnight
        if (!ot.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            throw new AssertionError("Expected LocalTime midnight but got " + ot.toLocalTime());
        }

        // Test with a non-UTC zone with offset +02:00
        ZoneId zonePlus2 = ZoneOffset.ofHours(2);
        OffsetTime ot2 = OffsetTime.ofInstant(instant, zonePlus2);
        if (!ot2.getOffset().equals(zonePlus2)) {
            throw new AssertionError("Expected offset +02:00 but got " + ot2.getOffset());
        }
        // The local time should be 02:00:00
        if (!ot2.toLocalTime().equals(LocalTime.of(2, 0))) {
            throw new AssertionError("Expected LocalTime 02:00 but got " + ot2.toLocalTime());
        }
    }
}
