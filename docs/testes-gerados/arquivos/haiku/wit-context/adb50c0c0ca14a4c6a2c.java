/*
 * @test
 * @summary Checks Clock.millis() returns the epoch millis of instant() and propagates ArithmeticException on overflow
 * @run main ClockWitupTest
 */
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class ClockWitupTest {

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException();
        }
    }

    static Clock clockAt(final Instant instant) {
        return new Clock() {
            @Override
            public ZoneId getZone() {
                return ZoneOffset.UTC;
            }

            @Override
            public Clock withZone(ZoneId zone) {
                return this;
            }

            @Override
            public Instant instant() {
                return instant;
            }
        };
    }

    public static void main(String[] args) {
        check(clockAt(Instant.ofEpochMilli(0L)).millis() == 0L);
        check(clockAt(Instant.ofEpochMilli(1234L)).millis() == 1234L);
        check(clockAt(Instant.ofEpochMilli(-1L)).millis() == -1L);
        check(clockAt(Instant.ofEpochSecond(1L, 999_999_999L)).millis() == 1999L);
        check(clockAt(Instant.ofEpochSecond(-1L, 500_000_000L)).millis() == -500L);

        boolean thrown = false;
        try {
            clockAt(Instant.ofEpochSecond(Long.MAX_VALUE)).millis();
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown);
    }
}
