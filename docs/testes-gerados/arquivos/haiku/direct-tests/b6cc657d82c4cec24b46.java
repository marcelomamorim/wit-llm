/*
 * @test
 * @summary Verifies OffsetDateTime.atZoneSameInstant preserves the instant and applies the target zone rules
 * @run main OffsetDateTimeWitupTest
 */

public class OffsetDateTimeWitupTest {
    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    public static void main(String[] args) {
        OffsetDateTime odt = OffsetDateTime.of(2018, 1, 1, 12, 0, 0, 0, ZoneOffset.ofHours(2));
        ZonedDateTime zdt = odt.atZoneSameInstant(ZoneId.of("UTC"));
        check(zdt.getHour() == 10, "UTC conversion should give hour 10, was " + zdt.getHour());
        check(zdt.getOffset().equals(ZoneOffset.UTC), "offset should be UTC, was " + zdt.getOffset());
        check(zdt.getZone().equals(ZoneId.of("UTC")), "zone should be UTC");
        check(zdt.toInstant().equals(odt.toInstant()), "instant must be preserved");

        ZonedDateTime sameOffset = odt.atZoneSameInstant(ZoneOffset.ofHours(2));
        check(sameOffset.getHour() == 12, "same offset should keep hour 12, was " + sameOffset.getHour());
        check(sameOffset.toInstant().equals(odt.toInstant()), "instant must be preserved for same offset");

        OffsetDateTime withNanos = OffsetDateTime.of(2018, 6, 15, 8, 30, 15, 123456789, ZoneOffset.ofHours(-3));
        ZonedDateTime paris = withNanos.atZoneSameInstant(ZoneId.of("Europe/Paris"));
        check(paris.getNano() == 123456789, "nanoseconds must be preserved, was " + paris.getNano());
        check(paris.getHour() == 13, "Paris summer hour should be 13, was " + paris.getHour());
        check(paris.getMinute() == 30, "Paris minute should be 30, was " + paris.getMinute());
        check(paris.toInstant().equals(withNanos.toInstant()), "instant must be preserved across zones");

        OffsetDateTime utcMidnight = OffsetDateTime.of(2018, 1, 15, 0, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime newYork = utcMidnight.atZoneSameInstant(ZoneId.of("America/New_York"));
        check(newYork.getDayOfMonth() == 14, "New York date should be 14, was " + newYork.getDayOfMonth());
        check(newYork.getHour() == 19, "New York hour should be 19, was " + newYork.getHour());
        check(newYork.getOffset().equals(ZoneOffset.ofHours(-5)), "New York January offset should be -05:00, was " + newYork.getOffset());
        check(newYork.toInstant().equals(utcMidnight.toInstant()), "instant must be preserved for New York");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
