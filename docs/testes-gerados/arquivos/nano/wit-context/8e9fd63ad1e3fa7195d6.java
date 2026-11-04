/*
 * @test
 * @summary Tests for java.time.chrono.JapaneseDate.until(ChronoLocalDate)
 * @run main JapaneseDateWitupTest
 */

import java.time.chrono.JapaneseDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.Period;
import java.time.LocalDate;

public class JapaneseDateWitupTest {

    public static void main(String[] args) {
        testUntilWithNullEndDate();
        testUntilWithValidDate();
    }

    private static void testUntilWithNullEndDate() {
        try {
            JapaneseDate date = JapaneseDate.of(2019, 5, 20);
            date.until(null);
            System.err.println("Failed: Expected NullPointerException for null endDate");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null endDate");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testUntilWithValidDate() {
        try {
            JapaneseDate date1 = JapaneseDate.of(2019, 5, 20);
            JapaneseDate date2 = JapaneseDate.of(2020, 5, 20);
            ChronoPeriod period = date1.until(date2);
            if (period != null && period.getYears() == 1 && period.getMonths() == 0 && period.getDays() == 0) {
                System.out.println("Passed: until() returns correct period for valid dates");
            } else {
                System.err.println("Failed: until() returned incorrect period: " + period);
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
