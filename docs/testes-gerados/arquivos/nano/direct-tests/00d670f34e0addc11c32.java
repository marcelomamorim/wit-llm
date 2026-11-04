/* @test    @summary Test the millis() method of java.time.Clock    @run main ClockWitupTest */

import java.time.Clock;
import java.time.Duration;
import static java.time.Clock.systemUTC;

public class ClockWitupTest {

    public static void main(String[] args) {
        testMillisWithSystemUTC();
        testMillisWithOffset();
    }

    private static void testMillisWithSystemUTC() {
        Clock clock = systemUTC();
        long result = clock.millis();
        long now = System.currentTimeMillis();
        long delta = Math.abs(now - result);
        if (delta > 1000) {
            throw new AssertionError("systemUTC() millis() result differs significantly from System.currentTimeMillis(): " + delta + " ms");
        }
    }

    private static void testMillisWithOffset() {
        Clock baseClock = systemUTC();
        Duration offset = Duration.ofMillis(5000);
        Clock offsetClock = new OffsetClock(baseClock, offset);
        long result = offsetClock.millis();
        long expected = baseClock.millis() + offset.toMillis();
        long delta = Math.abs(expected - result);
        if (delta > 1) {
            throw new AssertionError("OffsetClock millis() did not return expected value: " + result + " expected: " + expected);
        }
    }

    private static class OffsetClock extends Clock {
        private final Clock baseClock;
        private final Duration offset;

        OffsetClock(Clock baseClock, Duration offset) {
            this.baseClock = baseClock;
            this.offset = offset;
        }

        @Override
        public long millis() {
            return Math.addExact(baseClock.millis(), offset.toMillis());
        }

        @Override
        public String toString() {
            return "OffsetClock(base=" + baseClock + ", offset=" + offset + ")";
        }
    }
}
