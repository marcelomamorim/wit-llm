/* @test    @summary Test for java.time.Clock.millis() method.    @run main ClockWitupTest */

import java.time.Clock;
import java.time.Duration;
import java.util.Objects;

public class ClockWitupTest {

    public static void main(String[] args) {
        testMillisNormal();
        testMillisOverflow();
    }

    private static void testMillisNormal() {
        Clock clock = new Clock() {
            @Override
            public long millis() {
                return 1000L;
            }
        };
        long result = clock.millis();
        if (result != 1000L) {
            throw new AssertionError("Expected 1000L, but got " + result);
        }
    }

    private static void testMillisOverflow() {
        Clock baseClock = new Clock() {
            @Override
            public long millis() {
                return Long.MAX_VALUE - 10;
            }
        };
        Duration offset = Duration.ofMillis(20);
        Clock offsetClock = new Clock() {
            @Override
            public long millis() {
                return Math.addExact(baseClock.millis(), offset.toMillis());
            }
        };
        try {
            long result = offsetClock.millis();
            // Expecting an ArithmeticException due to overflow
            throw new AssertionError("Expected ArithmeticException due to overflow, but got result: " + result);
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
