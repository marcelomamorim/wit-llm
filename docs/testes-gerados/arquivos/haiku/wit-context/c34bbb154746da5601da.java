/*
 * @test
 * @summary Verifies that Normalizer2.getNFKDInstance() returns a non-null, stable NFKD instance
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

        Object first = invoke(m);
        Object second = invoke(m);

        check(first != null, "getNFKDInstance() returned null");
        check(cls.isInstance(first), "result is not a Normalizer2 instance");
        check(second != null, "second call returned null");
        check(first == second, "getNFKDInstance() should return the same instance on repeated calls");
    }

    private static Object invoke(Method m) throws Exception {
        try {
            return m.invoke(null);
        } catch (InvocationTargetException e) {
            throw new AssertionError("unexpected exception from getNFKDInstance()", e.getCause());
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
