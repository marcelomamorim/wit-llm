/*
 * @test
 * @summary Test StringBuilder.insert(int, float) method
 * @run main java.lang.StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertFloatAtStart();
        testInsertFloatAtMiddle();
        testInsertFloatAtEnd();
        testInsertFloatEmptyBuilder();
        testInsertFloatNegativeOffset();
        testInsertFloatOffsetTooLarge();
    }

    private static void testInsertFloatAtStart() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.insert(0, 1.5f);
        if (result != sb) throw new RuntimeException("insert should return this");
        String s = sb.toString();
        if (!s.startsWith("1.5")) {
            throw new RuntimeException("Expected string to start with '1.5', got: " + s);
        }
    }

    private static void testInsertFloatAtMiddle() {
        StringBuilder sb = new StringBuilder("abcdef");
        StringBuilder result = sb.insert(3, 2.5f);
        if (result != sb) throw new RuntimeException("insert should return this");
        String s = sb.toString();
        if (!s.substring(3).startsWith("2.5")) {
            throw new RuntimeException("Expected substring at offset 3 to start with '2.5', got: " + s);
        }
    }

    private static void testInsertFloatAtEnd() {
        StringBuilder sb = new StringBuilder("xyz");
        StringBuilder result = sb.insert(sb.length(), 3.5f);
        if (result != sb) throw new RuntimeException("insert should return this");
        String s = sb.toString();
        if (!s.endsWith("3.5")) {
            throw new RuntimeException("Expected string to end with '3.5', got: " + s);
        }
    }

    private static void testInsertFloatEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        StringBuilder result = sb.insert(0, 4.5f);
        if (result != sb) throw new RuntimeException("insert should return this");
        String s = sb.toString();
        if (!s.equals("4.5")) {
            throw new RuntimeException("Expected string to be '4.5', got: " + s);
        }
    }

    private static void testInsertFloatNegativeOffset() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(-1, 1.0f);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertFloatOffsetTooLarge() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(sb.length() + 1, 1.0f);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
