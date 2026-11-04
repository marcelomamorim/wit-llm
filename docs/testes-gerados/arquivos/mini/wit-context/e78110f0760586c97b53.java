/*
 * @test
 * @summary Test StringBuilder.replace(int, int, String) for valid and invalid ranges
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testReplaceValid();
        testReplaceInvalidStartNegative();
        testReplaceInvalidEndLessThanStart();
        testReplaceInvalidEndGreaterThanLength();
    }

    private static void testReplaceValid() {
        StringBuilder sb = new StringBuilder("HelloWorld");
        StringBuilder result = sb.replace(5, 7, " ");
        if (!"Hello rld".equals(sb.toString())) {
            throw new Error("testReplaceValid failed: expected 'Hello rld' but got '" + sb.toString() + "'");
        }
        if (result != sb) {
            throw new Error("testReplaceValid failed: replace should return this");
        }
    }

    private static void testReplaceInvalidStartNegative() {
        StringBuilder sb = new StringBuilder("Hello");
        try {
            sb.replace(-1, 2, "X");
            throw new Error("testReplaceInvalidStartNegative failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testReplaceInvalidEndLessThanStart() {
        StringBuilder sb = new StringBuilder("Hello");
        try {
            sb.replace(3, 2, "X");
            throw new Error("testReplaceInvalidEndLessThanStart failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testReplaceInvalidEndGreaterThanLength() {
        StringBuilder sb = new StringBuilder("Hello");
        try {
            sb.replace(1, 10, "X");
            throw new Error("testReplaceInvalidEndGreaterThanLength failed: expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
