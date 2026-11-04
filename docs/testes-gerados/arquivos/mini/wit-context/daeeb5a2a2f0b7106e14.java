/*
 * @test
 * @summary Test YearMonth.now(Clock) method behavior including null clock handling
 * @run main YearMonthWitupTest
 */

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    private static void testNowWithValidClock() {
        // Use a fixed clock for deterministic test
        Clock fixedClock = Clock.fixed(LocalDate.of(2018, Month.SEPTEMBER, 15).atStartOfDay(ZoneOffset.UTC).toInstant(), ZoneOffset.UTC);
        YearMonth ym = YearMonth.now(fixedClock);
        if (ym.getYear() != 2018) {
            throw new AssertionError("YearMonth.now(clock) returned wrong year: " + ym.getYear());
        }
        if (ym.getMonth() != Month.SEPTEMBER) {
            throw new AssertionError("YearMonth.now(clock) returned wrong month: " + ym.getMonth());
        }
    }

    private static void testNowWithNullClock() {
        try {
            YearMonth.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
