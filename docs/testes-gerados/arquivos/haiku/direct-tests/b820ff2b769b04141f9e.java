/*
 * @test
 * @summary Verifies StringUTF16.checkOffset accepts offsets in [0, length] and rejects others with StringIndexOutOfBoundsException
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUTF16WitupTest {

    static Method checkOffset;

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        checkOffset = c.getDeclaredMethod("checkOffset", int.class, byte[].class);
        checkOffset.setAccessible(true);

        byte[] val = new byte[4];
        expectOk(0, val);
        expectOk(2, val);
        expectOk(0, new byte[0]);

        expectSioobe(3, val);
        expectSioobe(-1, val);
        expectSioobe(1, new byte[0]);
    }

    static void expectOk(int off, byte[] val) throws Exception {
        try {
            checkOffset.invoke(null, off, val);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("unexpected exception for off=" + off + ": " + e.getCause());
        }
    }

    static void expectSioobe(int off, byte[] val) throws Exception {
        try {
            checkOffset.invoke(null, off, val);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof StringIndexOutOfBoundsException)) {
                throw new RuntimeException("wrong exception for off=" + off + ": " + e.getCause());
            }
            return;
        }
        throw new RuntimeException("no exception for off=" + off);
    }
}
