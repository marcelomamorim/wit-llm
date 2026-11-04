/*
 * @test
 * @summary Verify that clear() on the internal cleanable lists of CleanerImpl throws UnsupportedOperationException with message "clear"
 * @modules java.base/jdk.internal.ref
 * @run main/othervm --add-opens java.base/jdk.internal.ref=ALL-UNNAMED CleanerImplWitupTest
 */

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {

    public static void main(String[] args) throws Exception {
        CleanerImpl impl = new CleanerImpl();
        String[] names = {"phantomCleanableList", "weakCleanableList", "softCleanableList"};
        for (String name : names) {
            Field f = CleanerImpl.class.getDeclaredField(name);
            f.setAccessible(true);
            Object list = f.get(impl);
            if (list == null) {
                throw new RuntimeException(name + " is null");
            }
            Method m = findClear(list.getClass());
            if (m == null) {
                throw new RuntimeException("clear() not found for " + name);
            }
            m.setAccessible(true);
            try {
                m.invoke(list);
                throw new RuntimeException("No exception from clear() on " + name);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (!(cause instanceof UnsupportedOperationException)) {
                    throw new RuntimeException("Wrong cause for " + name, cause);
                }
                if (!"clear".equals(cause.getMessage())) {
                    throw new RuntimeException("Wrong message for " + name + ": " + cause.getMessage());
                }
            }
        }
    }

    private static Method findClear(Class<?> c) {
        Class<?> current = c;
        while (current != null) {
            try {
                return current.getDeclaredMethod("clear");
            } catch (NoSuchMethodException e) {
                current = current.getSuperclass();
            }
        }
        return null;
    }
}
