/* @test    @summary Test default constructor of SimpleDateFormat for correct pattern application    @run main SimpleDateFormatWitupTest */

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SimpleDateFormatWitupTest {

    public static void main(String[] args) {
        testDefaultConstructorCreatesPattern();
    }

    public static void testDefaultConstructorCreatesPattern() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        String pattern = sdf.toPattern();
        if (pattern == null || pattern.isEmpty()) {
            throw new AssertionError("Pattern should not be null or empty after default constructor");
        }
        System.out.println("Default constructor test passed with pattern: " + pattern);
    }
}
