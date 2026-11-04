/*
 * @test
 * @summary Verifies that Normalizer2.getNFKDInstance() returns a non-null, stable Normalizer2 instance
 * @modules java.base/sun.text.normalizer
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED Normalizer2WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Normalizer2WitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("sun.text.normalizer.Normalizer2");
        Method m = cls.getMethod("getNFKDInstance");
        m.setAccessible(true);

        check(m.getReturnType() == cls, "return type must be Normalizer2");

        Object first = invoke(m);
        Object second = invoke(m);

        check(first != null, "getNFKDInstance() returned null");
        check(cls.isInstance(first), "result is not a Normalizer2");
        check(first == second, "repeated calls should return the same singleton-backed instance");
    }

    static Object invoke(Method m) throws Exception {
        try {
            return m.invoke(null);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("getNFKDInstance() threw unexpectedly", e.getCause());
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
