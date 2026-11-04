/*
 * @test
 * @summary Test StringBuilder.insert(int, boolean) method
 * @run main StringBuilderWitupTest
 */
public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertBooleanAtStart();
        testInsertBooleanAtMiddle();
        testInsertBooleanAtEnd();
        testInsertBooleanEmptyBuilder();
    }

    private static void testInsertBooleanAtStart() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(0, true);
        String result = sb.toString();
        if (!result.equals("trueabc")) {
            throw new AssertionError("Expected 'trueabc' but got '" + result + "'");
        }
    }

    private static void testInsertBooleanAtMiddle() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(1, false);
        String result = sb.toString();
        if (!result.equals("afalsebc")) {
            throw new AssertionError("Expected 'afalsebc' but got '" + result + "'");
        }
    }

    private static void testInsertBooleanAtEnd() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(sb.length(), true);
        String result = sb.toString();
        if (!result.equals("abctrue")) {
            throw new AssertionError("Expected 'abctrue' but got '" + result + "'");
        }
    }

    private static void testInsertBooleanEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, false);
        String result = sb.toString();
        if (!result.equals("false")) {
            throw new AssertionError("Expected 'false' but got '" + result + "'");
        }
    }
}
