/* @test    @summary Test OffsetDateTime.toEpochSecond() method for correct epoch second calculation.    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testToEpochSecondWithPositiveOffset();
        testToEpochSecondWithNegativeOffset();
        testToEpochSecondWithZeroOffset();
    }

    static void testToEpochSecondWithPositiveOffset() {
        LocalDateTime ldt = LocalDateTime.of(2023, 3, 15, 12, 0, 0);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        OffsetDateTime odt = OffsetDateTime.of(ldt, offset);
        long epochSecond = odt.toEpochSecond();
        long expected = ldt.toEpochSecond(offset);
        if (epochSecond != expected) {
            throw new AssertionError("Expected: " + expected + ", but got: " + epochSecond);
        }
    }

    static void testToEpochSecondWithNegativeOffset() {
        LocalDateTime ldt = LocalDateTime.of(2023, 3, 15, 12, 0, 0);
        ZoneOffset offset = ZoneOffset.ofHours(-5);
        OffsetDateTime odt = OffsetDateTime.of(ldt, offset);
        long epochSecond = odt.toEpochSecond();
        long expected = ldt.toEpochSecond(offset);
        if (epochSecond != expected) {
            throw new AssertionError("Expected: " + expected + ", but got: " + epochSecond);
        }
    }

    static void testToEpochSecondWithZeroOffset() {
        LocalDateTime ldt = LocalDateTime.of(2023, 3, 15, 12, 0, 0);
        ZoneOffset offset = ZoneOffset.ofHours(0);
        OffsetDateTime odt = OffsetDateTime.of(ldt, offset);
        long epochSecond = odt.toEpochSecond();
        long expected = ldt.toEpochSecond(offset);
        if (epochSecond != expected) {
            throw new AssertionError("Expected: " + expected + ", but got: " + epochSecond);
        }
    }
}
