/* @test    @summary Test addMonth method of ImmutableGregorianDate class    @run main ImmutableGregorianDateWitupTest */

import sun.util.calendar.ImmutableGregorianDate;
import sun.util.calendar.BaseCalendar;

public class ImmutableGregorianDateWitupTest {

    public static void main(String[] args) {
        testAddMonthPositive();
        testAddMonthZero();
        testAddMonthNegative();
    }

    private static void testAddMonthPositive() {
        BaseCalendar.Date baseDate = new BaseCalendar.Date();
        baseDate.setYear(2023);
        baseDate.setMonth(5); // June
        baseDate.setDayOfMonth(15);
        ImmutableGregorianDate immutableDate = new ImmutableGregorianDate(baseDate);
        ImmutableGregorianDate result = immutableDate.addMonth(3);
        int month = result.getMonth();
        if (month != 8) {
            throw new AssertionError("Expected month 8, but got " + month);
        }
    }

    private static void testAddMonthZero() {
        BaseCalendar.Date baseDate = new BaseCalendar.Date();
        baseDate.setYear(2023);
        baseDate.setMonth(1); // February
        baseDate.setDayOfMonth(10);
        ImmutableGregorianDate immutableDate = new ImmutableGregorianDate(baseDate);
        ImmutableGregorianDate result = immutableDate.addMonth(0);
        int month = result.getMonth();
        if (month != 1) {
            throw new AssertionError("Expected month 1, but got " + month);
        }
    }

    private static void testAddMonthNegative() {
        BaseCalendar.Date baseDate = new BaseCalendar.Date();
        baseDate.setYear(2023);
        baseDate.setMonth(3); // April
        baseDate.setDayOfMonth(20);
        ImmutableGregorianDate immutableDate = new ImmutableGregorianDate(baseDate);
        ImmutableGregorianDate result = immutableDate.addMonth(-2);
        int month = result.getMonth();
        if (month != 1) {
            throw new AssertionError("Expected month 1, but got " + month);
        }
    }
}
