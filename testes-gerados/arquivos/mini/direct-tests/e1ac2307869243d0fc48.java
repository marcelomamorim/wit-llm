/*
 * @test
 * @summary Test java.io.ObjectInputFilter.createFilter(String) method
 * @run main ObjectInputFilterWitupTest
 */

import java.io.ObjectInputFilter;
import java.util.Objects;

public class ObjectInputFilterWitupTest {
    public static void main(String[] args) throws Exception {
        testCreateFilterWithValidPattern();
        testCreateFilterWithNullPattern();
    }

    private static void testCreateFilterWithValidPattern() {
        String pattern = "maxdepth=5";
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
            if (!"pattern".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch: " + npe.getMessage());
            }
        }
    }
}
