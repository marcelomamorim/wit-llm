/*
 * @test
 * @summary Test MonthDay.now(Clock) method for correct behavior
 * @run main MonthDayWitupTest
 */

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testNowWithSystemDefaultClock();
        testNowWithFixedClock();
    }

    static void testNowWithSystemDefaultClock() {
        MonthDay result = MonthDay.now(Clock.systemDefaultZone());
        LocalDate today = LocalDate.now();
        assert result.equals(MonthDay.of(today.getMonth(), today.getDayOfMonth())) : "Failed: system default clock";
    }

    static void testNowWithFixedClock() {
        LocalDate fixedDate = LocalDate.of(2020, Month.DECEMBER, 25);
        Clock fixedClock = Clock.fixed(fixedDate.atStartOfDay().toInstant(), fixedDate.atZone().getZone());
        MonthDay result = MonthDay.now(fixedClock);
        assert result.equals(MonthDay.of(Month.DECEMBER, 25)) : "Failed: fixed clock";
    }
}
