/*
 * @test
 * @summary Witup check: MergeCollation.getItemAt throws IndexOutOfBoundsException for indexes outside an empty pattern list
 * @run main/othervm --add-opens java.base/java.text=ALL-UNNAMED MergeCollationWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MergeCollationWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("java.text.MergeCollation");
        Constructor<?> ctor = c.getDeclaredConstructor(String.class);
        ctor.setAccessible(true);
        Object collation = ctor.newInstance("");
        Method getItemAt = c.getMethod("getItemAt", int.class);
        getItemAt.setAccessible(true);

        checkOutOfBounds(getItemAt, collation, 0);
        checkOutOfBounds(getItemAt, collation, 1);
        checkOutOfBounds(getItemAt, collation, -1);
        checkOutOfBounds(getItemAt, collation, Integer.MIN_VALUE);
    }

    private static void checkOutOfBounds(Method m, Object target, int index) {
        try {
            m.invoke(target, index);
            throw new RuntimeException("No exception for index " + index);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IndexOutOfBoundsException)) {
                throw new RuntimeException("Unexpected cause for index " + index, cause);
            }
        }
    }
}
