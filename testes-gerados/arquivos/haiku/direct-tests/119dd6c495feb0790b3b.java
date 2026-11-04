/*
 * @test
 * @summary Verifies LocalDate.format(DateTimeFormatter) returns formatted text and rejects a null formatter
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        LocalDate d = LocalDate.of(2018, 9, 15);
        LocalDate leap = LocalDate.of(2016, 2, 29);

        check("2018-09-15".equals(d.format(DateTimeFormatter.ISO_LOCAL_DATE)),
              "ISO_LOCAL_DATE format mismatch");

        check("2018/09/15".equals(d.format(DateTimeFormatter.ofPattern("uuuu/MM/dd"))),
              "uuuu/MM/dd format mismatch");

        check("15-09-18".equals(d.format(DateTimeFormatter.ofPattern("dd-MM-uu"))),
              "dd-MM-uu format mismatch");

        check("29/02/2016".equals(leap.format(DateTimeFormatter.ofPattern("dd/MM/uuuu"))),
              "leap day pattern format mismatch");

        check("2016-02-29".equals(leap.format(DateTimeFormatter.ISO_LOCAL_DATE)),
              "leap day ISO format mismatch");

        String first = d.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String second = d.format(DateTimeFormatter.ISO_LOCAL_DATE);
        check(first.equals(second), "format is not repeatable");

        boolean npe = false;
        try {
            d.format(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null formatter must throw NullPointerException");

        boolean dte = false;
        try {
            d.format(DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeException e) {
            dte = true;
        }
        check(dte, "time-only pattern on LocalDate must throw DateTimeException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
