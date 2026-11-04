/*
 * @test
 * @summary Verifies observable behavior of StringBuilder.insert(int, String), including return value and offset handling.
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("start");
        StringBuilder ret = sb.insert(4, "le");
        check(ret == sb, "insert must return the same builder instance");
        check("starlet".equals(sb.toString()), "insert in middle: got " + sb);

        sb = new StringBuilder("abc");
        sb.insert(0, "X");
        check("Xabc".equals(sb.toString()), "insert at offset 0: got " + sb);

        sb.insert(sb.length(), "Z");
        check("XabcZ".equals(sb.toString()), "insert at end: got " + sb);

        sb.insert(2, "");
        check("XabcZ".equals(sb.toString()), "inserting empty string must not change content: got " + sb);

        StringBuilder empty = new StringBuilder();
        empty.insert(0, "hi");
        check("hi".equals(empty.toString()), "insert into empty builder: got " + empty);

        StringBuilder small = new StringBuilder(1);
        small.insert(0, "longer text");
        check("longer text".equals(small.toString()), "insert forcing growth: got " + small);
        check(small.length() == 11, "length after growth insert: got " + small.length());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
