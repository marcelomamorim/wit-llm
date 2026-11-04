/*
 * @test
 * @summary Test StringBuilder.insert(int, CharSequence) for exception and normal cases
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertNullCharSequence();
        testInsertNegativeIndex();
        testInsertIndexOutOfBounds();
        testInsertNormal();
    }

    private static void testInsertNullCharSequence() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(1, (CharSequence) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected
        }
    }

    private static void testInsertNegativeIndex() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(-1, "xyz");
            throw new RuntimeException("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException sioobe) {
            // Expected
        }
    }

    private static void testInsertIndexOutOfBounds() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(4, "xyz"); // index > length
            throw new RuntimeException("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException sioobe) {
            // Expected
        }
    }

    private static void testInsertNormal() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.insert(1, "XYZ");
        if (result != sb) {
            throw new RuntimeException("insert should return this");
        }
        if (!"aXYZbc".equals(sb.toString())) {
            throw new RuntimeException("Unexpected result: " + sb.toString());
        }
    }
}
