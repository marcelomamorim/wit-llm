/*
 * @test
 * @summary Verifies append semantics of the NoopNormalizer2 nested class of Norm2AllModes via reflection
 * @modules java.base/sun.text.normalizer
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED Norm2AllModesWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Norm2AllModesWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.text.normalizer.Norm2AllModes$NoopNormalizer2");
        Object inst = c.getDeclaredConstructor().newInstance();
        Method m = c.getMethod("append", StringBuilder.class, CharSequence.class);

        StringBuilder first = new StringBuilder("ab");
        Object r = m.invoke(inst, first, new StringBuilder("cd"));
        if (r != first) {
            throw new RuntimeException("append did not return the same first instance");
        }
        if (!first.toString().equals("abcd")) {
            throw new RuntimeException("unexpected result: " + first);
        }

        StringBuilder empty = new StringBuilder("q");
        Object r2 = m.invoke(inst, empty, "");
        if (r2 != empty || !empty.toString().equals("q")) {
            throw new RuntimeException("appending empty sequence changed the builder: " + empty);
        }

        StringBuilder same = new StringBuilder("aa");
        try {
            m.invoke(inst, same, same);
            throw new RuntimeException("expected IllegalArgumentException when first == second");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                throw new RuntimeException("unexpected cause: " + e.getCause());
            }
        }
        if (!same.toString().equals("aa")) {
            throw new RuntimeException("builder modified despite exception: " + same);
        }
    }
}
