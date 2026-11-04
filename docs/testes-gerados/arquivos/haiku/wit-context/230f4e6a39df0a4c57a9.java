/*
 * @test
 * @summary Verifies that HijrahDate.now() returns a non-null date whose epoch day lies within the local system date window observed around the call
 * @run main HijrahDateWitupTest
 */

import java.time.LocalDate;
import java.time.chrono.HijrahDate;

public class HijrahDateWitupTest {
    public static void main(String[] args) {
        LocalDate before = LocalDate.now();
        HijrahDate result = HijrahDate.now();
        LocalDate after = LocalDate.now();

        if (result == null) {
            throw new AssertionError("HijrahDate.now() returned null");
        }
        if (result.getChronology() == null) {
            throw new AssertionError("HijrahDate.now() returned a date without chronology");
        }

        long epochDay = result.toEpochDay();
        if (epochDay < before.toEpochDay() || epochDay > after.toEpochDay()) {
            throw new AssertionError("Unexpected epoch day " + epochDay
                    + " not in [" + before.toEpochDay() + ", " + after.toEpochDay() + "]");
        }
    }
}
