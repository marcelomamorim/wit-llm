/*
 * @test
 * @summary Test java.time.YearMonth.now(Clock) method behavior
 * @run main YearMonthWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testNowWithFixedClock();
        testNowWithSystemUTCClock();
    }

    private static void testNowWithFixedClock() {
        // Fixed instant for 2018-09-01T00:00:00Z
        Instant fixedInstant = Instant.parse("2018-09-01T00:00:00Z");
        Clock fixedClock = Clock.fixed(fixedInstant, ZoneId.of("UTC"));

        YearMonth ym = YearMonth.now(fixedClock);

        // The YearMonth should be 2018-09
        if (ym.getYear() != 2018) {
            throw new AssertionError("Expected year 2018 but got " + ym.getYear());
        }
        if (ym.getMonth() != Month.SEPTEMBER) {
            throw new AssertionError("Expected month SEPTEMBER but got " + ym.getMonth());
        }
    }

    private static void testNowWithSystemUTCClock() {
        Clock systemUTC = Clock.systemUTC();
        YearMonth ym = YearMonth.now(systemUTC);

        LocalDate now = LocalDate.now(systemUTC);

        if (ym.getYear() != now.getYear()) {
            throw new AssertionError("YearMonth year " + ym.getYear() + " does not match LocalDate year " + now.getYear());
        }
        if (ym.getMonth() != now.getMonth()) {
            throw new AssertionError("YearMonth month " + ym.getMonth() + " does not match LocalDate month " + now.getMonth());
        }
    }
}
