/*
 * @test
 * @summary Tests for java.time.LocalDate.until(ChronoLocalDate)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testUntilWithNull();
        testUntilWithSameDate();
        testUntilWithDifferentDates();
    }

    private static void testUntilWithNull() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        try {
            date.until(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testUntilWithSameDate() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        ChronoLocalDate sameDate = LocalDate.of(2020, 1, 1);
        Period result = date.until(sameDate);
        if (result.equals(Period.of(0, 0, 0))) {
            System.out.println("PASS: until with same date returns zero period");
        } else {
            System.out.println("FAIL: until with same date returned: " + result);
        }
    }

    private static void testUntilWithDifferentDates() {
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 3, 15);
        Period result = startDate.until(endDate);
        // Expected: 1 year, 2 months, 14 days
        if (result.equals(Period.of(1, 2, 14))) {
            System.out.println("PASS: until with different dates returns expected period");
        } else {
            System.out.println("FAIL: until with different dates returned: " + result);
        }
    }
}
