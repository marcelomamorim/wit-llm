/*
 * @test
 * @summary Test for IsoChronology.zonedDateTime(Instant, ZoneId) method.
 * @run main IsoChronologyWitupTest
 */

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testZonedDateTimeWithNullInstant();
        testZonedDateTimeWithNullZone();
    }

    public static void testZonedDateTimeWithNullInstant() {
        try {
            ZoneId zone = ZoneId.systemDefault();
            IsoChronology.zonedDateTime(null, zone);
            System.out.println("Failed: Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null instant");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception for null instant: " + e);
        }
    }

    public static void testZonedDateTimeWithNullZone() {
        try {
            Instant instant = Instant.now();
            IsoChronology.zonedDateTime(instant, null);
            System.out.println("Failed: Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null zone");
        } catch (Exception e) {
            System.out.println("Failed: Unexpected exception for null zone: " + e);
        }
    }
}
