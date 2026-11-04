/*
 * @test
 * @summary Tests for DateTimeFormatter.withResolverStyle method.
 * @run main DateTimeFormatterWitupTest
 */

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.format.DateTimeFormatterBuilder;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testWithResolverStyle_NullInput();
        testWithResolverStyle_SameStyle_ReturnsSameInstance();
        testWithResolverStyle_DifferentStyle_ReturnsNewInstance();
    }

    static void testWithResolverStyle_NullInput() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter.withResolverStyle(null);
            System.err.println("FAIL: Expected NullPointerException for null resolverStyle");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testWithResolverStyle_SameStyle_ReturnsSameInstance() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.SMART);
        DateTimeFormatter result = formatter.withResolverStyle(ResolverStyle.SMART);
        if (result != formatter) {
            System.err.println("FAIL: withResolverStyle with same style should return same instance");
        }
    }

    static void testWithResolverStyle_DifferentStyle_ReturnsNewInstance() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.SMART);
        DateTimeFormatter newFormatter = formatter.withResolverStyle(ResolverStyle.STRICT);
        if (newFormatter == formatter) {
            System.err.println("FAIL: withResolverStyle with different style should return new instance");
        }
        if (!newFormatter.getClass().equals(formatter.getClass())) {
            System.err.println("FAIL: Returned formatter should be of same class");
        }
    }
}
