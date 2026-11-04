/*
 * @test
 * @summary WITUP-derived checks for StringLatin1.inflate(byte[], int, int) via reflection
 * @modules java.base/java.lang
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringLatin1WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringLatin1WitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringLatin1");
        Method m = c.getDeclaredMethod("inflate", byte[].class, int.class, int.class);
        m.setAccessible(true);

        byte[] src = {'A', 'B', 'C'};

        byte[] r = (byte[]) m.invoke(null, src, 0, 3);
        if (r.length != 6) {
            throw new RuntimeException("expected length 6 but was " + r.length);
        }

        byte[] empty = (byte[]) m.invoke(null, src, 0, 0);
        if (empty.length != 0) {
            throw new RuntimeException("expected length 0 but was " + empty.length);
        }

        try {
            m.invoke(null, src, 0, -1);
            throw new RuntimeException("expected InvocationTargetException for negative len");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof NegativeArraySizeException)) {
                throw new RuntimeException("unexpected cause: " + e.getCause(), e);
            }
        }
    }
}
