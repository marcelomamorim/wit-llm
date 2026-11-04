/*
 * @test
 * @summary Test StringBuilder.insert(int, Object) method
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertWithStringObject();
        testInsertWithNullObject();
        testInsertAtStart();
        testInsertAtEnd();
        testInsertWithInvalidOffset();
    }

    private static void testInsertWithStringObject() {
        StringBuilder sb = new StringBuilder("HelloWorld");
        StringBuilder result = sb.insert(5, " ");
        if (!"Hello World".equals(sb.toString())) {
            throw new Error("Failed insert with String object: " + sb.toString());
        }
        if (result != sb) {
            throw new Error("insert did not return this instance");
        }
    }

    private static void testInsertWithNullObject() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(1, (Object) null);
            throw new Error("Expected NullPointerException for null object");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testInsertAtStart() {
        StringBuilder sb = new StringBuilder("World");
        sb.insert(0, "Hello ");
        if (!"Hello World".equals(sb.toString())) {
            throw new Error("Failed insert at start: " + sb.toString());
        }
    }

    private static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("Hello");
        sb.insert(sb.length(), " World");
        if (!"Hello World".equals(sb.toString())) {
            throw new Error("Failed insert at end: " + sb.toString());
        }
    }

    private static void testInsertWithInvalidOffset() {
        StringBuilder sb = new StringBuilder("Hello");
        try {
            sb.insert(-1, "X");
            throw new Error("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
        try {
            sb.insert(sb.length() + 1, "X");
            throw new Error("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
