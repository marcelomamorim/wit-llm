/*
 * @test
 * @summary Test Duration.withNanos(int) method for various valid and invalid inputs
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoField;

public class DurationWitupTest {

    public static void main(String[] args) {
        testWithNanosValid();
        testWithNanosInvalid();
    }

    static void testWithNanosValid() {
        Duration duration = Duration.ofSeconds(10, 500_000_000); // 10.5 seconds
        Duration result = duration.withNanos(999_999_999);
        assert result != null : "Result should not be null";
        assert result.getSeconds() == 10 : "Seconds part should be 10";
        assert result.getNano() == 999_999_999 : "Nano part should be 999999999";
    }

    static void testWithNanosInvalid() {
        try {
            Duration duration = Duration.ofSeconds(5);
            duration.withNanos(1_500_000_000); // invalid nano value
            assert false : "Expected an exception for invalid nano value";
        } catch (DateTimeException e) {
            // Expected exception
        }
        try {
            Duration duration = Duration.ofSeconds(5);
            duration.withNanos(-1); // invalid nano value
            assert false : "Expected an exception for negative nano value";
        } catch (DateTimeException e) {
            // Expected exception
        }
    }
}
