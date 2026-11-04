/*
 * @test
 * @summary Test ZonedDateTime.now(Clock) method for correctness.
 * @run main ZonedDateTimeWitupTest
 */
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testNowWithSystemDefaultClock();
        testNowWithFixedClock();
        testNowWithOffsetClock();
    }

    static void testNowWithSystemDefaultClock() {
        ZonedDateTime zdt = ZonedDateTime.now(Clock.systemDefaultZone());
        assert zdt != null : "ZonedDateTime.now(Clock.systemDefaultZone()) returned null";
        ZoneId zone = zdt.getZone();
        assert zone != null : "ZoneId should not be null";
        // Check that the ZonedDateTime's zone matches system default zone
        assert zone.equals(ZoneId.systemDefault()) : "Zone mismatch";
    }

    static void testNowWithFixedClock() {
        Instant fixedInstant = Instant.parse("2023-01-01T12:00:00Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));
        ZonedDateTime zdt = ZonedDateTime.now(fixedClock);
        assert zdt != null : "ZonedDateTime.now(Clock.fixed) returned null";
        assert zdt.toInstant().equals(fixedInstant) : "Instant mismatch";
        assert zdt.getZone().equals(ZoneId.of("UTC")) : "Zone mismatch";
    }

    static void testNowWithOffsetClock() {
        Instant instant = Instant.parse("2023-06-15T08:30:00Z");
        ZoneId zone = ZoneId.of("America/New_York");
        Clock offsetClock = Clock.offset(Clock.fixed(instant, ZoneId.of("UTC")), java.time.Duration.ofHours(-4));
        ZonedDateTime zdt = ZonedDateTime.now(offsetClock);
        assert zdt != null : "ZonedDateTime.now(Clock.offset) returned null";
        assert zdt.toInstant().equals(instant) : "Instant mismatch";
        assert zdt.getZone().equals(zone) : "Zone mismatch";
    }
}
