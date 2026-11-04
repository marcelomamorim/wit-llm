/*
 * @test    @summary Test HijrahDate.now(ZoneId zone) method for different ZoneIds
 * @run main HijrahDateWitupTest
 */

import java.time.ZoneId;
import java.time.chrono.HijrahDate;

public class HijrahDateWitupTest {

    public static void main(String[] args) {
        testNowWithUTC();
        testNowWithSpecificZone();
    }

    static void testNowWithUTC() {
        try {
            HijrahDate date = HijrahDate.now(ZoneId.of("UTC"));
            if (date == null) {
                throw new AssertionError("HijrahDate.now(UTC) returned null");
            }
            System.out.println("testNowWithUTC passed: " + date);
        } catch (Exception e) {
            throw new AssertionError("Exception in testNowWithUTC", e);
        }
    }

    static void testNowWithSpecificZone() {
        try {
            ZoneId zone = ZoneId.of("Asia/Tokyo");
            HijrahDate date = HijrahDate.now(zone);
            if (date == null) {
                throw new AssertionError("HijrahDate.now(Asia/Tokyo) returned null");
            }
            System.out.println("testNowWithSpecificZone passed: " + date);
        } catch (Exception e) {
            throw new AssertionError("Exception in testNowWithSpecificZone", e);
        }
    }
}
