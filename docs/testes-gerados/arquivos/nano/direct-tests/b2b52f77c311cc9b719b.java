/*
 * @test
 * @summary Test getDayOfWeek() method of LocalDate for various dates.
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.DayOfWeek;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testGetDayOfWeek(2023, 1, 1); // Sunday
        testGetDayOfWeek(2023, 1, 2); // Monday
        testGetDayOfWeek(2023, 1, 3); // Tuesday
        testGetDayOfWeek(2023, 1, 4); // Wednesday
        testGetDayOfWeek(2023, 1, 5); // Thursday
        testGetDayOfWeek(2023, 1, 6); // Friday
        testGetDayOfWeek(2023, 1, 7); // Saturday
        testGetDayOfWeek(2000, 2, 29); // Leap year, Wednesday
        testGetDayOfWeek(1999, 12, 31); // Friday
        testGetDayOfWeek(2024, 2, 29); // Future leap year, Thursday
    }

    private static void testGetDayOfWeek(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        DayOfWeek dow = date.getDayOfWeek();
        int epochDay = (int) date.toEpochDay();
        int dow0 = Math.floorMod(epochDay + 3, 7);
        DayOfWeek expected = DayOfWeek.of(dow0 + 1);
        if (dow != expected) {
            throw new AssertionError("Failed for date: " + date + ", expected: " + expected + ", but got: " + dow);
        }
    }
}
