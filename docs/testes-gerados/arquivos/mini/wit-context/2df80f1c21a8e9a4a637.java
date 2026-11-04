/*
 * @test
 * @summary Test OffsetDateTime.toEpochSecond() for normal and null offset scenarios
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testToEpochSecondNormal();
        testToEpochSecondNullOffset();
        System.out.println("All tests passed.");
    }

    private static void testToEpochSecondNormal() {
        // Create OffsetDateTime with explicit offset
        LocalDateTime ldt = LocalDateTime.of(1970, 1, 1, 0, 0, 1);
        ZoneOffset offset = ZoneOffset.UTC;
        OffsetDateTime odt = OffsetDateTime.of(ldt, offset);

        // toEpochSecond should return 1 second since epoch
        long epochSecond = odt.toEpochSecond();
        if (epochSecond != 1L) {
            throw new AssertionError("Expected 1 but got " + epochSecond);
        }
    }

    private static void testToEpochSecondNullOffset() {
        // Construct OffsetDateTime with null offset via reflection is disallowed,
        // so simulate by subclassing or by creating a dummy OffsetDateTime with null offset is impossible.
        // Instead, test that toEpochSecond() throws NullPointerException if offset is null internally.
        // Since OffsetDateTime.of(...) does not allow null offset, we test by subclassing.

        OffsetDateTime odt = new OffsetDateTimeWithNullOffset();
        try {
            odt.toEpochSecond();
            throw new AssertionError("Expected NullPointerException for null offset");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    // Helper subclass to simulate OffsetDateTime with null offset
    private static class OffsetDateTimeWithNullOffset extends OffsetDateTime {
        private OffsetDateTimeWithNullOffset() {
            super(LocalDateTime.of(1970, 1, 1, 0, 0, 0), null);
        }

        @Override
        public ZoneOffset getOffset() {
            return null;
        }

        @Override
        public long toEpochSecond() {
            // Call super method which will use null offset and cause NPE
            return super.toEpochSecond();
        }
    }
}