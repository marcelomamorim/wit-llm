/*
 * @test
 * @summary Test StringBuilder.insert(int, double) method
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testInsertDoubleAtStart();
        testInsertDoubleAtMiddle();
        testInsertDoubleAtEnd();
        testInsertDoubleEmptyBuilder();
    }

    private static void testInsertDoubleAtStart() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(0, 1.23d);
        String result = sb.toString();
        if (!result.startsWith("1.23")) {
            throw new Error("Failed insert double at start: " + result);
        }
    }

    private static void testInsertDoubleAtMiddle() {
        StringBuilder sb = new StringBuilder("abcdef");
        sb.insert(3, -4.56d);
        String result = sb.toString();
        if (!result.contains("-4.56")) {
            throw new Error("Failed insert double at middle: " + result);
        }
        if (!result.startsWith("abc") || !result.endsWith("def")) {
            throw new Error("Unexpected string content after insert: " + result);
        }
    }

    private static void testInsertDoubleAtEnd() {
        StringBuilder sb = new StringBuilder("xyz");
        sb.insert(sb.length(), 7.89d);
        String result = sb.toString();
        if (!result.endsWith("7.89")) {
            throw new Error("Failed insert double at end: " + result);
        }
        if (!result.startsWith("xyz")) {
            throw new Error("Unexpected string content after insert at end: " + result);
        }
    }

    private static void testInsertDoubleEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, 0.0d);
        String result = sb.toString();
        if (!result.equals("0.0")) {
            throw new Error("Failed insert double into empty builder: " + result);
        }
    }
}
