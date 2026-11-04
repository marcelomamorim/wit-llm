/*
 * @test
 * @summary Test java.time.MonthDay.now(Clock) method behavior
 * @run main MonthDayWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.ZoneId;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testNowWithFixedClock();
        testNowWithSystemUTCClock();
    }

    private static void testNowWithFixedClock() {
        // Fixed instant: 2018-09-15T10:15:30Z
        Instant fixedInstant = Instant.parse("2018-09-15T10:15:30Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));

        MonthDay md = MonthDay.now(fixedClock);

        // Expected month and day
        if (md.getMonth() != Month.SEPTEMBER) {
            throw new AssertionError("Expected month SEPTEMBER but got " + md.getMonth());
        }
        if (md.getDayOfMonth() != 15) {
            throw new AssertionError("Expected day 15 but got " + md.getDayOfMonth());
        }
    }

    private static void testNowWithSystemUTCClock() {
        Clock systemUTC = Clock.systemUTC();
        MonthDay md = MonthDay.now(systemUTC);

        LocalDate now = LocalDate.now(systemUTC);

        if (md.getMonth() != now.getMonth()) {
            throw new AssertionError("MonthDay.now(systemUTC) month mismatch: expected " + now.getMonth() + " but got " + md.getMonth());
        }
        if (md.getDayOfMonth() != now.getDayOfMonth()) {
            throw new AssertionError("MonthDay.now(systemUTC) day mismatch: expected " + now.getDayOfMonth() + " but got " + md.getDayOfMonth());
        }
    }
}
