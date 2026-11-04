/*
 * @test
 * @summary Verify StringLatin1.inflate expands Latin1 bytes to UTF16 bytes in native byte order
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringLatin1WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteOrder;

public class StringLatin1WitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringLatin1");
        Method m = c.getDeclaredMethod("inflate", byte[].class, int.class, int.class);
        m.setAccessible(true);

        check(m, new byte[]{1, (byte) 0xff, 0x41}, 0, 3);
        check(m, new byte[]{1, 2, 3}, 1, 2);
        check(m, new byte[]{7, 8}, 0, 0);

        byte[] empty = invoke(m, new byte[]{5}, 0, 0);
        if (empty.length != 0) {
            throw new RuntimeException("expected empty result for len 0");
        }
    }

    static byte[] invoke(Method m, byte[] value, int off, int len) throws Exception {
        try {
            return (byte[]) m.invoke(null, value, off, len);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("inflate threw " + e.getCause(), e.getCause());
        }
    }

    static void check(Method m, byte[] value, int off, int len) throws Exception {
        byte[] r = invoke(m, value, off, len);
        if (r.length != 2 * len) {
            throw new RuntimeException("unexpected length " + r.length + " for len " + len);
        }
        boolean bigEndian = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
        for (int i = 0; i < len; i++) {
            int c = value[off + i] & 0xff;
            byte first = bigEndian ? (byte) 0 : (byte) c;
            byte second = bigEndian ? (byte) c : (byte) 0;
            if (r[2 * i] != first || r[2 * i + 1] != second) {
                throw new RuntimeException("mismatch at index " + i + " for char " + c);
            }
        }
    }
}
