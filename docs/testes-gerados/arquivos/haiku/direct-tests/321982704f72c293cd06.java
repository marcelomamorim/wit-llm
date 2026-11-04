/*
 * @test
 * @summary DateTimeFormatter.withResolverStyle rejects null, returns this for an equal style, and returns a new formatter with changed parsing behavior otherwise
 * @run main DateTimeFormatterWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        DateTimeFormatter base = DateTimeFormatter.ofPattern("uuuu-MM-dd");

        if (base.getResolverStyle() != ResolverStyle.SMART) {
            throw new AssertionError("expected SMART default, got " + base.getResolverStyle());
        }

        boolean npe = false;
        try {
            base.withResolverStyle(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new AssertionError("expected NullPointerException for null resolverStyle");
        }

        if (base.withResolverStyle(ResolverStyle.SMART) != base) {
            throw new AssertionError("expected same instance when style is unchanged");
        }

        DateTimeFormatter strict = base.withResolverStyle(ResolverStyle.STRICT);
        if (strict == base) {
            throw new AssertionError("expected new instance for different style");
        }
        if (strict.getResolverStyle() != ResolverStyle.STRICT) {
            throw new AssertionError("expected STRICT, got " + strict.getResolverStyle());
        }
        if (base.getResolverStyle() != ResolverStyle.SMART) {
            throw new AssertionError("original formatter must not change");
        }

        if (strict.withResolverStyle(ResolverStyle.STRICT) != strict) {
            throw new AssertionError("expected same instance when STRICT is re-applied");
        }

        LocalDate smart = LocalDate.parse("2011-02-31", base);
        if (!smart.equals(LocalDate.of(2011, 2, 28))) {
            throw new AssertionError("SMART should resolve to 2011-02-28, got " + smart);
        }

        boolean strictFailed = false;
        try {
            LocalDate.parse("2011-02-31", strict);
        } catch (DateTimeException e) {
            strictFailed = true;
        }
        if (!strictFailed) {
            throw new AssertionError("STRICT should reject 2011-02-31");
        }
    }
}
