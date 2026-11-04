/*
 * @test
 * @summary Verify that FinalReference.enqueue() throws InternalError via reflection
 * @modules java.base/java.lang.ref
 * @run main FinalReferenceWitupTest
 */

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FinalReferenceWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("java.lang.ref.FinalReference");

        Constructor<?> ctor = cls.getDeclaredConstructor(Object.class, ReferenceQueue.class);
        ctor.setAccessible(true);
        Object ref = ctor.newInstance(new Object(), new ReferenceQueue<Object>());

        Method enqueue = cls.getDeclaredMethod("enqueue");
        enqueue.setAccessible(true);

        boolean thrown = false;
        try {
            enqueue.invoke(ref);
        } catch (InvocationTargetException e) {
            thrown = true;
            Throwable cause = e.getCause();
            if (!(cause instanceof InternalError)) {
                throw new RuntimeException("Unexpected cause: " + cause, e);
            }
            if (!"should never reach here".equals(cause.getMessage())) {
                throw new RuntimeException("Unexpected message: " + cause.getMessage());
            }
        }

        if (!thrown) {
            throw new RuntimeException("enqueue() did not throw InternalError");
        }
    }
}
