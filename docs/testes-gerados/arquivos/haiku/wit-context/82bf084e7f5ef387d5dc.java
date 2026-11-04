/*
 * @test
 * @summary FixedList.clear() must throw UnsupportedOperationException with the documented message and keep the fixed size
 * @modules java.base/com.sun.java.util.jar.pack
 * @run main/othervm --add-opens java.base/com.sun.java.util.jar.pack=ALL-UNNAMED FixedListWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FixedListWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("com.sun.java.util.jar.pack.FixedList");
        Constructor<?> ctor = cls.getDeclaredConstructor(int.class);
        ctor.setAccessible(true);
        Object list = ctor.newInstance(2);

        Method clear = cls.getMethod("clear");
        clear.setAccessible(true);
        try {
            clear.invoke(list);
            throw new AssertionError("expected UnsupportedOperationException from clear()");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof UnsupportedOperationException)) {
                throw new AssertionError("wrong cause: " + cause);
            }
            if (!"operation not permitted".equals(cause.getMessage())) {
                throw new AssertionError("wrong message: " + cause.getMessage());
            }
        }

        Method size = cls.getMethod("size");
        size.setAccessible(true);
        int s = (Integer) size.invoke(list);
        if (s != 2) {
            throw new AssertionError("size changed after clear(): " + s);
        }

        Object emptyList = ctor.newInstance(0);
        try {
            clear.invoke(emptyList);
            throw new AssertionError("expected UnsupportedOperationException on empty list");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof UnsupportedOperationException)) {
                throw new AssertionError("wrong cause on empty list: " + e.getCause());
            }
        }
    }
}
