/*
 * @test
 * @summary LocalTime.format(DateTimeFormatter) formats observable values and rejects null formatter
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        LocalTime t = LocalTime.of(10, 15, 30);
        check("10:15:30", t.format(DateTimeFormatter.ISO_LOCAL_TIME));
        check("10:15", t.format(DateTimeFormatter.ofPattern("HH:mm")));
        check("00:00:00", LocalTime.MIDNIGHT.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        check("12:00:00", LocalTime.NOON.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        check("09:05:07.120", LocalTime.of(9, 5, 7, 120000000).format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")));
        check("23", LocalTime.of(23, 59).format(DateTimeFormatter.ofPattern("HH")));

        boolean npe = false;
        try {
            t.format(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null formatter");
        }
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }
}
