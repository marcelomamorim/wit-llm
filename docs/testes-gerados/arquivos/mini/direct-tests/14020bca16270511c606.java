/*
 * @test
 * @summary Test LocalDate.getMonth() returns correct Month enum
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testGetMonthReturnsCorrectMonth();
        testGetMonthForJanuary();
        testGetMonthForDecember();
    }

    private static void testGetMonthReturnsCorrectMonth() {
        LocalDate date = LocalDate.of(2018, 5, 15); // May
        Month month = date.getMonth();
        if (month != Month.MAY) {
            throw new AssertionError("Expected MAY but got " + month);
        }
    }

    private static void testGetMonthForJanuary() {
        LocalDate date = LocalDate.of(2020, 1, 1); // January
        Month month = date.getMonth();
        if (month != Month.JANUARY) {
            throw new AssertionError("Expected JANUARY but got " + month);
        }
    }

    private static void testGetMonthForDecember() {
        LocalDate date = LocalDate.of(1999, 12, 31); // December
        Month month = date.getMonth();
        if (month != Month.DECEMBER) {
            throw new AssertionError("Expected DECEMBER but got " + month);
        }
    }
}
