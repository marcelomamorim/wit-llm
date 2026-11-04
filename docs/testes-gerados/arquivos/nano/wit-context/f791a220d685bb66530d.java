/* @test    @summary Test for TemporalAdjusters.firstInMonth(DayOfWeek) method to verify behavior with null input.    @run main TemporalAdjustersWitupTest */

import static java.time.temporal.TemporalAdjusters.firstInMonth;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import java.time.temporal.TemporalAdjuster;
import java.time.DayOfWeek;

public class TemporalAdjustersWitupTest {

    public static void main(String[] args) {
        testFirstInMonthWithNull();
        testFirstInMonthWithValidDay();
    }

    private static void testFirstInMonthWithNull() {
        try {
            firstInMonth(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testFirstInMonthWithValidDay() {
        try {
            TemporalAdjuster adjuster = firstInMonth(MONDAY);
            if (adjuster != null) {
                System.out.println("PASS: Valid TemporalAdjuster returned");
            } else {
                System.out.println("FAIL: Null TemporalAdjuster returned");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception thrown: " + e);
        }
    }
}
