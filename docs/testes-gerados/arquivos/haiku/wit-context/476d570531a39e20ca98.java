/*
 * @test
 * @summary Witup-derived checks for ZonedDateTime.withZoneSameInstant null guard, same-zone identity and instant preservation
 * @run main ZonedDateTimeWitupTest
 */
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        ZoneId utc = ZoneId.of("UTC");
        ZoneId paris = ZoneId.of("Europe/Paris");
        ZonedDateTime base = ZonedDateTime.of(2018, 6, 1, 12, 0, 0, 0, utc);

        boolean thrown = false;
        try {
            base.withZoneSameInstant(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null zone");
        }

        if (base.withZoneSameInstant(utc) != base) {
            throw new AssertionError("same zone should return this");
        }

        ZonedDateTime converted = base.withZoneSameInstant(paris);
        if (!converted.toInstant().equals(base.toInstant())) {
            throw new AssertionError("instant must be preserved");
        }
        if (!converted.getZone().equals(paris)) {
            throw new AssertionError("zone must be the requested zone");
        }
        if (converted.getHour() != 14) {
            throw new AssertionError("expected local hour 14 in Europe/Paris, got " + converted.getHour());
        }
    }
}
