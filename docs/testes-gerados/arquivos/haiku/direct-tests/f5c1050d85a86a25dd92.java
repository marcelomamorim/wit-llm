/*
 * @test
 * @summary Verify Norm2AllModes.getNFCInstance returns a non-null, stable singleton instance
 * @modules java.base/sun.text.normalizer
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED Norm2AllModesWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Norm2AllModesWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.text.normalizer.Norm2AllModes");
        Method m = c.getDeclaredMethod("getNFCInstance");
        m.setAccessible(true);

        Object first;
        Object second;
        try {
            first = m.invoke(null);
            second = m.invoke(null);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("getNFCInstance threw: " + e.getCause(), e.getCause());
        }

        check(first != null, "getNFCInstance returned null");
        check(second != null, "second getNFCInstance call returned null");
        check(c.isInstance(first), "returned object is not a Norm2AllModes");
        check(first == second, "getNFCInstance should return the same singleton instance");
    }
}
