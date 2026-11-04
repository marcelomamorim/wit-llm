/*
 * @test
 * @summary Test java.time.MonthDay.now(Clock) method behavior including null clock handling
 * @run main MonthDayWitupTest
 */

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    private static void testNowWithValidClock() {
        // Use fixed clock for deterministic test
        LocalDate fixedDate = LocalDate.of(2018, Month.SEPTEMBER, 25);
        Clock fixedClock = Clock.fixed(fixedDate.atStartOfDay(ZoneOffset.UTC).toInstant(), ZoneOffset.UTC);

        MonthDay md = MonthDay.now(fixedClock);

        if (md.getMonth() != fixedDate.getMonth()) {
            throw new AssertionError("MonthDay.now(clock) returned wrong month: " + md.getMonth());
        }
        if (md.getDayOfMonth() != fixedDate.getDayOfMonth()) {
            throw new AssertionError("MonthDay.now(clock) returned wrong day: " + md.getDayOfMonth());
        }
    }

    private static void testNowWithNullClock() {
        try {
            MonthDay.now(null);
            throw new AssertionError("Expected NullPointerException for null clock");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
