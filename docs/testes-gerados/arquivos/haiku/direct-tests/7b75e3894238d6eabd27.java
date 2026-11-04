/*
 * @test
 * @summary Verifies StringUTF16.checkIndex bounds behavior using length(val) = val.length >> 1
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUTF16WitupTest {

    static Method checkIndexMethod;

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        checkIndexMethod = c.getDeclaredMethod("checkIndex", int.class, byte[].class);
        checkIndexMethod.setAccessible(true);

        // byte[4] -> UTF16 length 2, valid indices 0..1
        checkOk(0, new byte[4]);
        checkOk(1, new byte[4]);
        checkFails(2, new byte[4]);
        checkFails(-1, new byte[4]);

        // empty array -> length 0, every index is invalid
        checkFails(0, new byte[0]);

        // byte[2] -> length 1, only index 0 is valid
        checkOk(0, new byte[2]);
        checkFails(1, new byte[2]);

        // odd-length byte[3] -> length 1 (3 >> 1), index 1 is invalid
        checkOk(0, new byte[3]);
        checkFails(1, new byte[3]);
    }

    static void invoke(int off, byte[] val) throws Exception {
        try {
            checkIndexMethod.invoke(null, off, val);
        } catch (InvocationTargetException e) {
            throw e;
        }
    }

    static void checkOk(int off, byte[] val) throws Exception {
        try {
            invoke(off, val);
        } catch (InvocationTargetException e) {
            throw new AssertionError("Unexpected exception for off=" + off
                    + ", length=" + val.length + ": " + e.getCause());
        }
    }

    static void checkFails(int off, byte[] val) throws Exception {
        try {
            invoke(off, val);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof StringIndexOutOfBoundsException) {
                return;
            }
            throw new AssertionError("Wrong exception type for off=" + off
                    + ", length=" + val.length + ": " + cause);
        }
        throw new AssertionError("Expected StringIndexOutOfBoundsException for off="
                + off + ", length=" + val.length);
    }
}
