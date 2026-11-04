/*
 * @test
 * @summary Verifies FixedList.retainAll always rejects calls with UnsupportedOperationException and leaves the fixed-size list unchanged
 * @modules java.base/com.sun.java.util.jar.pack
 * @run main FixedListWitupTest
 */
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FixedListWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("com.sun.java.util.jar.pack.FixedList");
        Constructor<?> ctor = cls.getDeclaredConstructor(int.class);
        ctor.setAccessible(true);
        List<?> list = (List<?>) ctor.newInstance(3);

        expectUnsupported(list, new ArrayList<String>(), "empty collection");
        expectUnsupported(list, Collections.singletonList("x"), "non-empty collection");
        expectUnsupported(list, null, "null collection");

        if (list.size() != 3) {
            throw new RuntimeException("size changed: " + list.size());
        }
        for (int i = 0; i < 3; i++) {
            if (list.get(i) != null) {
                throw new RuntimeException("element " + i + " changed");
            }
        }
    }

    static void expectUnsupported(List<?> list, Collection<?> c, String label) {
        try {
            list.retainAll(c);
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("no UnsupportedOperationException for " + label);
    }
}
