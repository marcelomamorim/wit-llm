/*
 * @test
 * @summary ZonedDateTime.format must reject a null formatter and format local date-time fields
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        ZonedDateTime zdt = ZonedDateTime.of(2020, 1, 2, 3, 4, 5, 0, ZoneId.of("UTC"));
        try {
            zdt.format(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
        String s = zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (!"2020-01-02 03:04:05".equals(s)) {
            throw new RuntimeException("unexpected pattern output: " + s);
        }
        String iso = zdt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        if (!"2020-01-02T03:04:05".equals(iso)) {
            throw new RuntimeException("unexpected ISO output: " + iso);
        }
    }
}
