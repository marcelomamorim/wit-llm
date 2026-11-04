/*
 * @test
 * @summary Witup-derived tests for OffsetDateTime.format
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        OffsetDateTime utc = OffsetDateTime.of(2020, 1, 2, 3, 4, 5, 0, ZoneOffset.UTC);
        check("2020-01-02T03:04:05Z", utc.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        OffsetDateTime plus2 = OffsetDateTime.of(2020, 1, 2, 3, 4, 5, 0, ZoneOffset.ofHours(2));
        check("2020-01-02T03:04:05+02:00", plus2.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        check("2020/01/02 03:04", utc.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));

        boolean npe = false;
        try {
            utc.format(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null formatter");
        }
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
