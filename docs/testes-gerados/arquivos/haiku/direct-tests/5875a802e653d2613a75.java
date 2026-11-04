/*
 * @test
 * @summary Checks withZoneSameInstant on a ChronoZonedDateTime created through the public IsoChronology API
 * @run main ChronoZonedDateTimeImplWitupTest
 */

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.IsoChronology;

public class ChronoZonedDateTimeImplWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        ZoneId utc = ZoneOffset.UTC;
        ZoneId plus2 = ZoneOffset.ofHours(2);

        ChronoZonedDateTime<?> base =
            IsoChronology.INSTANCE.zonedDateTime(LocalDateTime.of(2020, 1, 1, 12, 0), utc);

        ChronoZonedDateTime<?> converted = base.withZoneSameInstant(plus2);
        check(converted.getZone().equals(plus2), "zone not changed");
        check(converted.toInstant().equals(base.toInstant()), "instant changed");
        check(converted.toLocalDateTime().equals(LocalDateTime.of(2020, 1, 1, 14, 0)),
              "local date-time not shifted to +02:00");

        ChronoZonedDateTime<?> roundTrip = converted.withZoneSameInstant(utc);
        check(roundTrip.getZone().equals(utc), "round trip zone wrong");
        check(roundTrip.toLocalDateTime().equals(LocalDateTime.of(2020, 1, 1, 12, 0)),
              "round trip local date-time wrong");
        check(roundTrip.toInstant().equals(base.toInstant()), "round trip instant wrong");

        check(base.withZoneSameInstant(utc) == base, "same zone should return this");

        try {
            base.withZoneSameInstant(null);
            throw new RuntimeException("null zone should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
