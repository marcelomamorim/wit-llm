/*
 * @test
 * @summary Verifies observable behavior of LocalDate.getMonth() for all months and month boundaries
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        Month[] months = Month.values();
        for (int i = 0; i < months.length; i++) {
            Month expected = months[i];
            LocalDate byEnum = LocalDate.of(2019, expected, 1);
            check(byEnum.getMonth() == expected, "of(int, Month, int) month mismatch for " + expected);
            LocalDate byInt = LocalDate.of(2019, i + 1, 1);
            check(byInt.getMonth() == expected, "of(int, int, int) month mismatch for " + expected);
            check(byInt.getMonth().getValue() == i + 1, "getValue mismatch for " + expected);
        }

        check(LocalDate.from(LocalDate.of(2000, 2, 29)).getMonth() == Month.FEBRUARY,
              "from(...) month mismatch");
        check(LocalDate.of(2020, 1, 31).plusDays(1).getMonth() == Month.FEBRUARY,
              "month rollover Jan->Feb failed");
        check(LocalDate.of(2020, 12, 31).plusDays(1).getMonth() == Month.JANUARY,
              "month rollover Dec->Jan failed");
        check(LocalDate.of(2020, 12, 15).getMonth().getValue() == 12,
              "December value mismatch");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
