/*
 * @test
 * @summary Verifies HijrahDate.lengthOfMonth returns valid month lengths consistent with day-of-month validation
 * @run main HijrahDateWitupTest
 */

import java.time.DateTimeException;
import java.time.chrono.HijrahDate;

public class HijrahDateWitupTest {

    public static void main(String[] args) {
        int[][] samples = {{1400, 1}, {1434, 1}, {1434, 9}, {1434, 12}, {1500, 6}};
        for (int[] s : samples) {
            int y = s[0];
            int m = s[1];
            HijrahDate first = HijrahDate.of(y, m, 1);
            int len = first.lengthOfMonth();

            if (len != 29 && len != 30) {
                throw new RuntimeException("Unexpected month length " + len + " for " + y + "-" + m);
            }
            if (first.lengthOfMonth() != len) {
                throw new RuntimeException("lengthOfMonth not stable for " + y + "-" + m);
            }
            if (HijrahDate.of(y, m, 15).lengthOfMonth() != len) {
                throw new RuntimeException("lengthOfMonth differs within month " + y + "-" + m);
            }
            if (HijrahDate.of(y, m, len).lengthOfMonth() != len) {
                throw new RuntimeException("lengthOfMonth differs on last day " + y + "-" + m);
            }

            boolean rejected = false;
            try {
                HijrahDate.of(y, m, len + 1);
            } catch (DateTimeException e) {
                rejected = true;
            }
            if (!rejected) {
                throw new RuntimeException("Day beyond month length accepted for " + y + "-" + m);
            }
        }
    }
}
