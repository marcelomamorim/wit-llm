/* @test    @summary Test ObjectInputFilter.createFilter with valid patterns    @run main ObjectInputFilterWitupTest */

import java.io.ObjectInputFilter;
import java.util.Objects;

public class ObjectInputFilterWitupTest {

    public static void main(String[] args) {
        testCreateFilterWithValidPattern();
        testCreateFilterWithNullPattern();
    }

    private static void testCreateFilterWithValidPattern() {
        String pattern = "maxBytes=1024";
        ObjectInputFilter filter = ObjectInputFilter.createFilter(pattern);
        if (filter == null) {
            throw new AssertionError("Expected non-null filter for pattern: " + pattern);
        }
    }

    private static void testCreateFilterWithNullPattern() {
        boolean exceptionThrown = false;
        try {
            ObjectInputFilter.createFilter(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null pattern");
        }
    }
}
