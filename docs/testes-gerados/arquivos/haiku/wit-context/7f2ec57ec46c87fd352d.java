/*
 * @test
 * @summary Witup-generated checks for OffsetDateTime.ofInstant null guards and offset derivation
 * @run main OffsetDateTimeWitupTest
 */
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testNullInstantThrowsNpe();
        testNullZoneThrowsNpe();
        testNullInstantAndZoneThrowsNpe();
        testFixedOffsetZoneDerivesOffsetAndLocalTime();
        testNanosPreservedAndInstantRoundTrip();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullInstantThrowsNpe() {
        try {
            OffsetDateTime.ofInstant(null, ZoneOffset.UTC);
            throw new RuntimeException("expected NullPointerException for null instant");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullZoneThrowsNpe() {
        Instant instant = Instant.ofEpochSecond(0L);
        try {
            OffsetDateTime.ofInstant(instant, null);
            throw new RuntimeException("expected NullPointerException for null zone");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullInstantAndZoneThrowsNpe() {
        try {
            OffsetDateTime.ofInstant(null, null);
            throw new RuntimeException("expected NullPointerException for null instant and zone");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFixedOffsetZoneDerivesOffsetAndLocalTime() {
        ZoneOffset minus3 = ZoneOffset.ofHours(-3);
        Instant epoch = Instant.ofEpochSecond(0L);
        OffsetDateTime odt = OffsetDateTime.ofInstant(epoch, minus3);
        check(odt.getOffset().equals(minus3), "offset must match fixed zone offset");
        check(odt.toLocalDateTime().equals(LocalDateTime.of(1969, 12, 31, 21, 0)),
              "local date-time must be epoch shifted by offset");
    }

    static void testNanosPreservedAndInstantRoundTrip() {
        Instant instant = Instant.ofEpochSecond(1L, 500L);
        OffsetDateTime odt = OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
        check(odt.getOffset().equals(ZoneOffset.UTC), "UTC zone must yield UTC offset");
        check(odt.getNano() == 500, "nanosecond must be preserved");
        check(odt.toInstant().equals(instant), "toInstant must return the original instant");
        ZoneId zone = ZoneOffset.ofHours(2);
        OffsetDateTime shifted = OffsetDateTime.ofInstant(instant, zone);
        check(shifted.toInstant().equals(instant), "instant must be preserved across zones");
    }
}
