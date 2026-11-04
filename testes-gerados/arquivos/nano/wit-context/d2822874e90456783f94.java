/* @test    @summary Test for Clock.system(ZoneId zone) method to verify behavior with null input and valid ZoneId.    @run main ClockWitupTest */

import java.time.Clock;
import java.time.ZoneId;

public class ClockWitupTest {

    public static void main(String[] args) {
        testSystemWithNullZone();
        testSystemWithUTCZone();
        testSystemWithCustomZone();
    }

    private static void testSystemWithNullZone() {
        boolean exceptionThrown = false;
        try {
            Clock c = Clock.system(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to system() method");
        }
    }

    private static void testSystemWithUTCZone() {
        Clock clock = Clock.system(ZoneId.of("UTC"));
        if (clock == null) {
            throw new AssertionError("Clock should not be null for UTC zone");
        }
        // Additional observable behavior checks can be added if needed
    }

    private static void testSystemWithCustomZone() {
        ZoneId zone = ZoneId.of("America/New_York");
        Clock clock = Clock.system(zone);
        if (clock == null) {
            throw new AssertionError("Clock should not be null for custom zone");
        }
        // Additional observable behavior checks can be added if needed
    }
}
