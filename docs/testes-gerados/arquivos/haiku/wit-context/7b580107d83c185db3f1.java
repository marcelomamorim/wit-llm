/*
 * @test
 * @summary Verifies that Norm2AllModes.getNFKCInstance() returns a non-null, stable singleton instance
 * @modules java.base/sun.text.normalizer
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED Norm2AllModesWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Norm2AllModesWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.text.normalizer.Norm2AllModes");
        Method m = c.getMethod("getNFKCInstance");
        m.setAccessible(true);

        Object first;
        Object second;
        try {
            first = m.invoke(null);
            second = m.invoke(null);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("getNFKCInstance threw unexpectedly", e.getCause());
        }

        if (first == null) {
            throw new RuntimeException("getNFKCInstance returned null");
        }
        if (!c.isInstance(first)) {
            throw new RuntimeException("Unexpected return type: " + first.getClass());
        }
        if (first != second) {
            throw new RuntimeException("getNFKCInstance returned different instances");
        }
    }
}
