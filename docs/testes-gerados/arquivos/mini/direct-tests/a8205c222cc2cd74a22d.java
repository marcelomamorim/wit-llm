/*
 * @test
 * @summary Test String.valueOf(char[], int, int) for correctness and boundary conditions
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testBasic();
        testEmptyCount();
        testFullArray();
        testOffsetZeroCountZero();
        testOffsetNonZeroCountOne();
    }

    private static void testBasic() {
        char[] data = {'a', 'b', 'c', 'd', 'e'};
        String s = String.valueOf(data, 1, 3);
        if (!s.equals("bcd")) {
            throw new Error("Expected 'bcd' but got '" + s + "'");
        }
    }

    private static void testEmptyCount() {
        char[] data = {'x', 'y', 'z'};
        String s = String.valueOf(data, 1, 0);
        if (!s.equals("")) {
            throw new Error("Expected empty string but got '" + s + "'");
        }
    }

    private static void testFullArray() {
        char[] data = {'h', 'e', 'l', 'l', 'o'};
        String s = String.valueOf(data, 0, data.length);
        if (!s.equals("hello")) {
            throw new Error("Expected 'hello' but got '" + s + "'");
        }
    }

    private static void testOffsetZeroCountZero() {
        char[] data = {'a', 'b', 'c'};
        String s = String.valueOf(data, 0, 0);
        if (!s.equals("")) {
            throw new Error("Expected empty string but got '" + s + "'");
        }
    }

    private static void testOffsetNonZeroCountOne() {
        char[] data = {'a', 'b', 'c'};
        String s = String.valueOf(data, 2, 1);
        if (!s.equals("c")) {
            throw new Error("Expected 'c' but got '" + s + "'");
        }
    }
}