/*
 * @test
 * @summary Teste do método until de MinguoDate para comportamento observável.
 * @run main MinguoDateWitupTest
 */

import java.time.chrono.MinguoDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.Period;

public class MinguoDateWitupTest {

    public static void main(String[] args) {
        testUntilWithValidDate();
        testUntilWithNull();
    }

    private static void testUntilWithValidDate() {
        try {
            MinguoDate startDate = MinguoDate.of(10, 5, 15);
            MinguoDate endDate = MinguoDate.of(12, 7, 20);
            ChronoPeriod period = startDate.until(endDate);
            if (period == null) {
                throw new AssertionError("Expected non-null ChronoPeriod");
            }
            System.out.println("testUntilWithValidDate passed: " + period);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testUntilWithValidDate", e);
        }
    }

    private static void testUntilWithNull() {
        try {
            MinguoDate startDate = MinguoDate.of(10, 5, 15);
            startDate.until(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("testUntilWithNull passed: Caught expected NullPointerException");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testUntilWithNull", e);
        }
    }
}
