/*
 * @test
 * @summary Checks the identity guard and append behavior of Norm2AllModes NoopNormalizer2 via reflection
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED Norm2AllModesWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Norm2AllModesWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.text.normalizer.Norm2AllModes$NoopNormalizer2");
        Constructor<?> ctor = c.getConstructor();
        Object normalizer = ctor.newInstance();
        Method append = c.getMethod("append", StringBuilder.class, CharSequence.class);

        StringBuilder first = new StringBuilder("ab");
        Object result = append.invoke(normalizer, first, "cd");
        if (result != first) {
            throw new RuntimeException("append must return the first argument");
        }
        if (!"abcd".equals(first.toString())) {
            throw new RuntimeException("unexpected content: " + first);
        }

        StringBuilder same = new StringBuilder("x");
        try {
            append.invoke(normalizer, same, same);
            throw new RuntimeException("expected IllegalArgumentException for first == second");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause: " + e.getCause(), e);
            }
        }
    }
}
