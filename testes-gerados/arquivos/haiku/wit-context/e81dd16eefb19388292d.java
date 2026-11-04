/*
 * @test
 * @summary Standalone tests for OffsetDateTime.of(LocalDateTime, ZoneOffset) public behavior
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 15, 10, 30, 45, 123);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        OffsetDateTime odt = OffsetDateTime.of(ldt, offset);

        check(ldt.equals(odt.toLocalDateTime()), "local date-time must be preserved");
        check(offset.equals(odt.getOffset()), "offset must be preserved");

        OffsetDateTime sameInputs = OffsetDateTime.of(ldt, offset);
        check(odt.equals(sameInputs), "equal inputs must produce equal results");

        OffsetDateTime utc = OffsetDateTime.of(ldt, ZoneOffset.UTC);
        check(!odt.equals(utc), "different offsets must produce different results");
        check(ldt.equals(utc.toLocalDateTime()), "local date-time must be preserved for UTC offset");

        expectNpe(null, offset, "null dateTime must be rejected");
        expectNpe(ldt, null, "null offset must be rejected");

        System.out.println("passed");
    }

    private static void expectNpe(LocalDateTime dateTime, ZoneOffset offset, String label) {
        try {
            OffsetDateTime.of(dateTime, offset);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException: " + label);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
