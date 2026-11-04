/*
 * @test
 * @summary Test DateTimeFormatter.withResolverStyle method behavior
 * @run main java.time.format.DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Objects;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testWithResolverStyleNull();
        testWithResolverStyleSameInstance();
        testWithResolverStyleDifferentInstance();
    }

    private static void testWithResolverStyleNull() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        try {
            formatter.withResolverStyle(null);
            throw new AssertionError("Expected NullPointerException for null resolverStyle");
        } catch (NullPointerException npe) {
            // expected
            if (!"resolverStyle".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testWithResolverStyleSameInstance() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        ResolverStyle currentStyle = formatter.getResolverStyle();
        DateTimeFormatter result = formatter.withResolverStyle(currentStyle);
        if (result != formatter) {
            throw new AssertionError("Expected same instance when resolverStyle is unchanged");
        }
    }

    private static void testWithResolverStyleDifferentInstance() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        ResolverStyle newStyle = ResolverStyle.LENIENT;
        if (Objects.equals(formatter.getResolverStyle(), newStyle)) {
            // Defensive: if ISO_LOCAL_DATE already has LENIENT, pick STRICT
            newStyle = ResolverStyle.STRICT;
        }
        DateTimeFormatter result = formatter.withResolverStyle(newStyle);
        if (result == formatter) {
            throw new AssertionError("Expected new instance when resolverStyle is different");
        }
        if (result.getResolverStyle() != newStyle) {
            throw new AssertionError("ResolverStyle not updated correctly");
        }
        // Check other properties remain equal
        if (!formatter.getLocale().equals(result.getLocale())) {
            throw new AssertionError("Locale changed unexpectedly");
        }
        if (!formatter.getDecimalStyle().equals(result.getDecimalStyle())) {
            throw new AssertionError("DecimalStyle changed unexpectedly");
        }
    }
}
