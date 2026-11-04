/*
 * @test
 * @summary Verifies MergeCollation.getItemAt returns entries for a valid index and rejects out-of-range indexes
 * @run main/othervm --add-opens java.base/java.text=ALL-UNNAMED MergeCollationWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MergeCollationWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("java.text.MergeCollation");
        Constructor<?> ctor = cls.getConstructor(String.class);
        Object mc = ctor.newInstance("a<b");
        Method getItemAt = cls.getMethod("getItemAt", int.class);

        Object first = getItemAt.invoke(mc, 0);
        if (first == null) {
            throw new RuntimeException("getItemAt(0) returned null for pattern a<b");
        }

        Object again = getItemAt.invoke(mc, 0);
        if (first != again) {
            throw new RuntimeException("getItemAt(0) should return the same stored entry on repeated calls");
        }

        checkOutOfBounds(getItemAt, mc, -1);
        checkOutOfBounds(getItemAt, mc, 1000);
    }

    private static void checkOutOfBounds(Method getItemAt, Object mc, int index) throws Exception {
        try {
            getItemAt.invoke(mc, index);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index " + index);
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof IndexOutOfBoundsException)) {
                throw new RuntimeException("Unexpected cause for index " + index, e.getCause());
            }
        }
    }
}
