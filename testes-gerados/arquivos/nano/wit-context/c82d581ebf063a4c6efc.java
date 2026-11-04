/*
 * @test    @summary Test for ZonedDateTime.withZoneSameLocal(ZoneId zone)
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testWithZoneSameLocal_NullZone();
        testWithZoneSameLocal_SameZone();
        testWithZoneSameLocal_DifferentZone();
    }

    private static void testWithZoneSameLocal_NullZone() {
        ZonedDateTime zdt = ZonedDateTime.now();
        try {
            zdt.withZoneSameLocal(null);
            System.out.println("FAIL: Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        }
    }

    private static void testWithZoneSameLocal_SameZone() {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zdt = ZonedDateTime.now(zone);
        ZonedDateTime result = zdt.withZoneSameLocal(zone);
        if (result.equals(zdt)) {
            System.out.println("PASS: withZoneSameLocal with same zone returns same object");
        } else {
            System.out.println("FAIL: withZoneSameLocal with same zone should return same object");
        }
    }

    private static void testWithZoneSameLocal_DifferentZone() {
        ZoneId zone1 = ZoneId.of("America/New_York");
        ZoneId zone2 = ZoneId.of("Europe/Paris");
        ZonedDateTime zdt = ZonedDateTime.of(2023, 3, 15, 10, 30, 0, 0, zone1);
        ZonedDateTime result = zdt.withZoneSameLocal(zone2);
        if (!result.equals(zdt) && result.getZone().equals(zone2)) {
            System.out.println("PASS: withZoneSameLocal with different zone returns new object with correct zone");
        } else {
            System.out.println("FAIL: withZoneSameLocal with different zone did not behave as expected");
        }
    }
}
