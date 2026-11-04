/*
 * @test
 * @summary ZonedDateTime.format delegates to the formatter and rejects a null formatter
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        ZonedDateTime utc = ZonedDateTime.of(2018, 1, 2, 3, 4, 5, 0, ZoneId.of("UTC"));
        ZonedDateTime off = ZonedDateTime.of(2018, 1, 2, 3, 4, 5, 123000000, ZoneOffset.ofHours(-3));

        check("2018-01-02T03:04:05", utc.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        check("2018-01-02T03:04:05.123", off.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        check("2018-01-02T03:04:05.123-03:00", off.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        check("2018/01/02", utc.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));

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

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
