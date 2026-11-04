/*
 * @test
 * @summary Verify NoopNormalizer2.normalizeSecondAndAppend appends second to first and rejects identical instances
 * @modules java.base/sun.text.normalizer
 * @run main Norm2AllModesWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Norm2AllModesWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.text.normalizer.Norm2AllModes$NoopNormalizer2");
        Object n = c.getConstructor().newInstance();
        Method m = c.getMethod("normalizeSecondAndAppend", StringBuilder.class, CharSequence.class);

        StringBuilder first = new StringBuilder("ab");
        Object r = m.invoke(n, first, "cd");
        if (r != first) {
            throw new RuntimeException("expected same StringBuilder returned");
        }
        if (!first.toString().equals("abcd")) {
            throw new RuntimeException("expected abcd but was " + first);
        }

        StringBuilder f2 = new StringBuilder("q");
        m.invoke(n, f2, "");
        if (!f2.toString().equals("q")) {
            throw new RuntimeException("empty second should not change first");
        }

        StringBuilder other = new StringBuilder("zz");
        m.invoke(n, f2, other);
        if (!f2.toString().equals("qzz")) {
            throw new RuntimeException("expected qzz but was " + f2);
        }

        StringBuilder same = new StringBuilder("x");
        try {
            m.invoke(n, same, same);
            throw new RuntimeException("expected IllegalArgumentException for identical arguments");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause: " + e.getCause());
            }
        }
        if (!same.toString().equals("x")) {
            throw new RuntimeException("state changed after rejected call");
        }
    }
}
