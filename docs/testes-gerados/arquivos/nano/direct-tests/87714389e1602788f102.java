/*
 * @test
 * @summary Test getMonth() method of YearMonth class
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;
import java.time.Month;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testGetMonth();
    }

    public static void testGetMonth() {
        YearMonth ym = YearMonth.of(2023, 1);
        Month month = ym.getMonth();
        if (month != null && month == Month.JANUARY) {
            System.out.println("Test passed: getMonth() returns JANUARY for month 1");
        } else {
            throw new AssertionError("Test failed: getMonth() did not return JANUARY for month 1");
        }
        
        // Test for a different month
        YearMonth ym2 = YearMonth.of(2023, 12);
        Month month2 = ym2.getMonth();
        if (month2 != null && month2 == Month.DECEMBER) {
            System.out.println("Test passed: getMonth() returns DECEMBER for month 12");
        } else {
            throw new AssertionError("Test failed: getMonth() did not return DECEMBER for month 12");
        }
    }
}
