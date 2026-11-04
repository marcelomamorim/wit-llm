/*
 * @test
 * @summary Test String.copyValueOf(char[], int, int) basic and boundary cases
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testCopyValueOfBasic();
        testCopyValueOfEmpty();
        testCopyValueOfFullArray();
        testCopyValueOfSingleChar();
    }

    private static void testCopyValueOfBasic() {
        char[] data = {'a', 'b', 'c', 'd', 'e'};
        String s = String.copyValueOf(data, 1, 3);
        if (!s.equals("bcd")) {
            throw new Error("Expected 'bcd' but got '" + s + "'");
        }
    }

    private static void testCopyValueOfEmpty() {
        char[] data = {'x', 'y', 'z'};
        String s = String.copyValueOf(data, 1, 0);
        if (!s.equals("")) {
            throw new Error("Expected empty string but got '" + s + "'");
        }
    }

    private static void testCopyValueOfFullArray() {
        char[] data = {'h', 'e', 'l', 'l', 'o'};
        String s = String.copyValueOf(data, 0, data.length);
        if (!s.equals("hello")) {
            throw new Error("Expected 'hello' but got '" + s + "'");
        }
    }

    private static void testCopyValueOfSingleChar() {
        char[] data = {'z'};
        String s = String.copyValueOf(data, 0, 1);
        if (!s.equals("z")) {
            throw new Error("Expected 'z' but got '" + s + "'");
        }
    }
}