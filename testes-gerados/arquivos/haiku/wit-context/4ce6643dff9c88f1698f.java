/*
 * @test
 * @summary Witup-generated checks for IsoChronology.zonedDateTime(Instant, ZoneId)
 * @run main IsoChronologyWitupTest
 */

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        IsoChronology chrono = IsoChronology.INSTANCE;

        Instant instant = Instant.ofEpochSecond(0L);
        ZonedDateTime zdt = chrono.zonedDateTime(instant, ZoneOffset.UTC);
        check(zdt != null, "result must not be null");
        check(zdt.toInstant().equals(instant), "instant must be preserved");
        check(zdt.getZone().equals(ZoneOffset.UTC), "zone must be preserved");
        check(zdt.getYear() == 1970, "year of epoch must be 1970");

        expectNpe(() -> chrono.zonedDateTime(null, ZoneOffset.UTC), "null instant");
        expectNpe(() -> chrono.zonedDateTime(instant, null), "null zone");
    }

    private static void expectNpe(Runnable r, String what) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for " + what);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
