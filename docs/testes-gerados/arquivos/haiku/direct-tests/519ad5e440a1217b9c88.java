/*
 * @test
 * @summary Verifies that SimpleDateFormat.applyLocalizedPattern translates and applies a pattern observable through toPattern, format and parse
 * @run main SimpleDateFormatWitupTest
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SimpleDateFormatWitupTest {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        sdf.applyLocalizedPattern("dd/MM/yyyy");
        check("dd/MM/yyyy".equals(sdf.toPattern()), "toPattern after applyLocalizedPattern");
        check("01/01/1970".equals(sdf.format(new Date(0L))), "format after applyLocalizedPattern");

        Date parsed = sdf.parse("15/08/2001");
        check("15/08/2001".equals(sdf.format(parsed)), "round trip parse/format");

        SimpleDateFormat quoted = new SimpleDateFormat("yyyy", Locale.US);
        quoted.setTimeZone(TimeZone.getTimeZone("UTC"));
        quoted.applyLocalizedPattern("dd'T'HH:mm");
        check("dd'T'HH:mm".equals(quoted.toPattern()), "quoted literal preserved in toPattern");
        check("01T00:00".equals(quoted.format(new Date(0L))), "quoted literal in format output");

        sdf.applyLocalizedPattern("yyyy");
        check("yyyy".equals(sdf.toPattern()), "second applyLocalizedPattern overrides previous pattern");
        check("1970".equals(sdf.format(new Date(0L))), "format after second apply");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
