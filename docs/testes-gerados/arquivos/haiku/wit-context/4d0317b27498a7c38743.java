/*
 * @test
 * @summary WITUP-derived behavioral tests for StringLatin1.fillNull range checks and zero-fill
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringLatin1WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringLatin1WitupTest {

    static Method fillNull;

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringLatin1");
        fillNull = c.getDeclaredMethod("fillNull", byte[].class, int.class, int.class);
        fillNull.setAccessible(true);

        byte[] a = {1, 2, 3, 4, 5};
        call(a, 1, 3);
        if (a[0] != 1 || a[1] != 0 || a[2] != 0 || a[3] != 4 || a[4] != 5) {
            throw new AssertionError("unexpected contents after fillNull(1,3)");
        }

        byte[] b = {7, 7, 7};
        call(b, 0, 0);
        call(b, 3, 3);
        if (b[0] != 7 || b[1] != 7 || b[2] != 7) {
            throw new AssertionError("empty range modified array");
        }

        expectFailure(new byte[3], -1, 2, ArrayIndexOutOfBoundsException.class);
        expectFailure(new byte[3], 2, 1, IllegalArgumentException.class);
        expectFailure(new byte[3], 0, 4, ArrayIndexOutOfBoundsException.class);
    }

    static void call(byte[] val, int index, int end) throws Exception {
        fillNull.invoke(null, val, index, end);
    }

    static void expectFailure(byte[] val, int index, int end, Class<? extends Throwable> expected)
            throws Exception {
        try {
            fillNull.invoke(null, val, index, end);
        } catch (InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            if (!expected.isInstance(cause)) {
                throw new AssertionError("expected " + expected.getName() + " but got " + cause, cause);
            }
            return;
        }
        throw new AssertionError("expected " + expected.getName() + " for index=" + index + ", end=" + end);
    }
}
