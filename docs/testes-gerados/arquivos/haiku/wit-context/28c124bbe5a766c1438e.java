/*
 * @test
 * @summary ZoneOffset.from(TemporalAccessor) null, missing-offset and offset extraction behavior
 * @run main ZoneOffsetWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class ZoneOffsetWitupTest {

    public static void main(String[] args) {
        testNullTemporalThrowsNpe();
        testTemporalWithoutOffsetThrowsDateTimeException();
        testOffsetIsExtracted();
    }

    static void testNullTemporalThrowsNpe() {
        try {
            ZoneOffset.from(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null temporal");
    }

    static void testTemporalWithoutOffsetThrowsDateTimeException() {
        try {
            ZoneOffset.from(LocalDate.of(2020, 1, 1));
        } catch (DateTimeException expected) {
            return;
        }
        throw new RuntimeException("DateTimeException expected for temporal without offset");
    }

    static void testOffsetIsExtracted() {
        ZoneOffset expected = ZoneOffset.of("+02:00");
        OffsetDateTime odt = OffsetDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0), expected);
        ZoneOffset actual = ZoneOffset.from(odt);
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }
}
