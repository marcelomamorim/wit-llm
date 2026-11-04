/*
 * @test
 * @summary Verifies that FixedList.add(int, E) always rejects the call with UnsupportedOperationException and leaves the fixed-size list unchanged
 * @modules java.base/com.sun.java.util.jar.pack
 * @run main/othervm --add-opens java.base/com.sun.java.util.jar.pack=ALL-UNNAMED FixedListWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class FixedListWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("com.sun.java.util.jar.pack.FixedList");
        Constructor<?> ctor = cls.getDeclaredConstructor(int.class);
        ctor.setAccessible(true);
        Object target = ctor.newInstance(2);
        Method add = cls.getDeclaredMethod("add", int.class, Object.class);
        add.setAccessible(true);

        checkThrows(add, target, 0, "a");
        checkThrows(add, target, 5, null);
        checkThrows(add, target, -1, "b");

        List<?> list = (List<?>) target;
        if (list.size() != 2) {
            throw new AssertionError("size changed: " + list.size());
        }
        if (list.get(0) != null || list.get(1) != null) {
            throw new AssertionError("contents changed");
        }
    }

    static void checkThrows(Method add, Object target, int index, Object element) throws Exception {
        try {
            add.invoke(target, index, element);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof UnsupportedOperationException)) {
                throw new AssertionError("unexpected cause: " + cause);
            }
            if (!"operation not permitted".equals(cause.getMessage())) {
                throw new AssertionError("unexpected message: " + cause.getMessage());
            }
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException for index " + index);
    }
}
