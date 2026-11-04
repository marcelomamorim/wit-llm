/* @test    @summary Testa o método millis() de java.time.Clock com comportamento padrão.    @run main ClockWitupTest */

import java.time.Clock;
import java.time.Instant;

public class ClockWitupTest {

    public static void main(String[] args) {
        testMillisReturnsInstantEpochMilli();
    }

    public static void testMillisReturnsInstantEpochMilli() {
        Instant fixedInstant = Instant.ofEpochMilli(123456789L);
        Clock clock = new Clock() {
            @Override
            public Instant instant() {
                return fixedInstant;
            }
            @Override
            public long millis() {
                return instant().toEpochMilli();
            }
        };
        long expected = fixedInstant.toEpochMilli();
        long actual = clock.millis();
        if (actual != expected) {
            throw new AssertionError("Expected: " + expected + ", but got: " + actual);
        }
    }
}
