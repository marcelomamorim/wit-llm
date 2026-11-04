/*
 * @test
 * @summary Test java.time.Instant.ofEpochSecond(long) for boundary and normal values
 * @run main InstantWitupTest
 */

import java.time.Instant;
import java.time.DateTimeException;

public class InstantWitupTest {
    public static void main(String[] args) {
        testValidEpochSeconds();
        testBoundaryEpochSeconds();
        testOutOfRangeEpochSeconds();
    }

    private static void testValidEpochSeconds() {
        // Normal values
        Instant i1 = Instant.ofEpochSecond(0L);
        if (i1.getEpochSecond() != 0L || i1.getNano() != 0) {
            throw new AssertionError("Instant.ofEpochSecond(0) failed");
        }
        Instant i2 = Instant.ofEpochSecond(1L);
        if (i2.getEpochSecond() != 1L || i2.getNano() != 0) {
            throw new AssertionError("Instant.ofEpochSecond(1) failed");
        }
        Instant i3 = Instant.ofEpochSecond(-1L);
        if (i3.getEpochSecond() != -1L || i3.getNano() != 0) {
            throw new AssertionError("Instant.ofEpochSecond(-1) failed");
        }
    }

    private static void testBoundaryEpochSeconds() {
        // Test boundaries near the limits (from WIT expath)
        // Lower bound: -31557014167219200L
        Instant lowBound = Instant.ofEpochSecond(-31557014167219200L);
        if (lowBound.getEpochSecond() != -31557014167219200L) {
            throw new AssertionError("Instant.ofEpochSecond lower bound failed");
        }
        // Upper bound: 31556889864403199L
        Instant upBound = Instant.ofEpochSecond(31556889864403199L);
        if (upBound.getEpochSecond() != 31556889864403199L) {
            throw new AssertionError("Instant.ofEpochSecond upper bound failed");
        }
    }

    private static void testOutOfRangeEpochSeconds() {
        // According to current source, ofEpochSecond calls create(epochSecond, 0)
        // The WIT expath suggests DateTimeException for out of range values
        // But since create is not visible here, we test observable behavior
        // We expect DateTimeException for out of range values
        long belowMin = -31557014167219201L;
        long aboveMax = 31556889864403200L;

        try {
            Instant.ofEpochSecond(belowMin);
            throw new AssertionError("Expected DateTimeException for below min epochSecond");
        } catch (DateTimeException e) {
            // expected
        }

        try {
            Instant.ofEpochSecond(aboveMax);
            throw new AssertionError("Expected DateTimeException for above max epochSecond");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
