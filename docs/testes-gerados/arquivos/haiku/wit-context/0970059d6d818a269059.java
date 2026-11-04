/*
 * @test
 * @summary Witup generated tests for StringUTF16.newBytesFor via reflection
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUTF16WitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        Method m = c.getDeclaredMethod("newBytesFor", int.class);
        m.setAccessible(true);

        testZeroLength(m);
        testSmallLengthsReturnDoubleSize(m);
        testNegativeLengthThrowsNegativeArraySize(m, -1);
        testNegativeLengthThrowsNegativeArraySize(m, Integer.MIN_VALUE);
        testNegativeLengthThrowsNegativeArraySize(m, -Integer.MAX_VALUE);
    }

    private static void testZeroLength(Method m) throws Exception {
        byte[] r = (byte[]) m.invoke(null, 0);
        if (r == null || r.length != 0) {
            throw new AssertionError("expected empty array for len 0");
        }
    }

    private static void testSmallLengthsReturnDoubleSize(Method m) throws Exception {
        int[] lengths = {1, 2, 5, 16};
        for (int len : lengths) {
            byte[] r = (byte[]) m.invoke(null, len);
            if (r == null || r.length != len * 2) {
                throw new AssertionError("expected byte length " + (len * 2)
                        + " for len " + len + " but got "
                        + (r == null ? "null" : String.valueOf(r.length)));
            }
        }
    }

    private static void testNegativeLengthThrowsNegativeArraySize(Method m, int len) throws Exception {
        try {
            m.invoke(null, len);
            throw new AssertionError("expected NegativeArraySizeException for len " + len);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof NegativeArraySizeException)) {
                throw new AssertionError("unexpected cause for len " + len + ": " + cause);
            }
        }
    }
}
