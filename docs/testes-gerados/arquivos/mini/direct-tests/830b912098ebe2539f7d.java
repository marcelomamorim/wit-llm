/*
 * @test
 * @summary Test StringBuilder.insert(int, long) method behavior
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertLongAtStart();
        testInsertLongAtMiddle();
        testInsertLongAtEnd();
        testInsertLongEmptyBuilder();
    }

    private static void testInsertLongAtStart() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(0, 12345L);
        String result = sb.toString();
        if (!result.equals("12345abc")) {
            throw new Error("testInsertLongAtStart failed: got '" + result + "'");
        }
    }

    private static void testInsertLongAtMiddle() {
        StringBuilder sb = new StringBuilder("abcdef");
        sb.insert(3, 67890L);
        String result = sb.toString();
        if (!result.equals("abc67890def")) {
            throw new Error("testInsertLongAtMiddle failed: got '" + result + "'");
        }
    }

    private static void testInsertLongAtEnd() {
        StringBuilder sb = new StringBuilder("xyz");
        sb.insert(sb.length(), 0L);
        String result = sb.toString();
        if (!result.equals("xyz0")) {
            throw new Error("testInsertLongAtEnd failed: got '" + result + "'");
        }
    }

    private static void testInsertLongEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, -999L);
        String result = sb.toString();
        if (!result.equals("-999")) {
            throw new Error("testInsertLongEmptyBuilder failed: got '" + result + "'");
        }
    }
}
