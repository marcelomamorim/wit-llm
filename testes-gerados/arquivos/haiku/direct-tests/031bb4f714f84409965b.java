/*
 * @test
 * @summary SimpleDateFormat() no-arg constructor yields a usable default-pattern formatter
 * @run main SimpleDateFormatWitupTest
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SimpleDateFormatWitupTest {

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat();

        String pattern = sdf.toPattern();
        if (pattern == null || pattern.isEmpty()) {
            throw new RuntimeException("Default pattern must not be null or empty");
        }

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatted = sdf.format(new Date(0L));
        if (formatted == null || formatted.isEmpty()) {
            throw new RuntimeException("Formatting with default pattern produced empty output");
        }

        try {
            Date parsed = sdf.parse(formatted);
            if (parsed == null) {
                throw new RuntimeException("Parsing formatted output returned null");
            }
        } catch (ParseException e) {
            throw new RuntimeException("Default formatter could not parse its own output: " + formatted, e);
        }

        SimpleDateFormat other = new SimpleDateFormat();
        if (!pattern.equals(other.toPattern())) {
            throw new RuntimeException("Two default-constructed instances disagree on pattern");
        }
    }
}
