/*
 * @test
 * @summary Test YearMonth.now(Clock clock) method for correct behavior
 * @run main YearMonthWitupTest
 */

import java.time.Clock;
import java.time.LocalDate;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testNowWithSystemDefaultClock();
        testNowWithFixedClock();
    }

    static void testNowWithSystemDefaultClock() {
        YearMonth ym = YearMonth.now(Clock.systemDefaultZone());
        LocalDate today = LocalDate.now();
        assert ym.getYear() == today.getYear() : "Year mismatch";
        assert ym.getMonth() == today.getMonth() : "Month mismatch";
    }

    static void testNowWithFixedClock() {
        long fixedEpochDay = 18500L; // arbitrary fixed date
        Clock fixedClock = Clock.fixed(java.time.Instant.ofEpochMilli(fixedEpochDay * 86400000L), java.time.ZoneId.systemDefault());
        YearMonth ym = YearMonth.now(fixedClock);
        LocalDate expectedDate = LocalDate.ofEpochDay(fixedEpochDay);
        assert ym.getYear() == expectedDate.getYear() : "Year mismatch";
        assert ym.getMonth() == expectedDate.getMonth() : "Month mismatch";
    }
}
