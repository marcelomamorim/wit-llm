/*
 * @test
 * @summary Tests String.copyValueOf(char[], int, int) observable behavior
 * @run main StringWitupTest
 */
public class StringWitupTest {
    public static void main(String[] args) {
        char[] data = {'a', 'b', 'c', 'd'};

        check("bc".equals(String.copyValueOf(data, 1, 2)), "middle slice");
        check("d".equals(String.copyValueOf(data, 3, 1)), "last character");
        check("abcd".equals(String.copyValueOf(data, 0, data.length)), "full array");
        check("".equals(String.copyValueOf(data, 0, 0)), "zero count");
        check("".equals(String.copyValueOf(data, 4, 0)), "zero count at end");
        check("".equals(String.copyValueOf(new char[0], 0, 0)), "empty array");

        String copy = String.copyValueOf(data, 0, 4);
        data[0] = 'z';
        check("abcd".equals(copy), "result must not alias the source array");

        check(copy.equals(new String(new char[] {'z', 'b', 'c', 'd'}, 0, 4)),
              "result must match constructor with same arguments");

        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
