/*
 * @test
 * @summary Verify that FixedList.clear() always throws UnsupportedOperationException and leaves the fixed size unchanged
 * @add-opens java.base/com.sun.java.util.jar.pack
 * @run main FixedListWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class FixedListWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("com.sun.java.util.jar.pack.FixedList");
        Constructor<?> ctor = cls.getDeclaredConstructor(int.class);
        ctor.setAccessible(true);
        checkClear(ctor, 3);
        checkClear(ctor, 0);
    }

    private static void checkClear(Constructor<?> ctor, int capacity) throws Exception {
        Object instance;
        try {
            instance = ctor.newInstance(capacity);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
        List<?> list = (List<?>) instance;
        if (list.size() != capacity) {
            throw new RuntimeException("unexpected initial size: " + list.size());
        }
        boolean thrown = false;
        try {
            list.clear();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("clear() did not throw UnsupportedOperationException");
        }
        if (list.size() != capacity) {
            throw new RuntimeException("size changed after clear(): " + list.size());
        }
    }
}
