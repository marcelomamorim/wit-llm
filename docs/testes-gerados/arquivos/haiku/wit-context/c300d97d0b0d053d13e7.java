/*
 * @test
 * @summary Verifies FixedList.get(int) returns null for initialized slots and throws IndexOutOfBoundsException for invalid indexes
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
        Method get = cls.getDeclaredMethod("get", int.class);
        get.setAccessible(true);
        Method size = cls.getDeclaredMethod("size");
        size.setAccessible(true);

        Object list = ctor.newInstance(3);
        check(((Integer) size.invoke(list)) == 3, "size should be 3");
        check(get.invoke(list, 0) == null, "slot 0 should be null");
        check(get.invoke(list, 2) == null, "slot 2 should be null");

        expectIndexOutOfBounds(get, list, -1);
        expectIndexOutOfBounds(get, list, 3);

        Object empty = ctor.newInstance(0);
        expectIndexOutOfBounds(get, empty, 0);
    }

    private static void expectIndexOutOfBounds(Method get, Object list, int index) throws Exception {
        try {
            get.invoke(list, index);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IndexOutOfBoundsException) {
                return;
            }
            throw new RuntimeException("unexpected cause for index " + index + ": " + cause, cause);
        }
        throw new RuntimeException("no exception thrown for index " + index);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
