/*
 * @test
 * @summary FixedList.add(int, E) must reject insertion with UnsupportedOperationException and leave the list unchanged
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

        Method add = c.getMethod("add", int.class, Object.class);
        Method size = c.getMethod("size");
        Method get = c.getMethod("get", int.class);

        expectRejected(add, list, 0, "x");
        expectRejected(add, list, 3, "y");
        expectRejected(add, list, -1, "z");

        int s = (Integer) size.invoke(list);
        if (s != 3) {
            throw new AssertionError("size changed after rejected add: " + s);
        }
        for (int i = 0; i < 3; i++) {
            Object v = get.invoke(list, i);
            if (v != null) {
                throw new AssertionError("element " + i + " modified: " + v);
            }
        }
    }

    private static void expectRejected(Method add, Object list, int index, Object element)
            throws Exception {
        try {
            add.invoke(list, index, element);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof UnsupportedOperationException)) {
                throw new AssertionError("unexpected cause: " + cause, cause);
            }
            if (!"operation not permitted".equals(cause.getMessage())) {
                throw new AssertionError("unexpected message: " + cause.getMessage());
            }
            return;
        }
        throw new AssertionError("add(" + index + ", " + element + ") did not throw");
    }
}
