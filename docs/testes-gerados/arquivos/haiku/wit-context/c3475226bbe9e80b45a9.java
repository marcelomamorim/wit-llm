/*
 * @test
 * @summary Witup-generated checks for HijrahDate.of(int, int, int) valid and invalid arguments
 * @run main HijrahDateWitupTest
 */

import java.time.DateTimeException;
import java.time.chrono.HijrahDate;

public class HijrahDateWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void expectDateTimeException(int year, int month, int day) {
        try {
            HijrahDate d = HijrahDate.of(year, month, day);
            check(false, "expected DateTimeException for " + year + "-" + month + "-" + day + " but got " + d);
        } catch (DateTimeException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        HijrahDate d = HijrahDate.of(1437, 1, 1);
        check(d.getYear() == 1437, "year should be 1437");
        check(d.getMonthValue() == 1, "month should be 1");
        check(d.getDayOfMonth() == 1, "day should be 1");

        expectDateTimeException(1437, 0, 1);
        expectDateTimeException(1437, 13, 1);
        expectDateTimeException(1437, 1, 0);
        expectDateTimeException(1437, 1, 31);
        expectDateTimeException(1437, 2, 31);

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
