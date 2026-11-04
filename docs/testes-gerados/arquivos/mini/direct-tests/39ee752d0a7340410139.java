/*
 * @test
 * @summary Test StringBuilder.deleteCharAt(int) method behavior
 * @run main StringBuilderWitupTest
 */
public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testDeleteCharAtValidIndex();
        testDeleteCharAtFirstChar();
        testDeleteCharAtLastChar();
        testDeleteCharAtEmptyBuilder();
        testDeleteCharAtIndexOutOfBounds();
    }

    private static void testDeleteCharAtValidIndex() {
        StringBuilder sb = new StringBuilder("hello");
        sb.deleteCharAt(1); // remove 'e'
        String result = sb.toString();
        if (!"hllo".equals(result)) {
            throw new AssertionError("Expected 'hllo' but got '" + result + "'");
        }
    }

    private static void testDeleteCharAtFirstChar() {
        StringBuilder sb = new StringBuilder("abc");
        sb.deleteCharAt(0); // remove 'a'
        String result = sb.toString();
        if (!"bc".equals(result)) {
            throw new AssertionError("Expected 'bc' but got '" + result + "'");
        }
    }

    private static void testDeleteCharAtLastChar() {
        StringBuilder sb = new StringBuilder("xyz");
        sb.deleteCharAt(sb.length() - 1); // remove 'z'
        String result = sb.toString();
        if (!"xy".equals(result)) {
            throw new AssertionError("Expected 'xy' but got '" + result + "'");
        }
    }

    private static void testDeleteCharAtEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.deleteCharAt(0);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for empty builder");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testDeleteCharAtIndexOutOfBounds() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.deleteCharAt(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.deleteCharAt(sb.length());
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index == length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
