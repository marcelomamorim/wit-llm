/*
 * @test
 * @summary Test StringBuilder.insert(int, CharSequence) basic and edge cases
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertNullCharSequence();
        testInsertEmptyCharSequence();
        testInsertAtStart();
        testInsertAtEnd();
        testInsertMiddle();
        testInsertWithStringBuilderCharSequence();
    }

    private static void testInsertNullCharSequence() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(1, (CharSequence) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testInsertEmptyCharSequence() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.insert(1, "");
        if (result != sb) {
            throw new RuntimeException("insert should return this");
        }
        if (!sb.toString().equals("abc")) {
            throw new RuntimeException("Inserting empty CharSequence should not change content");
        }
    }

    private static void testInsertAtStart() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(0, "X");
        if (!sb.toString().equals("Xabc")) {
            throw new RuntimeException("Insert at start failed");
        }
    }

    private static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(sb.length(), "Y");
        if (!sb.toString().equals("abcY")) {
            throw new RuntimeException("Insert at end failed");
        }
    }

    private static void testInsertMiddle() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(1, "Z");
        if (!sb.toString().equals("aZbc")) {
            throw new RuntimeException("Insert in middle failed");
        }
    }

    private static void testInsertWithStringBuilderCharSequence() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder toInsert = new StringBuilder("DEF");
        sb.insert(2, toInsert);
        if (!sb.toString().equals("abDEFc")) {
            throw new RuntimeException("Insert with StringBuilder CharSequence failed");
        }
    }
}
