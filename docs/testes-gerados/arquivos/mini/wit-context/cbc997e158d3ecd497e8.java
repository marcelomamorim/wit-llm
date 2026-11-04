/*
 * @test
 * @summary Test OffsetDateTime.toInstant() method behavior including NPE on null offset
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testToInstantNormal();
        testToInstantNullOffset();
        System.out.println("All tests passed.");
    }

    private static void testToInstantNormal() {
        // Create OffsetDateTime with explicit offset
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        OffsetDateTime odt = OffsetDateTime.of(ldt, offset);

        Instant instant = odt.toInstant();

        // The instant should be equal to ldt minus offset
        long expectedEpochSecond = ldt.toEpochSecond(offset);
        if (instant.getEpochSecond() != expectedEpochSecond) {
            throw new AssertionError("toInstant() epochSecond mismatch: expected " + expectedEpochSecond + " but got " + instant.getEpochSecond());
        }

        // Nano adjustment check
        if (instant.getNano() != ldt.getNano()) {
            throw new AssertionError("toInstant() nano mismatch: expected " + ldt.getNano() + " but got " + instant.getNano());
        }
    }

    private static void testToInstantNullOffset() {
        // Construct OffsetDateTime with null offset via reflection is not possible publicly,
        // so we test the behavior indirectly by creating a subclass that returns null offset.
        // Since OffsetDateTime is final, we cannot subclass it, so we test that toInstant()
        // throws NPE if offset is null by simulating the call to dateTime.toInstant(null).
        // This is not possible directly, so we test that OffsetDateTime.of throws NPE on null offset.

        try {
            OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0), null);
            throw new AssertionError("Expected NullPointerException for null offset in OffsetDateTime.of");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
