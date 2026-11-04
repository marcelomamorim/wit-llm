/*
 * @test
 * @summary Verifies StringUTF16.checkBoundsOffCount accepts valid offset/count pairs and rejects out-of-range ones
 * @run main/othervm --add-opens java.base/java.lang=ALL-UNNAMED StringUTF16WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StringUTF16WitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.lang.StringUTF16");
        Method m = c.getDeclaredMethod("checkBoundsOffCount", int.class, int.class, byte[].class);
        m.setAccessible(true);

        byte[] val = new byte[10];

        int[][] ok = {{0, 5}, {5, 0}, {2, 3}, {0, 0}};
        for (int[] o : ok) {
            invoke(m, o[0], o[1], val, false);
        }

        int[][] bad = {{-1, 1}, {0, -1}, {3, 3}, {6, 0}};
        for (int[] b : bad) {
            invoke(m, b[0], b[1], val, true);
        }
    }

    static void invoke(Method m, int offset, int count, byte[] val, boolean expectFailure)
            throws Exception {
        try {
            m.invoke(null, offset, count, val);
            if (expectFailure) {
                throw new RuntimeException("No exception for offset=" + offset + ", count=" + count);
            }
        } catch (InvocationTargetException e) {
            if (!expectFailure || !(e.getCause() instanceof StringIndexOutOfBoundsException)) {
                throw new RuntimeException("Unexpected cause for offset=" + offset + ", count=" + count,
                                           e.getCause());
            }
        }
    }
}
