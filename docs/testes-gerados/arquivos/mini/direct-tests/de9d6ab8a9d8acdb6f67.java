/*
 * @test
 * @summary Basic tests for String.charAt(int) method
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testCharAtBasic();
        testCharAtBoundary();
        testCharAtEmptyString();
    }

    private static void testCharAtBasic() {
        String s = "abc";
        char c0 = s.charAt(0);
        char c1 = s.charAt(1);
        char c2 = s.charAt(2);
        if (c0 != 'a' || c1 != 'b' || c2 != 'c') {
            throw new Error("charAt returned wrong character for basic string");
        }
    }

    private static void testCharAtBoundary() {
        String s = "hello";
        // test first character
        if (s.charAt(0) != 'h') {
            throw new Error("charAt failed at index 0");
        }
        // test last character
        if (s.charAt(s.length() - 1) != 'o') {
            throw new Error("charAt failed at last index");
        }
    }

    private static void testCharAtEmptyString() {
        String s = "";
        try {
            s.charAt(0);
            throw new Error("Expected StringIndexOutOfBoundsException for empty string charAt(0)");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
