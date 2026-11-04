/*
 * @test
 * @summary Witup-derived boundary tests for Instant.ofEpochSecond(long)
 * @run main InstantWitupTest
 */

import java.time.DateTimeException;
import java.time.Instant;

public class InstantWitupTest {
    private static final long MIN_SECOND = -31557014167219200L;
    private static final long MAX_SECOND = 31556889864403199L;
    private static int failures = 0;

    public static void main(String[] args) {
        checkValid(0L);
        checkValid(1L);
        checkValid(-1L);
        checkValid(1500000000L);
        checkValid(MIN_SECOND);
        checkValid(MAX_SECOND);

        if (!Instant.ofEpochSecond(0L).equals(Instant.EPOCH)) {
            fail("epoch second 0 must equal Instant.EPOCH");
        }
        if (!Instant.ofEpochSecond(MIN_SECOND).equals(Instant.MIN)) {
            fail("minimum epoch second must equal Instant.MIN");
        }
        if (!Instant.ofEpochSecond(MAX_SECOND).equals(Instant.MAX)) {
            fail("maximum epoch second must equal Instant.MAX");
        }

        checkThrows(MIN_SECOND - 1L);
        checkThrows(MAX_SECOND + 1L);
        checkThrows(Long.MIN_VALUE);
        checkThrows(Long.MAX_VALUE);

        if (failures > 0) {
            throw new RuntimeException("failures: " + failures);
        }
    }

    private static void checkValid(long seconds) {
        Instant instant = Instant.ofEpochSecond(seconds);
        if (instant.getEpochSecond() != seconds || instant.getNano() != 0) {
            fail("unexpected instant for epoch second " + seconds + ": " + instant);
        }
    }

    private static void checkThrows(long seconds) {
        try {
            Instant instant = Instant.ofEpochSecond(seconds);
            fail("expected DateTimeException for epoch second " + seconds + " but got " + instant);
        } catch (DateTimeException e) {
            // expected
        }
    }

    private static void fail(String message) {
        failures++;
        System.err.println(message);
    }
}
