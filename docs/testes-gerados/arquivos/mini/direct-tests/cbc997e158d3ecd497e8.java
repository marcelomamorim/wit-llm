/*
 * @test
 * @summary Test java.time.OffsetDateTime.toInstant() method
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testToInstantBasic();
        testToInstantEpoch();
        testToInstantNegativeOffset();
    }

    private static void testToInstantBasic() {
        OffsetDateTime odt = OffsetDateTime.of(
            2020, 5, 15, 10, 30, 45, 123456789, ZoneOffset.ofHours(2));
        Instant instant = odt.toInstant();
        // The instant should be equal to the local datetime minus 2 hours
        long expectedEpochSecond = LocalDateTime.of(2020, 5, 15, 10, 30, 45)
            .toEpochSecond(ZoneOffset.ofHours(2));
        int expectedNano = 123456789;
        if (instant.getEpochSecond() != expectedEpochSecond) {
            throw new AssertionError("EpochSecond mismatch: expected " + expectedEpochSecond + " but got " + instant.getEpochSecond());
        }
        if (instant.getNano() != expectedNano) {
            throw new AssertionError("Nano mismatch: expected " + expectedNano + " but got " + instant.getNano());
        }
    }

    private static void testToInstantEpoch() {
        OffsetDateTime odt = OffsetDateTime.of(
            1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        Instant instant = odt.toInstant();
        if (instant.getEpochSecond() != 0) {
            throw new AssertionError("EpochSecond should be 0 for epoch start but got " + instant.getEpochSecond());
        }
        if (instant.getNano() != 0) {
            throw new AssertionError("Nano should be 0 for epoch start but got " + instant.getNano());
        }
    }

    private static void testToInstantNegativeOffset() {
        OffsetDateTime odt = OffsetDateTime.of(
            2020, 1, 1, 0, 0, 0, 0, ZoneOffset.ofHours(-5));
        Instant instant = odt.toInstant();
        long expectedEpochSecond = LocalDateTime.of(2020, 1, 1, 0, 0)
            .toEpochSecond(ZoneOffset.ofHours(-5));
        if (instant.getEpochSecond() != expectedEpochSecond) {
            throw new AssertionError("EpochSecond mismatch for negative offset: expected " + expectedEpochSecond + " but got " + instant.getEpochSecond());
        }
        if (instant.getNano() != 0) {
            throw new AssertionError("Nano mismatch for negative offset: expected 0 but got " + instant.getNano());
        }
    }
}
