/*
 * @test
 * @summary Tests SimpleDateFormat.applyLocalizedPattern observable behavior
 * @run main SimpleDateFormatWitupTest
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SimpleDateFormatWitupTest {

    public static void main(String[] args) {
        testAppliesValidLocalizedPattern();
        testInvalidPatternLetterThrows();
        testFailedApplyKeepsPreviousPattern();
        testLocalizedPatternRoundTrip();
    }

    static void testAppliesValidLocalizedPattern() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf.applyLocalizedPattern("dd/MM/yyyy");
        String out = sdf.format(new Date(1535760000000L));
        if (!"01/09/2018".equals(out)) {
            throw new RuntimeException("Unexpected format output: " + out);
        }
        if (!"dd/MM/yyyy".equals(sdf.toPattern())) {
            throw new RuntimeException("Unexpected toPattern: " + sdf.toPattern());
        }
    }

    static void testInvalidPatternLetterThrows() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.US);
        try {
            sdf.applyLocalizedPattern("QQ");
            throw new RuntimeException("IllegalArgumentException expected for invalid pattern letter");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testFailedApplyKeepsPreviousPattern() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            sdf.applyLocalizedPattern("QQ");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        if (!"yyyy-MM-dd".equals(sdf.toPattern())) {
            throw new RuntimeException("Pattern changed after failed apply: " + sdf.toPattern());
        }
    }

    static void testLocalizedPatternRoundTrip() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.US);
        sdf.applyLocalizedPattern("yyyy");
        if (!"yyyy".equals(sdf.toLocalizedPattern())) {
            throw new RuntimeException("Unexpected localized pattern: " + sdf.toLocalizedPattern());
        }
    }
}
