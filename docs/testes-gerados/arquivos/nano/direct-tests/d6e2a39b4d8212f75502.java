/* @test    @summary Test OffsetDateTime.now(Clock) with fixed Clock    @run main OffsetDateTimeWitupTest */

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.Instant;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testNowWithFixedClock();
    }

    public static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2023-01-01T12:00:00Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneOffset.UTC);
        OffsetDateTime odt = OffsetDateTime.now(fixedClock);
        // Verify that the returned OffsetDateTime corresponds to the fixed instant and zone
        assert odt.toInstant().equals(fixedInstant) : "OffsetDateTime instant mismatch";
        assert odt.getOffset().equals(ZoneOffset.UTC) : "Offset mismatch";
        System.out.println("testNowWithFixedClock passed: " + odt);
    }
}
