/*
 * @test
 * @summary Test StringBuilder.replace(int, int, String) basic and boundary behaviors
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testReplaceBasic();
        testReplaceEmptyRange();
        testReplaceFullRange();
        testReplaceWithNullString();
        testReplaceWithStartEqualsEnd();
    }

    private static void testReplaceBasic() {
        StringBuilder sb = new StringBuilder("HelloWorld");
        StringBuilder result = sb.replace(5, 7, " ");
        if (!"Hello rld".equals(sb.toString())) {
            throw new Error("testReplaceBasic failed: expected 'Hello rld' but got '" + sb + "'");
        }
        if (result != sb) {
            throw new Error("testReplaceBasic failed: replace should return this");
        }
    }

    private static void testReplaceEmptyRange() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.replace(1, 1, "X");
        if (!"aXbc".equals(sb.toString())) {
            throw new Error("testReplaceEmptyRange failed: expected 'aXbc' but got '" + sb + "'");
        }
        if (result != sb) {
            throw new Error("testReplaceEmptyRange failed: replace should return this");
        }
    }

    private static void testReplaceFullRange() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.replace(0, sb.length(), "xyz");
        if (!"xyz".equals(sb.toString())) {
            throw new Error("testReplaceFullRange failed: expected 'xyz' but got '" + sb + "'");
        }
        if (result != sb) {
            throw new Error("testReplaceFullRange failed: replace should return this");
        }
    }

    private static void testReplaceWithNullString() {
        StringBuilder sb = new StringBuilder("abc");
        boolean npeThrown = false;
        try {
            sb.replace(0, 1, null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new Error("testReplaceWithNullString failed: expected NullPointerException");
        }
    }

    private static void testReplaceWithStartEqualsEnd() {
        StringBuilder sb = new StringBuilder("abc");
        StringBuilder result = sb.replace(2, 2, "Z");
        if (!"abZc".equals(sb.toString())) {
            throw new Error("testReplaceWithStartEqualsEnd failed: expected 'abZc' but got '" + sb + "'");
        }
        if (result != sb) {
            throw new Error("testReplaceWithStartEqualsEnd failed: replace should return this");
        }
    }
}
