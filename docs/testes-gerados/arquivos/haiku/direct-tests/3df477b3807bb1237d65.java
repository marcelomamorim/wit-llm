/*
 * @test
 * @summary Verifies StringUTF16.putCharsSB writes UTF16 big-endian chars and rejects out-of-range bounds
 * @modules java.base/java.lang
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUTF16WitupTest {

    static Method putCharsSB;

    static void invoke(byte[] val, int index, CharSequence s, int off, int end)
            throws Exception {
        try {
            putCharsSB.invoke(null, val, index, s, off, end);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw e;
        }
    }

    static char readChar(byte[] val, int index) {
        return (char) (((val[index * 2] & 0xff) << 8) | (val[index * 2 + 1] & 0xff));
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        putCharsSB = c.getDeclaredMethod("putCharsSB",
                byte[].class, int.class, CharSequence.class, int.class, int.class);
        putCharsSB.setAccessible(true);

        // Full write of "abc" at index 0
        byte[] v1 = new byte[6];
        invoke(v1, 0, "abc", 0, 3);
        check(readChar(v1, 0) == 'a', "expected 'a' at 0");
        check(readChar(v1, 1) == 'b', "expected 'b' at 1");
        check(readChar(v1, 2) == 'c', "expected 'c' at 2");

        // Sub-range "yz" from "xyzw" written at index 1
        byte[] v2 = new byte[6];
        invoke(v2, 1, "xyzw", 1, 3);
        check(readChar(v2, 0) == 0, "index 0 must be untouched");
        check(readChar(v2, 1) == 'y', "expected 'y' at 1");
        check(readChar(v2, 2) == 'z', "expected 'z' at 2");

        // Non-ASCII char is stored as UTF16 big-endian
        byte[] v3 = new byte[2];
        invoke(v3, 0, "\u4e2d", 0, 1);
        check(v3[0] == (byte) 0x4e && v3[1] == (byte) 0x2d, "UTF16 BE encoding mismatch");

        // Empty range leaves array unchanged
        byte[] v4 = new byte[4];
        v4[0] = 7;
        invoke(v4, 0, "abc", 1, 1);
        check(v4[0] == 7 && v4[1] == 0 && v4[2] == 0 && v4[3] == 0, "empty range modified array");

        // Out of range destination must fail with an IndexOutOfBoundsException
        byte[] v5 = new byte[2];
        boolean thrown = false;
        try {
            invoke(v5, 1, "abc", 0, 3);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected IndexOutOfBoundsException for overflowing write");
    }
}
