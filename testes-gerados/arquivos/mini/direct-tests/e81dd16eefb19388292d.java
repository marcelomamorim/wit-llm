/*
 * @test
 * @summary Test OffsetDateTime.of(LocalDateTime, ZoneOffset) factory method
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testOfWithValidInputs();
        testOfWithNullDateTime();
        testOfWithNullOffset();
    }

    private static void testOfWithValidInputs() {
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 25, 15, 30, 45, 123456789);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(2, 30);
        OffsetDateTime odt = OffsetDateTime.of(ldt, offset);

        if (!odt.toLocalDateTime().equals(ldt)) {
            throw new AssertionError("LocalDateTime mismatch");
        }
        if (!odt.getOffset().equals(offset)) {
            throw new AssertionError("ZoneOffset mismatch");
        }
    }

    private static void testOfWithNullDateTime() {
        try {
            OffsetDateTime.of(null, ZoneOffset.UTC);
            throw new AssertionError("Expected NullPointerException for null LocalDateTime");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testOfWithNullOffset() {
        try {
            OffsetDateTime.of(LocalDateTime.now(), null);
            throw new AssertionError("Expected NullPointerException for null ZoneOffset");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
