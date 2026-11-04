/*
 * @test
 * @summary Basic observable behavior of the no-arg SimpleDateFormat constructor
 * @run main SimpleDateFormatWitupTest
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatWitupTest {

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat();

        String pattern = sdf.toPattern();
        if (pattern == null || pattern.isEmpty()) {
            throw new RuntimeException("toPattern() returned null or empty");
        }

        Date date = new Date(0L);
        String formatted = sdf.format(date);
        if (formatted == null || formatted.isEmpty()) {
            throw new RuntimeException("format() returned null or empty");
        }

        Date parsed = sdf.parse(formatted);
        String reformatted = sdf.format(parsed);
        if (!reformatted.equals(formatted)) {
            throw new RuntimeException("round trip mismatch: " + formatted + " vs " + reformatted);
        }

        DateFormat ref = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        String expected = ref.format(date);
        if (!expected.equals(formatted)) {
            throw new RuntimeException("expected " + expected + " but got " + formatted);
        }
    }
}
