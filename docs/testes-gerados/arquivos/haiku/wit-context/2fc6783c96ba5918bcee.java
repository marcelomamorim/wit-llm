/*
 * @test
 * @summary Basic observable behavior of String.getBytes() using the default charset
 * @run main StringWitupTest
 */
public class StringWitupTest {
    public static void main(String[] args) {
        String abc = "abc";
        byte[] b = abc.getBytes();
        check(b != null, "result must not be null");
        check(b.length == 3, "length of bytes for abc must be 3");
        check(b[0] == 'a' && b[1] == 'b' && b[2] == 'c', "content of bytes for abc");

        byte[] expected = abc.getBytes(java.nio.charset.Charset.defaultCharset());
        check(expected.length == b.length, "must match default charset encoding length");
        for (int i = 0; i < b.length; i++) {
            check(b[i] == expected[i], "must match default charset encoding at index " + i);
        }

        byte[] empty = "".getBytes();
        check(empty != null && empty.length == 0, "empty string must yield empty array");

        byte[] first = abc.getBytes();
        first[0] = 'z';
        byte[] second = abc.getBytes();
        check(second[0] == 'a', "returned array must be a fresh copy, not shared state");
        check(first != second, "each call must return a new array");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
