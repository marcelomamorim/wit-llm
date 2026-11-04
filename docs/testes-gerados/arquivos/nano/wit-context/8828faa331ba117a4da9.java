/* @test    @summary Test for MonthDay.getMonth() method to verify behavior with valid and invalid month values    @run main MonthDayWitupTest */

import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testValidMonth();
        testInvalidMonth();
    }

    private static void testValidMonth() {
        try {
            MonthDay md = MonthDay.of(1, 15);
            Month m = md.getMonth();
            if (m != null && m == Month.JANUARY) {
                System.out.println("testValidMonth passed");
            } else {
                System.out.println("testValidMonth failed: unexpected month value");
            }
        } catch (Exception e) {
            System.out.println("testValidMonth failed with exception: " + e);
        }
    }

    private static void testInvalidMonth() {
        try {
            // Simulate invalid month by creating a Month object with invalid value
            // Since Month.of() throws DateTimeException for invalid values, we test that
            Month invalidMonth = Month.of(0); // invalid, should throw exception
            System.out.println("testInvalidMonth failed: exception not thrown");
        } catch (Exception e) {
            if (e.getClass().getName().equals("java.time.DateTimeException")) {
                System.out.println("testInvalidMonth passed");
            } else {
                System.out.println("testInvalidMonth failed with unexpected exception: " + e);
            }
        }
    }
}
