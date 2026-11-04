/*
 * @test
 * @summary Witup-generated checks for Instant.now(Clock)
 * @run main InstantWitupTest
 */
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public class InstantWitupTest {
    public static void main(String[] args) {
        testFixedClockReturnsInstant();
        testNullClockThrowsNullPointerException();
    }

    static void testFixedClockReturnsInstant() {
        Instant expected = Instant.ofEpochSecond(1500000000L, 123456789L);
        Clock clock = Clock.fixed(expected, ZoneOffset.UTC);
        Instant actual = Instant.now(clock);
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }

    static void testNullClockThrowsNullPointerException() {
        try {
            Instant.now(null);
        } catch (NullPointerException e) {
            if (!"clock".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NPE message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected NullPointerException was not thrown");
    }
}
