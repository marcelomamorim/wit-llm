/*
 * @test
 * @summary Test ObjectInputFilter.createFilter(String) for null argument and valid pattern
 * @run main ObjectInputFilterWitupTest
 */

import java.io.ObjectInputFilter;
import java.util.Objects;

public class ObjectInputFilterWitupTest {
    public static void main(String[] args) {
        testCreateFilterWithValidPattern();
        testCreateFilterWithNullPattern();
    }

    private static void testCreateFilterWithValidPattern() {
        // Use a simple valid pattern string
        String pattern = "java.base/*";
        ObjectInputFilter filter = ObjectInputFilter.createFilter(pattern);
        if (filter == null) {
            throw new AssertionError("createFilter returned null for valid pattern");
        }
    }

    private static void testCreateFilterWithNullPattern() {
        try {
            ObjectInputFilter.createFilter(null);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }
}
