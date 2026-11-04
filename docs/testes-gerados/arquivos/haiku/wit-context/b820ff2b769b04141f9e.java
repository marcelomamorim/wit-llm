/*
 * @test
 * @summary Verifies StringUTF16.checkOffset bounds behavior through reflection
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUTF16WitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        Method m = c.getDeclaredMethod("checkOffset", int.class, byte[].class);
        m.setAccessible(true);

        byte[] val = new byte[4];
        expectOk(m, 0, val);
        expectOk(m, 1, val);
        expectOk(m, 2, val);
        expectStringIndexOutOfBounds(m, -1, val);
        expectStringIndexOutOfBounds(m, 3, val);
        expectStringIndexOutOfBounds(m, Integer.MIN_VALUE, val);
        expectStringIndexOutOfBounds(m, Integer.MAX_VALUE, val);

        byte[] empty = new byte[0];
        expectOk(m, 0, empty);
        expectStringIndexOutOfBounds(m, 1, empty);
    }

    static void expectOk(Method m, int off, byte[] val) throws Exception {
        try {
            m.invoke(null, off, val);
        } catch (InvocationTargetException e) {
            throw new AssertionError("unexpected exception for off=" + off + ": " + e.getCause(), e);
        }
    }

    static void expectStringIndexOutOfBounds(Method m, int off, byte[] val) throws Exception {
        try {
            m.invoke(null, off, val);
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (!(t instanceof StringIndexOutOfBoundsException)) {
                throw new AssertionError("wrong cause for off=" + off + ": " + t, e);
            }
            return;
        }
        throw new AssertionError("no exception for off=" + off);
    }
}
