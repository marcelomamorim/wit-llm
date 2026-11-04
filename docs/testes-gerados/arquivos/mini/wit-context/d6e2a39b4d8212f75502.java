/*
 * @test
 * @summary Test OffsetDateTime.now(Clock) method behavior including null argument check
 * @run main OffsetDateTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
        System.out.println("All tests passed.");
    }

    private static void testNowWithValidClock() {
        Clock fixedClock = Clock.fixed(Instant.parse("2020-01-01T10:15:30.00Z"), ZoneId.of("UTC"));
        OffsetDateTime odt = OffsetDateTime.now(fixedClock);
        // The OffsetDateTime should represent the fixed instant with offset UTC
        if (odt.getOffset() == null) {
            throw new AssertionError("Offset should not be null");
        }
        if (!odt.toInstant().equals(fixedClock.instant())) {
            throw new AssertionError("OffsetDateTime instant does not match clock instant");
        }
    }

    private static void testNowWithNullClock() {
        try {
            OffsetDateTime.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException npe) {
            // Expected exception
            if (!"clock".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
