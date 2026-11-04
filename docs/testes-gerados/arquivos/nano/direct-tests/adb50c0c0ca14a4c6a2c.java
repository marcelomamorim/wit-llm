/* @test    @summary Test the millis() method of java.time.Clock for correct epoch milli return.    @run main ClockWitupTest */

import java.time.Clock;
import java.time.Instant;

public class ClockWitupTest {

    public static void main(String[] args) {
        testMillisReturnsEpochMilli();
    }

    public static void testMillisReturnsEpochMilli() {
        Instant fixedInstant = Instant.ofEpochMilli(1234567890L);
        Clock clock = new Clock() {
            @Override
            public Instant instant() {
                return fixedInstant;
            }
        };
        long result = clock.millis();
        assert result == 1234567890L : "Expected 1234567890L, but got " + result;
    }
}
