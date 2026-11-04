/* @test    @summary Test for Clock.offset method to verify exception paths and behavior    @run main ClockWitupTest */

import java.time.Clock;
import java.time.Duration;

public class ClockWitupTest {

    public static void main(String[] args) {
        testOffsetWithNullBaseClock();
        testOffsetWithNullDuration();
        testOffsetWithZeroDuration();
    }

    private static void testOffsetWithNullBaseClock() {
        try {
            Clock.offset(null, Duration.ofMinutes(5));
            System.err.println("Failed: Expected NullPointerException for null baseClock");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null baseClock");
        }
    }

    private static void testOffsetWithNullDuration() {
        Clock validClock = Clock.systemUTC();
        try {
            Clock.offset(validClock, null);
            System.err.println("Failed: Expected NullPointerException for null offsetDuration");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null offsetDuration");
        }
    }

    private static void testOffsetWithZeroDuration() {
        Clock validClock = Clock.systemUTC();
        Duration zeroDuration = Duration.ZERO;
        Clock resultClock = Clock.offset(validClock, zeroDuration);
        if (resultClock == validClock) {
            System.out.println("Passed: offset with zero duration returns the same clock instance");
        } else {
            System.err.println("Failed: offset with zero duration should return the same clock instance");
        }
    }
}
