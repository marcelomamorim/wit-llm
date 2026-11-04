/*
 * @test
 * @summary Test java.lang.String.join(CharSequence, CharSequence...) for null checks and basic join
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testNullDelimiter();
        testNullElements();
        testEmptyElements();
        testSingleElement();
        testMultipleElements();
    }

    private static void testNullDelimiter() {
        try {
            java.lang.String.join(null, "a", "b");
            throw new RuntimeException("Expected NullPointerException for null delimiter");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullElements() {
        try {
            java.lang.String.join(",", (CharSequence[]) null);
            throw new RuntimeException("Expected NullPointerException for null elements");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyElements() {
        String result = java.lang.String.join(",");
        if (!"".equals(result)) {
            throw new RuntimeException("Expected empty string when no elements provided, got: " + result);
        }
    }

    private static void testSingleElement() {
        String result = java.lang.String.join(",", "single");
        if (!"single".equals(result)) {
            throw new RuntimeException("Expected 'single' for single element, got: " + result);
        }
    }

    private static void testMultipleElements() {
        String result = java.lang.String.join(",", "a", "b", "c");
        if (!"a,b,c".equals(result)) {
            throw new RuntimeException("Expected 'a,b,c' for multiple elements, got: " + result);
        }
    }
}
