/*
 * @test
 * @summary Test StringBuffer.replace(int, int, String) basic and boundary behaviors
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testReplaceBasic();
        testReplaceEmptyString();
        testReplaceFullRange();
        testReplaceNoChange();
        testReplaceWithNullString();
    }

    private static void testReplaceBasic() {
        StringBuffer sb = new StringBuffer("HelloWorld");
        sb.replace(5, 7, " ");
        String result = sb.toString();
        if (!"Hello rld".equals(result)) {
            throw new Error("testReplaceBasic failed: expected 'Hello rld' but got '" + result + "'");
        }
    }

    private static void testReplaceEmptyString() {
        StringBuffer sb = new StringBuffer("abcde");
        sb.replace(2, 2, "XYZ"); // insert at index 2
        String result = sb.toString();
        if (!"abXYZcde".equals(result)) {
            throw new Error("testReplaceEmptyString failed: expected 'abXYZcde' but got '" + result + "'");
        }
    }

    private static void testReplaceFullRange() {
        StringBuffer sb = new StringBuffer("12345");
        sb.replace(0, sb.length(), "abc");
        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new Error("testReplaceFullRange failed: expected 'abc' but got '" + result + "'");
        }
    }

    private static void testReplaceNoChange() {
        StringBuffer sb = new StringBuffer("unchanged");
        sb.replace(3, 3, ""); // replace empty range with empty string
        String result = sb.toString();
        if (!"unchanged".equals(result)) {
            throw new Error("testReplaceNoChange failed: expected 'unchanged' but got '" + result + "'");
        }
    }

    private static void testReplaceWithNullString() {
        StringBuffer sb = new StringBuffer("nulltest");
        try {
            sb.replace(0, 4, null);
            throw new Error("testReplaceWithNullString failed: expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
