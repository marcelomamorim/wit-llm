/*
 * @test
 * @summary Verify FixedList.retainAll always throws UnsupportedOperationException and leaves the list unchanged
 * @modules java.base/com.sun.java.util.jar.pack
 * @run main/othervm --add-opens java.base/com.sun.java.util.jar.pack=ALL-UNNAMED FixedListWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class FixedListWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("com.sun.java.util.jar.pack.FixedList");
        Constructor<?> ctor = cls.getDeclaredConstructor(int.class);
        ctor.setAccessible(true);
        Object list = ctor.newInstance(2);

        Method retainAll = cls.getMethod("retainAll", Collection.class);
        retainAll.setAccessible(true);
        Method size = cls.getMethod("size");
        Method isEmpty = cls.getMethod("isEmpty");

        if (((Integer) size.invoke(list)).intValue() != 2) {
            throw new AssertionError("initial size should be 2");
        }

        expectUOE(retainAll, list, new ArrayList<String>());
        expectUOE(retainAll, list, new ArrayList<Integer>());
        expectUOE(retainAll, list, (Object) null);

        if (((Integer) size.invoke(list)).intValue() != 2) {
            throw new AssertionError("size changed after retainAll");
        }
        if (((Boolean) isEmpty.invoke(list)).booleanValue()) {
            throw new AssertionError("list should not be empty after retainAll");
        }
    }

    private static void expectUOE(Method m, Object target, Object arg) throws Exception {
        try {
            m.invoke(target, arg);
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
        throw new AssertionError("retainAll did not throw UnsupportedOperationException");
    }
}
