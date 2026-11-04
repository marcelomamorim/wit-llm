/*
 * @test
 * @summary Test for DateTimeFormatter.withResolverStyle method.
 * @run main DateTimeFormatterWitupTest
 */
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class DateTimeFormatterWitupTest {

    public static void main(String[] args) {
        testWithResolverStyleSameStyle();
        testWithResolverStyleDifferentStyle();
    }

    static void testWithResolverStyleSameStyle() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.SMART);
        DateTimeFormatter result = formatter.withResolverStyle(ResolverStyle.SMART);
        if (result != formatter) {
            throw new AssertionError("Expected same formatter instance when resolver style is unchanged");
        }
    }

    static void testWithResolverStyleDifferentStyle() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.SMART);
        DateTimeFormatter newFormatter = formatter.withResolverStyle(ResolverStyle.STRICT);
        if (newFormatter == formatter) {
            throw new AssertionError("Expected different formatter instance when resolver style changes");
        }
        if (newFormatter == null) {
            throw new AssertionError("Returned formatter should not be null");
        }
        if (newFormatter.equals(formatter)) {
            throw new AssertionError("Formatter should be different after style change");
        }
    }
}
