/* @test    @summary Test for Clock.fixed method to verify null argument handling    @run main ClockWitupTest */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ClockWitupTest {

    public static void main(String[] args) {
        testFixed_NullInstant();
        testFixed_NullZone();
    }

    public static void testFixed_NullInstant() {
        try {
            Clock.fixed(null, ZoneId.systemDefault());
            System.err.println("testFixed_NullInstant failed: expected NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testFixed_NullZone() {
        try {
            Instant now = Instant.now();
            Clock.fixed(now, null);
            System.err.println("testFixed_NullZone failed: expected NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
