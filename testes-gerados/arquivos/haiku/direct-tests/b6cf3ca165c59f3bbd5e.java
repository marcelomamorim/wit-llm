/*
 * @test
 * @summary OffsetDateTime.format delegates to DateTimeFormatter and rejects null formatter
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        OffsetDateTime odt = OffsetDateTime.of(2020, 1, 2, 3, 4, 5, 0, ZoneOffset.ofHours(1));
        check("2020-01-02T03:04:05+01:00", odt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        check("2020-01-02T03:04:05", odt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        check("+0100", odt.format(DateTimeFormatter.ofPattern("xxxx")));

        OffsetDateTime utc = OffsetDateTime.of(LocalDateTime.of(2020, 1, 2, 3, 4, 5), ZoneOffset.UTC);
        check("2020-01-02 03:04:05 Z", utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX")));

        boolean npeThrown = false;
        try {
            odt.format(null);
        } catch (NullPointerException e) {
            npeThrown = true;
            check("formatter", e.getMessage());
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null formatter");
        }
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected \"" + expected + "\" but was \"" + actual + "\"");
        }
    }
}
