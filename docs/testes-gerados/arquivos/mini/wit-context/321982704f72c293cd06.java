/*
 * @test
 * @summary Test DateTimeFormatter.withResolverStyle(ResolverStyle) method
 * @run main java.time.format.DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
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
        } catch (NullPointerException e) {
            // Expected exception
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
        ResolverStyle currentStyle = formatter.getResolverStyle();
        ResolverStyle newStyle = (currentStyle == ResolverStyle.STRICT) ? ResolverStyle.SMART : ResolverStyle.STRICT;
        DateTimeFormatter result = formatter.withResolverStyle(newStyle);
        if (result == null) {
            throw new AssertionError("Resulting formatter should not be null");
        }
        if (result == formatter) {
            throw new AssertionError("Expected different instance when resolverStyle is changed");
        }
        if (!Objects.equals(result.getResolverStyle(), newStyle)) {
            throw new AssertionError("Resulting formatter should have the new resolverStyle");
        }
    }
}
