/*
 * @test
 * @summary Verifies that FixedList.addAll(int, Collection) always rejects the call with UnsupportedOperationException and leaves the fixed size unchanged
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

    private static final String EXPECTED_MESSAGE = "operation not permitted";

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("com.sun.java.util.jar.pack.FixedList");
        Constructor<?> ctor = cls.getDeclaredConstructor(int.class);
        ctor.setAccessible(true);
        Object list = ctor.newInstance(3);

        Method addAll = cls.getMethod("addAll", int.class, Collection.class);
        addAll.setAccessible(true);

        expectUnsupported(addAll, list, 0, new ArrayList<String>());
        expectUnsupported(addAll, list, 1, Collections.emptyList());
        expectUnsupported(addAll, list, -1, Collections.singletonList("x"));
        expectUnsupported(addAll, list, 3, null);

        List<?> view = (List<?>) list;
        if (view.size() != 3) {
            throw new RuntimeException("size changed after rejected addAll: " + view.size());
        }
        for (Object o : view) {
            if (o != null) {
                throw new RuntimeException("element modified after rejected addAll");
            }
        }
    }

    private static void expectUnsupported(Method m, Object target, int index, Collection<?> c)
            throws Exception {
        try {
            m.invoke(target, index, c);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof UnsupportedOperationException)) {
                throw new RuntimeException("unexpected cause: " + cause, cause);
            }
            if (!EXPECTED_MESSAGE.equals(cause.getMessage())) {
                throw new RuntimeException("unexpected message: " + cause.getMessage());
            }
            return;
        }
        throw new RuntimeException("addAll(" + index + ", ...) did not throw UnsupportedOperationException");
    }
}
