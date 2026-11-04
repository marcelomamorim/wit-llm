/*
 * @test
 * @summary CleanerImpl list head clear() must throw UnsupportedOperationException with message "clear"
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
        Field f = CleanerImpl.class.getDeclaredField("phantomCleanableList");
        f.setAccessible(true);
        Object head = f.get(impl);
        Method m = head.getClass().getMethod("clear");
        m.setAccessible(true);
        try {
            m.invoke(head);
            throw new AssertionError("clear() did not throw");
        } catch (InvocationTargetException e) {
            Throwable c = e.getCause();
            if (!(c instanceof UnsupportedOperationException)) {
                throw new AssertionError("wrong cause: " + c);
            }
            if (!"clear".equals(c.getMessage())) {
                throw new AssertionError("wrong message: " + c.getMessage());
            }
        }
    }
}
