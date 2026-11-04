/*
 * @test
 * @summary StringUTF16.newBytesFor returns correctly sized byte arrays and throws for invalid lengths
 * @modules java.base/java.lang
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUTF16WitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectThrow(Method m, int arg, Class<? extends Throwable> expected) throws Exception {
        try {
            m.invoke(null, arg);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            check(expected.isInstance(cause),
                  "expected " + expected.getName() + " but got " + cause);
            return;
        }
        throw new RuntimeException("no exception thrown for arg " + arg);
    }

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        Method m = c.getMethod("newBytesFor", int.class);
        m.setAccessible(true);

        byte[] r0 = (byte[]) m.invoke(null, 0);
        check(r0.length == 0, "length 0 should give empty array");

        byte[] r1 = (byte[]) m.invoke(null, 1);
        check(r1.length == 2, "length 1 should give 2 bytes");

        byte[] r3 = (byte[]) m.invoke(null, 3);
        check(r3.length == 6, "length 3 should give 6 bytes");

        expectThrow(m, -1, NegativeArraySizeException.class);
        expectThrow(m, Integer.MAX_VALUE, OutOfMemoryError.class);
    }
}
