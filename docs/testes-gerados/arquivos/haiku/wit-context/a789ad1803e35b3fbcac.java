/*
 * @test
 * @summary Verifies that FixedList.add(E) always throws UnsupportedOperationException and leaves the fixed size unchanged
 * @modules java.base/com.sun.java.util.jar.pack
 * @run main/othervm --add-opens java.base/com.sun.java.util.jar.pack=ALL-UNNAMED FixedListWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FixedListWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("com.sun.java.util.jar.pack.FixedList");
        Constructor<?> ctor = c.getDeclaredConstructor(int.class);
        ctor.setAccessible(true);
        Object list = ctor.newInstance(3);

        Method add = c.getDeclaredMethod("add", Object.class);
        add.setAccessible(true);

        checkAddThrowsUOE(add, list, "x");
        checkAddThrowsUOE(add, list, null);

        Method size = c.getDeclaredMethod("size");
        size.setAccessible(true);
        int s = (Integer) size.invoke(list, new Object[0]);
        if (s != 3) {
            throw new AssertionError("size changed after rejected add: " + s);
        }
    }

    private static void checkAddThrowsUOE(Method add, Object target, Object arg) throws Exception {
        try {
            add.invoke(target, new Object[]{arg});
            throw new AssertionError("add did not throw UnsupportedOperationException");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof UnsupportedOperationException)) {
                throw new AssertionError("unexpected cause: " + e.getCause(), e);
            }
        }
    }
}
