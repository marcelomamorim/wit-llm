/*
 * @test
 * @summary Witup-generated checks for Duration.withNanos nano-of-second validation
 * @run main DurationWitupTest
 */

import java.time.DateTimeException;
import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        checkValidValues();
        checkInvalidValuesThrow();
        checkSecondsPreserved();
    }

    static void checkValidValues() {
        Duration base = Duration.ofSeconds(5);

        Duration zero = base.withNanos(0);
        if (zero.getNano() != 0) {
            throw new AssertionError("expected nano 0 but was " + zero.getNano());
        }

        Duration max = base.withNanos(999_999_999);
        if (max.getNano() != 999_999_999) {
            throw new AssertionError("expected nano 999999999 but was " + max.getNano());
        }

        Duration mid = base.withNanos(123_456_789);
        if (mid.getNano() != 123_456_789) {
            throw new AssertionError("expected nano 123456789 but was " + mid.getNano());
        }
    }

    static void checkInvalidValuesThrow() {
        expectDateTimeException(-1);
        expectDateTimeException(1_000_000_000);
        expectDateTimeException(Integer.MIN_VALUE);
        expectDateTimeException(Integer.MAX_VALUE);
    }

    static void checkSecondsPreserved() {
        Duration base = Duration.ofSeconds(5);
        Duration changed = base.withNanos(42);
        if (changed.getSeconds() != 5) {
            throw new AssertionError("expected seconds 5 but was " + changed.getSeconds());
        }
        if (changed.getNano() != 42) {
            throw new AssertionError("expected nano 42 but was " + changed.getNano());
        }
    }

    static void expectDateTimeException(int nanos) {
        try {
            Duration.ofSeconds(5).withNanos(nanos);
        } catch (DateTimeException e) {
            return;
        }
        throw new AssertionError("no DateTimeException for nanos " + nanos);
    }
}
