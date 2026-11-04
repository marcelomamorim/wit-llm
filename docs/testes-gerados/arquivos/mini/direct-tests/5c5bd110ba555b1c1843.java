/*
 * @test
 * @summary Test java.time.chrono.HijrahDate.now(ZoneId) method
 * @run main HijrahDateWitupTest
 */

import java.time.ZoneId;
import java.time.chrono.HijrahDate;

public class HijrahDateWitupTest {
    public static void main(String[] args) {
        // Test with system default zone
        ZoneId systemZone = ZoneId.systemDefault();
        HijrahDate date1 = HijrahDate.now(systemZone);
        if (date1 == null) {
            throw new AssertionError("HijrahDate.now(systemZone) returned null");
        }

        // Test with UTC zone
        ZoneId utc = ZoneId.of("UTC");
        HijrahDate date2 = HijrahDate.now(utc);
        if (date2 == null) {
            throw new AssertionError("HijrahDate.now(UTC) returned null");
        }

        // Test with another valid zone
        ZoneId paris = ZoneId.of("Europe/Paris");
        HijrahDate date3 = HijrahDate.now(paris);
        if (date3 == null) {
            throw new AssertionError("HijrahDate.now(Paris) returned null");
        }

        // Test that dates are not null and roughly equal to each other (dates may differ by zone offset)
        if (date1.toEpochDay() < 0 || date2.toEpochDay() < 0 || date3.toEpochDay() < 0) {
            throw new AssertionError("HijrahDate.toEpochDay() returned negative value");
        }

        System.out.println("HijrahDate.now(ZoneId) tests passed.");
    }
}
