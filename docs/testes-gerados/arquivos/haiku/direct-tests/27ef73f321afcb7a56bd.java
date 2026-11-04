/*
 * @test
 * @summary Verifies LocalDateTime.plusMinutes for minute arithmetic, day and year rollover, and zero delta
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    private static int failures = 0;

    private static void check(String name, LocalDateTime actual, LocalDateTime expected) {
        if (!expected.equals(actual)) {
            failures++;
            System.err.println("FAIL " + name + ": expected " + expected + " but was " + actual);
        }
    }

    public static void main(String[] args) {
        LocalDateTime base = LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0);

        check("plus one minute",
              base.plusMinutes(1),
              LocalDateTime.of(2020, 1, 1, 0, 1));

        check("minus one minute crosses day back",
              base.plusMinutes(-1),
              LocalDateTime.of(2019, 12, 31, 23, 59));

        check("plus sixty minutes rolls hour",
              LocalDateTime.of(2020, 1, 1, 10, 30).plusMinutes(60),
              LocalDateTime.of(2020, 1, 1, 11, 30));

        check("plus ninety minutes crosses hour boundary",
              LocalDateTime.of(2020, 1, 1, 10, 30).plusMinutes(90),
              LocalDateTime.of(2020, 1, 1, 12, 0));

        check("plus one day in minutes keeps time of day",
              LocalDateTime.of(2020, 1, 1, 12, 15).plusMinutes(24 * 60),
              LocalDateTime.of(2020, 1, 2, 12, 15));

        check("new year rollover",
              LocalDateTime.of(2019, 12, 31, 23, 59).plusMinutes(1),
              LocalDateTime.of(2020, 1, 1, 0, 0));

        check("leap day backward from March 1",
              LocalDateTime.of(2020, 3, 1, 0, 30).plusMinutes(-60),
              LocalDateTime.of(2020, 2, 29, 23, 30));

        check("zero minutes keeps value",
              LocalDateTime.of(2020, 6, 15, 8, 45, 30, 123),
              LocalDateTime.of(2020, 6, 15, 8, 45, 30, 123).plusMinutes(0));

        check("preserves seconds and nanos",
              LocalDateTime.of(2020, 6, 15, 8, 45, 30, 123456789).plusMinutes(15),
              LocalDateTime.of(2020, 6, 15, 9, 0, 30, 123456789));

        if (failures > 0) {
            throw new RuntimeException(failures + " plusMinutes check(s) failed");
        }
    }
}
