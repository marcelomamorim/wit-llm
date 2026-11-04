/*
 * @test
 * @summary Test java.time.ZonedDateTime.ofInstant(Instant, ZoneId) method
 * @run main ZonedDateTimeWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testOfInstantBasic();
        testOfInstantNullInstant();
        testOfInstantNullZone();
    }

    private static void testOfInstantBasic() {
        Instant instant = Instant.ofEpochSecond(1000L, 123456789);
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zone);
        // Check that the returned ZonedDateTime has the same instant and zone
        if (!zdt.toInstant().equals(instant)) {
            throw new AssertionError("ZonedDateTime instant does not match input instant");
        }
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("ZonedDateTime zone does not match input zone");
        }
        // Check that the nanos are preserved
        if (zdt.getNano() != instant.getNano()) {
            throw new AssertionError("ZonedDateTime nanos do not match input instant nanos");
        }
    }

    private static void testOfInstantNullInstant() {
        try {
            ZonedDateTime.ofInstant(null, ZoneId.of("UTC"));
            throw new AssertionError("Expected NullPointerException for null instant");
        } catch (NullPointerException npe) {
            if (!"instant".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null instant");
            }
        }
    }

    private static void testOfInstantNullZone() {
        try {
            ZonedDateTime.ofInstant(Instant.now(), null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException npe) {
            if (!"zone".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null zone");
            }
        }
    }
}
