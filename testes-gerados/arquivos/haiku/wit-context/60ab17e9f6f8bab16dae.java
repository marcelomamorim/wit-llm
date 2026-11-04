/*
 * @test
 * @summary Verify StringBuffer.insert(int, int) inserts the decimal form of the value and rejects invalid offsets
 * @run main StringBufferWitupTest
 */
public class StringBufferWitupTest {
    public static void main(String[] args) {
        check(new StringBuffer("abc").insert(1, 42).toString(), "a42bc");
        check(new StringBuffer("abc").insert(0, -7).toString(), "-7abc");
        check(new StringBuffer("ab").insert(2, 5).toString(), "ab5");
        check(new StringBuffer().insert(0, 0).toString(), "0");
        check(new StringBuffer("x").insert(0, Integer.MIN_VALUE).toString(), "-2147483648x");

        StringBuffer sb = new StringBuffer("abc");
        if (sb.insert(3, 9) != sb) {
            throw new AssertionError("insert must return the same StringBuffer instance");
        }
        check(sb.toString(), "abc9");

        expectSIOOBE(new StringBuffer("abc"), -1, 1);
        expectSIOOBE(new StringBuffer("abc"), 4, 1);
        expectSIOOBE(new StringBuffer(), 1, 1);
    }

    static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new AssertionError("expected " + expected + " but got " + actual);
        }
    }

    static void expectSIOOBE(StringBuffer sb, int offset, int value) {
        try {
            sb.insert(offset, value);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("expected StringIndexOutOfBoundsException for offset " + offset);
    }
}
