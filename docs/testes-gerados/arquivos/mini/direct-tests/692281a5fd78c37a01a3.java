/*
 * @test
 * @summary Test lengthOfMonth method of HijrahDate
 * @run main HijrahDateWitupTest
 */

import java.time.chrono.HijrahDate;
import java.time.DateTimeException;

public class HijrahDateWitupTest {
    public static void main(String[] args) {
        testLengthOfMonthValid();
    }

    private static void testLengthOfMonthValid() {
        // Use known valid HijrahDate values
        // Example: year=1440, month=1 (Muharram), day=1
        HijrahDate date = HijrahDate.of(1440, 1, 1);
        int length = date.lengthOfMonth();
        if (length < 29 || length > 30) {
            throw new AssertionError("lengthOfMonth() returned invalid length: " + length);
        }

        // Another month example
        HijrahDate date2 = HijrahDate.of(1440, 9, 1); // Ramadan
        int length2 = date2.lengthOfMonth();
        if (length2 < 29 || length2 > 30) {
            throw new AssertionError("lengthOfMonth() returned invalid length: " + length2);
        }
    }
}
