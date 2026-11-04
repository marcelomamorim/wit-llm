/* @test    @summary Test for HijrahDate.now() method to observe normal behavior and exception paths    @run main HijrahDateWitupTest */

import java.time.Clock;
import java.time.chrono.HijrahDate;
import java.time.DateTimeException;

public class HijrahDateWitupTest {

    public static void main(String[] args) {
        testNowNormal();
        testNowWithInvalidClock();
    }

    static void testNowNormal() {
        try {
            HijrahDate date = HijrahDate.now();
            System.out.println("HijrahDate.now() returned: " + date);
        } catch (Exception e) {
            System.err.println("Unexpected exception in testNowNormal: " + e);
        }
    }

    static void testNowWithInvalidClock() {
        Clock invalidClock = new Clock() {
            @Override
            public ZoneId getZone() {
                return ZoneId.systemDefault();
            }

            @Override
            public Clock withZone(ZoneId zone) {
                return this;
            }

            @Override
            public Instant instant() {
                throw new DateTimeException("Invalid clock for testing");
            }
        };
        try {
            HijrahDate.now(invalidClock);
            System.err.println("Expected exception not thrown in testNowWithInvalidClock");
        } catch (DateTimeException e) {
            System.out.println("Caught expected DateTimeException in testNowWithInvalidClock: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected exception in testNowWithInvalidClock: " + e);
        }
    }
}
