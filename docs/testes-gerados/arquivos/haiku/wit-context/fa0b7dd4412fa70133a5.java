/*
 * @test
 * @summary Verifies that Normalizer2.getNFDInstance() returns a non-null, stable singleton instance
 * @modules java.base/sun.text.normalizer
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED Normalizer2WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Normalizer2WitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> normalizer2 = Class.forName("sun.text.normalizer.Normalizer2");
        Method getNFDInstance = normalizer2.getMethod("getNFDInstance");
        getNFDInstance.setAccessible(true);

        Object first = invokeStatic(getNFDInstance);
        Object second = invokeStatic(getNFDInstance);

        if (first == null) {
            throw new AssertionError("getNFDInstance() returned null");
        }
        if (!normalizer2.isInstance(first)) {
            throw new AssertionError("getNFDInstance() returned an unexpected type: " + first.getClass());
        }
        if (first != second) {
            throw new AssertionError("getNFDInstance() returned different instances on repeated calls");
        }
    }

    private static Object invokeStatic(Method m) throws Exception {
        try {
            return m.invoke(null);
        } catch (InvocationTargetException e) {
            throw new AssertionError("getNFDInstance() threw an exception", e.getCause());
        }
    }
}
