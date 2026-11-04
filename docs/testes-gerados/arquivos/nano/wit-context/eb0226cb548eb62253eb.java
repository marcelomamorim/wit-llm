/* @test    @summary Teste do método java.time.Clock.millis() para verificar comportamento padrão e exceções.    @run main ClockWitupTest */

import java.time.Clock;
import java.time.Instant;

public class ClockWitupTest {

    public static void main(String[] args) {
        testMillisReturnsEpochMilli();
        testMillisThrowsArithmeticException();
    }

    private static void testMillisReturnsEpochMilli() {
        Clock clock = Clock.systemUTC();
        long result = clock.millis();
        long expected = clock.instant().toEpochMilli();
        if (result != expected) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }

    private static void testMillisThrowsArithmeticException() {
        Clock clock = new Clock() {
            @Override
            public long millis() {
                throw new ArithmeticException("Simulated overflow");
            }

            @Override
            public Instant instant() {
                return Instant.EPOCH;
            }
        };
        try {
            clock.millis();
            throw new AssertionError("Expected ArithmeticException was not thrown");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
