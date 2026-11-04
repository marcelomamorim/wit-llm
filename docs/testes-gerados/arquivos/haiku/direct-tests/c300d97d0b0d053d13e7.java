/*
 * @test
 * @summary Verify FixedList.get returns null-initialized slots, reflects set updates, and throws IndexOutOfBoundsException out of range
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
        Object list = ctor.newInstance(3);

        Method get = cls.getMethod("get", int.class);
        get.setAccessible(true);
        Method set = cls.getMethod("set", int.class, Object.class);
        set.setAccessible(true);

        // Slots are initialized to null by the constructor
        check(get.invoke(list, 0) == null, "get(0) should be null initially");
        check(get.invoke(list, 2) == null, "get(2) should be null initially");

        // set returns previous value and get observes the update
        Object previous = set.invoke(list, 1, "x");
        check(previous == null, "set should return previous null value");
        check("x".equals(get.invoke(list, 1)), "get(1) should return updated value");

        // Out-of-range indexes must fail
        expectIndexOutOfBounds(get, list, 3);
        expectIndexOutOfBounds(get, list, -1);
    }

    private static void expectIndexOutOfBounds(Method get, Object list, int index) throws Exception {
        try {
            get.invoke(list, index);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IndexOutOfBoundsException)) {
                throw new RuntimeException("Unexpected cause for index " + index + ": " + e.getCause(), e.getCause());
            }
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for index " + index);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
