/* @test    @summary Test OffsetDateTime.toInstant() method for correct behavior    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.Instant;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testToInstantWithPositiveOffset();
        testToInstantWithNegativeOffset();
        testToInstantWithZeroOffset();
    }

    static void testToInstantWithPositiveOffset() {
        OffsetDateTime odt = OffsetDateTime.of(2023, 3, 15, 12, 0, 0, 0, ZoneOffset.ofHours(5));
        Instant instant = odt.toInstant();
        Instant expected = odt.getOffset().getRules().getOffset(odt.toLocalDateTime()).getRules().getOffset(odt.toLocalDateTime()).getOffset().adjustInto(odt.toLocalDateTime()).toInstant();
        // Since getOffset().getRules() is complex, compare with expected by reconstructing
        // Alternatively, compare with manually computed expected Instant
        // For simplicity, compare with known expected value
        // But here, we can compare the instant with the expected instant computed directly
        // Using the offset to get the expected instant
        Instant expectedInstant = odt.toLocalDateTime().toInstant(ZoneOffset.ofHours(5));
        assert instant.equals(expectedInstant) : "Failed: Positive offset";
    }

    static void testToInstantWithNegativeOffset() {
        OffsetDateTime odt = OffsetDateTime.of(2023, 3, 15, 12, 0, 0, 0, ZoneOffset.ofHours(-3));
        Instant instant = odt.toInstant();
        Instant expectedInstant = odt.toLocalDateTime().toInstant(ZoneOffset.ofHours(-3));
        assert instant.equals(expectedInstant) : "Failed: Negative offset";
    }

    static void testToInstantWithZeroOffset() {
        OffsetDateTime odt = OffsetDateTime.of(2023, 3, 15, 12, 0, 0, 0, ZoneOffset.UTC);
        Instant instant = odt.toInstant();
        Instant expectedInstant = odt.toLocalDateTime().toInstant(ZoneOffset.UTC);
        assert instant.equals(expectedInstant) : "Failed: Zero offset";
    }
}
