/*
 * @test
 * @summary Test OffsetDateTime.ofInstant(Instant, ZoneId) method
 * @run main OffsetDateTimeWitupTest
 */

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.DateTimeException;
import java.util.Objects;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testOfInstantBasic();
        testOfInstantNullInstant();
        testOfInstantNullZone();
        testOfInstantWithUtcZone();
        testOfInstantWithPositiveOffset();
        testOfInstantWithNegativeOffset();
    }

    private static void testOfInstantBasic() {
        Instant instant = Instant.ofEpochSecond(0L, 0);
        ZoneId zone = ZoneOffset.UTC;
        OffsetDateTime odt = OffsetDateTime.ofInstant(instant, zone);
        if (!odt.toInstant().equals(instant)) {
            throw new AssertionError("OffsetDateTime.toInstant() must equal input instant");
        }
        if (!odt.getOffset().equals(zone)) {
            throw new AssertionError("OffsetDateTime offset must equal zone offset");
        }
    }

    private static void testOfInstantNullInstant() {
        try {
            OffsetDateTime.ofInstant(null, ZoneOffset.UTC);
            throw new AssertionError("Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            if (!"instant".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null instant");
            }
        }
    }

    private static void testOfInstantNullZone() {
        try {
            OffsetDateTime.ofInstant(Instant.now(), null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            if (!"zone".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch for null zone");
            }
        }
    }

    private static void testOfInstantWithUtcZone() {
        Instant instant = Instant.ofEpochSecond(123456789L, 987654321);
        ZoneId zone = ZoneOffset.UTC;
        OffsetDateTime odt = OffsetDateTime.ofInstant(instant, zone);
        if (!odt.toInstant().equals(instant)) {
            throw new AssertionError("OffsetDateTime.toInstant() must equal input instant for UTC zone");
        }
        if (!odt.getOffset().equals(zone)) {
            throw new AssertionError("OffsetDateTime offset must equal UTC offset");
        }
    }

    private static void testOfInstantWithPositiveOffset() {
        Instant instant = Instant.ofEpochSecond(0L, 0);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        OffsetDateTime odt = OffsetDateTime.ofInstant(instant, offset);
        if (!odt.getOffset().equals(offset)) {
            throw new AssertionError("OffsetDateTime offset must equal positive offset");
        }
        if (!odt.toInstant().equals(instant)) {
            throw new AssertionError("OffsetDateTime.toInstant() must equal input instant for positive offset");
        }
    }

    private static void testOfInstantWithNegativeOffset() {
        Instant instant = Instant.ofEpochSecond(0L, 0);
        ZoneOffset offset = ZoneOffset.ofHours(-5);
        OffsetDateTime odt = OffsetDateTime.ofInstant(instant, offset);
        if (!odt.getOffset().equals(offset)) {
            throw new AssertionError("OffsetDateTime offset must equal negative offset");
        }
        if (!odt.toInstant().equals(instant)) {
            throw new AssertionError("OffsetDateTime.toInstant() must equal input instant for negative offset");
        }
    }
}
