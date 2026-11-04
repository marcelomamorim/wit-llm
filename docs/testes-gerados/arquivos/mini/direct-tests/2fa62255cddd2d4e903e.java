/*
 * @test
 * @summary Test StringBuilder.insert(int, char[]) method behavior
 * @run main StringBuilderWitupTest
 */
public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertCharArrayAtStart();
        testInsertCharArrayAtMiddle();
        testInsertCharArrayAtEnd();
        testInsertNullCharArray();
        testInsertCharArrayInvalidOffset();
    }

    private static void testInsertCharArrayAtStart() {
        StringBuilder sb = new StringBuilder("world");
        char[] chars = {'H', 'e', 'l', 'l', 'o', ' '};
        sb.insert(0, chars);
        String result = sb.toString();
        if (!"Hello world".equals(result)) {
            throw new AssertionError("Expected 'Hello world' but got '" + result + "'");
        }
    }

    private static void testInsertCharArrayAtMiddle() {
        StringBuilder sb = new StringBuilder("Helo");
        char[] chars = {'l'};
        sb.insert(3, chars);
        String result = sb.toString();
        if (!"Hello".equals(result)) {
            throw new AssertionError("Expected 'Hello' but got '" + result + "'");
        }
    }

    private static void testInsertCharArrayAtEnd() {
        StringBuilder sb = new StringBuilder("Hello");
        char[] chars = {' ', 'W', 'o', 'r', 'l', 'd'};
        sb.insert(sb.length(), chars);
        String result = sb.toString();
        if (!"Hello World".equals(result)) {
            throw new AssertionError("Expected 'Hello World' but got '" + result + "'");
        }
    }

    private static void testInsertNullCharArray() {
        StringBuilder sb = new StringBuilder("test");
        try {
            sb.insert(2, (char[]) null);
            throw new AssertionError("Expected NullPointerException for null char[]");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testInsertCharArrayInvalidOffset() {
        StringBuilder sb = new StringBuilder("test");
        char[] chars = {'a'};
        try {
            sb.insert(-1, chars);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.insert(sb.length() + 1, chars);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
