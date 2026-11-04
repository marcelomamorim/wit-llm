/*
 * @test
 * @summary Witup generated tests for ZonedDateTime.now(Clock): null clock contract and fixed clock instant/zone observation
 * @run main ZonedDateTimeWitupTest
 */
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testNullClockThrowsNullPointerException();
        testFixedClockInstantAndZone();
    }

    static void testNullClockThrowsNullPointerException() {
        try {
            ZonedDateTime.now((Clock) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null clock");
    }

    static void testFixedClockInstantAndZone() {
        Instant instant = Instant.parse("2018-01-01T10:15:30Z");
        ZoneId zone = ZoneOffset.ofHours(2);
        Clock clock = Clock.fixed(instant, zone);

        ZonedDateTime now = ZonedDateTime.now(clock);

        if (!now.toInstant().equals(instant)) {
            throw new RuntimeException("instant mismatch: " + now.toInstant());
        }
        if (!now.getZone().equals(zone)) {
            throw new RuntimeException("zone mismatch: " + now.getZone());
        }
        if (!now.getOffset().equals(ZoneOffset.ofHours(2))) {
            throw new RuntimeException("offset mismatch: " + now.getOffset());
        }
        LocalDateTime expectedLocal = LocalDateTime.of(2018, 1, 1, 12, 15, 30);
        if (!now.toLocalDateTime().equals(expectedLocal)) {
            throw new RuntimeException("local date-time mismatch: " + now.toLocalDateTime());
        }
    }
}
