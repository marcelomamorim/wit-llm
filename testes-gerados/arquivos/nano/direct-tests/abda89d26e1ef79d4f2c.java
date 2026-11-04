/* @test    @summary Test getInstant() returns correct Instant based on epochSecond    @run main ZoneOffsetTransitionWitupTest */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

public class ZoneOffsetTransitionWitupTest {

    public static void main(String[] args) {
        testGetInstant();
    }

    public static void testGetInstant() {
        long epochSecond = 1625097600L; // Corresponds to 2021-07-01T00:00:00Z
        LocalDateTime ldt = LocalDateTime.of(2021, 7, 1, 0, 0);
        ZoneOffset offset = ZoneOffset.UTC;
        ZoneOffsetTransition transition = ZoneOffsetTransition.of(ldt, offset, offset);
        // Manually set epochSecond for test
        // Since epochSecond is private, we simulate the behavior by creating a subclass or using reflection if needed
        // But as per constraints, we assume getInstant() uses epochSecond directly
        // For testing, we can create a subclass that exposes epochSecond or simulate via reflection
        // However, since epochSecond is not accessible, we test getInstant() returns expected Instant
        Instant expectedInstant = Instant.ofEpochSecond(epochSecond);
        Instant actualInstant = transition.getInstant();
        if (!actualInstant.equals(expectedInstant)) {
            throw new AssertionError("Expected: " + expectedInstant + ", but got: " + actualInstant);
        }
    }
}
