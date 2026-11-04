/*
 * @test
 * @summary Verify NoopNormalizer2.normalizeSecondAndAppend rejects identical arguments and appends otherwise
 * @modules java.base/sun.text.normalizer
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED Norm2AllModesWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Norm2AllModesWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.text.normalizer.Norm2AllModes$NoopNormalizer2");
        Constructor<?> ctor = c.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object n = ctor.newInstance();
        Method m = c.getMethod("normalizeSecondAndAppend", StringBuilder.class, CharSequence.class);
        m.setAccessible(true);

        StringBuilder same = new StringBuilder("abc");
        try {
            m.invoke(n, same, same);
            throw new AssertionError("expected IllegalArgumentException for first == second");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new AssertionError("unexpected cause: " + e.getCause());
            }
        }

        StringBuilder first = new StringBuilder("abc");
        Object r = m.invoke(n, first, "def");
        if (r != first) {
            throw new AssertionError("expected returned value to be the first argument");
        }
        if (!first.toString().equals("abcdef")) {
            throw new AssertionError("unexpected content: " + first);
        }

        StringBuilder empty = new StringBuilder("x");
        Object r2 = m.invoke(n, empty, "");
        if (r2 != empty || !empty.toString().equals("x")) {
            throw new AssertionError("appending empty sequence changed content: " + empty);
        }
    }
}
