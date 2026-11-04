/*
 * @test
 * @summary Verify FixedList.addAll(int, Collection) always throws UnsupportedOperationException and leaves the fixed-size list unchanged
 * @modules java.base/com.sun.java.util.jar.pack
 * @run main/othervm --add-opens java.base/com.sun.java.util.jar.pack=ALL-UNNAMED FixedListWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FixedListWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("com.sun.java.util.jar.pack.FixedList");
        Constructor<?> ctor = cls.getDeclaredConstructor(int.class);
        ctor.setAccessible(true);
        Object list = ctor.newInstance(3);

        Method addAll = cls.getMethod("addAll", int.class, Collection.class);
        addAll.setAccessible(true);
        Method size = cls.getMethod("size");
        size.setAccessible(true);
        Method get = cls.getMethod("get", int.class);
        get.setAccessible(true);

        // Valid index, empty collection: must still throw UOE
        expectUOE(addAll, list, 0, Collections.emptyList());

        // Valid index, non-empty collection
        List<String> items = new ArrayList<>();
        items.add("a");
        expectUOE(addAll, list, 1, items);

        // Negative index: method does not validate index, still UOE
        expectUOE(addAll, list, -1, items);

        // Index beyond size: still UOE, not IndexOutOfBoundsException
        expectUOE(addAll, list, 10, items);

        // Null collection: method does not dereference it, still UOE
        expectUOE(addAll, list, 0, null);

        // The list must remain unchanged
        int actualSize = (Integer) size.invoke(list);
        if (actualSize != 3) {
            throw new RuntimeException("size changed: " + actualSize);
        }
        for (int i = 0; i < 3; i++) {
            if (get.invoke(list, i) != null) {
                throw new RuntimeException("element " + i + " was modified");
            }
        }
    }

    private static void expectUOE(Method m, Object target, Object... args) {
        try {
            m.invoke(target, args);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof UnsupportedOperationException)) {
                throw new RuntimeException("unexpected cause: " + e.getCause(), e);
            }
            return;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("expected UnsupportedOperationException");
    }
}
