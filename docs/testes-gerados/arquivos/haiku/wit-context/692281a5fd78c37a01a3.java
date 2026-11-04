/*
 * @test
 * @summary Verifies HijrahDate.lengthOfMonth() returns a valid Hijrah month length and agrees with day-of-month validation
 * @run main HijrahDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.HijrahDate;

public class HijrahDateWitupTest {
    public static void main(String[] args) {
        for (int month = 1; month <= 12; month++) {
            HijrahDate first = HijrahDate.of(1434, month, 1);
            int len = first.lengthOfMonth();
            if (len != 29 && len != 30) {
                throw new RuntimeException("Unexpected month length " + len + " for month " + month);
            }
            HijrahDate last = HijrahDate.of(1434, month, len);
            if (last.lengthOfMonth() != len) {
                throw new RuntimeException("lengthOfMonth inconsistent for month " + month);
            }
            try {
                HijrahDate.of(1434, month, len + 1);
                throw new RuntimeException("Expected DateTimeException for day " + (len + 1) + " in month " + month);
            } catch (DateTimeException expected) {
            }
        }
        HijrahDate fromDate = HijrahDate.from(LocalDate.of(2019, 1, 1));
        int fromLen = fromDate.lengthOfMonth();
        if (fromLen != 29 && fromLen != 30) {
            throw new RuntimeException("Unexpected month length from LocalDate: " + fromLen);
        }
    }
}
