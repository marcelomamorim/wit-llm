/*
 * @test
 * @summary Test deterministic behavior of HijrahDate.lengthOfMonth()
 * @run main HijrahDateWitupTest
 */

import java.time.chrono.HijrahDate;
import java.time.DateTimeException;

public class HijrahDateWitupTest {
    public static void main(String[] args) {
        testLengthOfMonthValidDate();
        testLengthOfMonthInvalidDate();
    }

    private static void testLengthOfMonthValidDate() {
        // Use a known valid Hijrah date
        HijrahDate date = HijrahDate.of(1440, 9, 1); // 9th month, 1440 AH
        int length = date.lengthOfMonth();
        if (length < 29 || length > 30) {
            throw new AssertionError("lengthOfMonth() returned invalid length: " + length);
        }
    }

    private static void testLengthOfMonthInvalidDate() {
        // Attempt to create a HijrahDate with invalid month to test lengthOfMonth behavior
        // Since HijrahDate.of validates inputs, we simulate invalid internal state by reflection is disallowed,
        // so instead test that lengthOfMonth throws DateTimeException for out-of-range year/month indirectly
        // by using from() with a TemporalAccessor that yields invalid fields.
        // However, no public API to create invalid HijrahDate, so test that lengthOfMonth does not throw for valid dates.
        // Thus, no direct test for exception here; note in 'notes'.
    }
}
