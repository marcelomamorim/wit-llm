/*
 * @test
 * @summary Verifies that FixedList.add(E) always throws UnsupportedOperationException with the fixed message and leaves the list size unchanged
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

        Method add = cls.getDeclaredMethod("add", Object.class);
        add.setAccessible(true);

        checkAddUnsupported(add, list, "x");
        checkAddUnsupported(add, list, null);

        Method size = cls.getDeclaredMethod("size");
        size.setAccessible(true);
        int s = (Integer) size.invoke(list);
        if (s != 2) {
            throw new RuntimeException("size changed after rejected add: " + s);
        }
    }

    private static void checkAddUnsupported(Method add, Object list, Object value)
            throws Exception {
        try {
            add.invoke(list, new Object[] { value });
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof UnsupportedOperationException)) {
                throw new RuntimeException("unexpected exception: " + cause, cause);
            }
            if (!"operation not permitted".equals(cause.getMessage())) {
                throw new RuntimeException("unexpected message: " + cause.getMessage());
            }
            return;
        }
        throw new RuntimeException("add(E) did not throw UnsupportedOperationException");
    }
}
