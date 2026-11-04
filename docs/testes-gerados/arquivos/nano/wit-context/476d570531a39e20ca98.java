/*
 * @test
 * @summary Tests for ZonedDateTime.withZoneSameInstant(ZoneId zone)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testWithZoneSameInstant_NullZone();
        testWithZoneSameInstant_SameZone();
        testWithZoneSameInstant_DifferentZone();
    }

    static void testWithZoneSameInstant_NullZone() {
        ZonedDateTime zdt = ZonedDateTime.now();
        try {
            zdt.withZoneSameInstant(null);
            System.err.println("Expected NullPointerException for null zone");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testWithZoneSameInstant_SameZone() {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zdt = ZonedDateTime.now(zone);
        ZonedDateTime result = zdt.withZoneSameInstant(zone);
        if (result != zdt) {
            System.err.println("Expected same instance when zone is unchanged");
            System.exit(1);
        }
    }

    static void testWithZoneSameInstant_DifferentZone() {
        ZoneId zone1 = ZoneId.of("America/New_York");
        ZoneId zone2 = ZoneId.of("Asia/Tokyo");
        ZonedDateTime zdt = ZonedDateTime.now(zone1);
        ZonedDateTime result = zdt.withZoneSameInstant(zone2);
        if (result == zdt) {
            System.err.println("Expected different instance when zone changes");
            System.exit(1);
        }
        if (!result.getZone().equals(zone2)) {
            System.err.println("Result zone does not match expected zone");
            System.exit(1);
        }
        if (!result.toEpochSecond() == zdt.toEpochSecond()) {
            System.err.println("Epoch seconds should be equal for same instant");
            System.exit(1);
        }
    }
}
