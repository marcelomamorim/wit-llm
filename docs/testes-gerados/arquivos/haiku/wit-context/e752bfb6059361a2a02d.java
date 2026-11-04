/*
 * @test
 * @summary Witup checks for StringUTF16.inflate bounds validation and observable copy behavior
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class StringUTF16WitupTest {

    private static final Method INFLATE;

    static {
        try {
            Class<?> c = Class.forName("java.lang.StringUTF16");
            INFLATE = c.getDeclaredMethod("inflate", byte[].class, int.class, byte[].class, int.class, int.class);
            INFLATE.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        testValidInflate();
        byte[] src = new byte[] {1, 2, (byte) 0xff};
        expectStringIndexOutOfBounds(src, 0, new byte[4], 0, 3);
        expectStringIndexOutOfBounds(src, 0, new byte[6], -1, 1);
        expectStringIndexOutOfBounds(src, 0, new byte[6], 0, -1);
    }

    private static void testValidInflate() throws Exception {
        byte[] src = new byte[] {1, 2, (byte) 0xff};
        byte[] dst = new byte[6];
        invoke(src, 0, dst, 0, 3);
        byte[] expected = new byte[] {0, 1, 0, 2, 0, (byte) 0xff};
        if (!Arrays.equals(expected, dst)) {
            throw new RuntimeException("unexpected inflate result: " + Arrays.toString(dst));
        }
    }

    private static void expectStringIndexOutOfBounds(byte[] src, int srcOff, byte[] dst, int dstOff, int len)
            throws Exception {
        try {
            invoke(src, srcOff, dst, dstOff, len);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof StringIndexOutOfBoundsException) {
                return;
            }
            throw new RuntimeException("unexpected cause: " + cause, cause);
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException");
    }

    private static void invoke(byte[] src, int srcOff, byte[] dst, int dstOff, int len)
            throws InvocationTargetException, IllegalAccessException {
        INFLATE.invoke(null, src, srcOff, dst, dstOff, len);
    }
}
