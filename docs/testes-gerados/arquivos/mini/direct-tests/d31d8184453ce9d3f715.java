/*
 * @test
 * @summary Test StringBuilder.append(char[], int, int) method
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        testAppendCharArrayNormal();
        testAppendCharArrayEmpty();
        testAppendCharArrayFull();
        testAppendCharArrayOffsetLenZero();
    }

    private static void testAppendCharArrayNormal() {
        StringBuilder sb = new StringBuilder("Hello");
        char[] chars = {' ', 'W', 'o', 'r', 'l', 'd', '!'};
        sb.append(chars, 1, 5); // append "World"
        String result = sb.toString();
        if (!"HelloWorld".equals(result)) {
            throw new Error("testAppendCharArrayNormal failed: expected 'HelloWorld' but got '" + result + "'");
        }
    }

    private static void testAppendCharArrayEmpty() {
        StringBuilder sb = new StringBuilder("Test");
        char[] chars = {};
        sb.append(chars, 0, 0);
        String result = sb.toString();
        if (!"Test".equals(result)) {
            throw new Error("testAppendCharArrayEmpty failed: expected 'Test' but got '" + result + "'");
        }
    }

    private static void testAppendCharArrayFull() {
        StringBuilder sb = new StringBuilder();
        char[] chars = {'J', 'D', 'K', '1', '1'};
        sb.append(chars, 0, chars.length);
        String result = sb.toString();
        if (!"JDK11".equals(result)) {
            throw new Error("testAppendCharArrayFull failed: expected 'JDK11' but got '" + result + "'");
        }
    }

    private static void testAppendCharArrayOffsetLenZero() {
        StringBuilder sb = new StringBuilder("Start");
        char[] chars = {'a', 'b', 'c'};
        sb.append(chars, 0, 0); // append empty substring
        String result = sb.toString();
        if (!"Start".equals(result)) {
            throw new Error("testAppendCharArrayOffsetLenZero failed: expected 'Start' but got '" + result + "'");
        }
    }
}
