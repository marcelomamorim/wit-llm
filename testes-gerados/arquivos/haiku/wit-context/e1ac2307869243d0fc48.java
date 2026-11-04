/*
 * @test
 * @summary Verify ObjectInputFilter.createFilter rejects a null pattern and returns a non-null filter for a valid pattern
 * @run main ObjectInputFilterWitupTest
 */

import java.io.ObjectInputFilter;

public class ObjectInputFilterWitupTest {

    public static void main(String[] args) {
        testNullPatternThrowsNpe();
        testValidPatternReturnsFilter();
    }

    static void testNullPatternThrowsNpe() {
        try {
            ObjectInputFilter.createFilter(null);
            throw new AssertionError("Expected NullPointerException for null pattern");
        } catch (NullPointerException expected) {
            if (!"pattern".equals(expected.getMessage())) {
                throw new AssertionError("Unexpected NPE message: " + expected.getMessage());
            }
        }
    }

    static void testValidPatternReturnsFilter() {
        ObjectInputFilter filter = ObjectInputFilter.createFilter("maxdepth=10");
        if (filter == null) {
            throw new AssertionError("createFilter returned null for a valid pattern");
        }
    }
}
