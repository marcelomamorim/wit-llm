/*
 * @test
 * @summary WITUP-derived behavioral checks for java.lang.StringUTF16.compress via reflection
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUTF16WitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        Method m = c.getDeclaredMethod("compress",
                byte[].class, int.class, byte[].class, int.class, int.class);
        m.setAccessible(true);

        byte[] src = {0x00, 0x61, 0x00, 0x62};
        byte[] dst = new byte[2];
        int r = (Integer) m.invoke(null, src, 0, dst, 0, 2);
        if (r != 2) {
            throw new RuntimeException("expected 2 but was " + r);
        }
        if (dst[0] != 0x61 || dst[1] != 0x62) {
            throw new RuntimeException("unexpected compressed bytes");
        }

        byte[] wide = {0x01, 0x00};
        byte[] dst2 = new byte[1];
        r = (Integer) m.invoke(null, wide, 0, dst2, 0, 1);
        if (r != 0) {
            throw new RuntimeException("expected 0 for char > 0xFF but was " + r);
        }

        expectSIOOBE(m, src, 0, new byte[4], 0, 3);
        expectSIOOBE(m, src, -1, new byte[4], 0, 1);
    }

    private static void expectSIOOBE(Method m, byte[] src, int srcOff,
                                     byte[] dst, int dstOff, int len) throws Exception {
        try {
            m.invoke(null, src, srcOff, dst, dstOff, len);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof StringIndexOutOfBoundsException)) {
                throw new RuntimeException("unexpected cause: " + e.getCause());
            }
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException");
    }
}
