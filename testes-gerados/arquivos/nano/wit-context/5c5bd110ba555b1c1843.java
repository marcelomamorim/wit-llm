/*
 * @test
 * @summary Test for HijrahDate.now(ZoneId zone) method.
 * @run main HijrahDateWitupTest
 */

import java.time.ZoneId;
import java.time.chrono.HijrahDate;

public class HijrahDateWitupTest {

    public static void main(String[] args) {
        testNowWithValidZone();
        testNowWithNullZone();
    }

    private static void testNowWithValidZone() {
        try {
            ZoneId zone = ZoneId.systemDefault();
            HijrahDate date = HijrahDate.now(zone);
            if (date == null) {
                throw new AssertionError("HijrahDate.now(zone) returned null");
            }
            System.out.println("testNowWithValidZone passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNowWithValidZone", e);
        }
    }

    private static void testNowWithNullZone() {
        try {
            HijrahDate.now(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("testNowWithNullZone passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNowWithNullZone", e);
        }
    }
}
