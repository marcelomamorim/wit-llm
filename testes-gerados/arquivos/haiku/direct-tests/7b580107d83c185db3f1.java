/*
 * @test
 * @summary Verifies that Norm2AllModes.getNFKCInstance() returns non-null Norm2AllModes instances without throwing
 * @modules java.base/sun.text.normalizer
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED Norm2AllModesWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Norm2AllModesWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.text.normalizer.Norm2AllModes");
        Method m = c.getDeclaredMethod("getNFKCInstance");
        m.setAccessible(true);

        Object first = invoke(m);
        Object second = invoke(m);

        if (first == null) {
            throw new RuntimeException("getNFKCInstance() returned null");
        }
        if (second == null) {
            throw new RuntimeException("getNFKCInstance() returned null on second call");
        }
        if (!c.isInstance(first) || !c.isInstance(second)) {
            throw new RuntimeException("getNFKCInstance() did not return a Norm2AllModes instance");
        }
    }

    private static Object invoke(Method m) throws Exception {
        try {
            return m.invoke(null);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("getNFKCInstance() threw an exception", e.getCause());
        }
    }
}
