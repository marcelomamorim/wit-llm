/*
 * @test
 * @summary Verifies that HijrahDate.now() returns the current date in the system default zone
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
            throw new AssertionError("now() returned null");
        }

        long low = HijrahDate.from(before).toEpochDay();
        long high = HijrahDate.from(after).toEpochDay();
        long actual = result.toEpochDay();

        if (actual < low || actual > high) {
            throw new AssertionError("now() returned " + actual
                    + ", expected between " + low + " and " + high);
        }

        HijrahDate again = HijrahDate.from(result);
        if (again.toEpochDay() != actual) {
            throw new AssertionError("from(now()) mismatch: " + again.toEpochDay()
                    + " != " + actual);
        }
    }
}
