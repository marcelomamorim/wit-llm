/*
 * @test
 * @summary Verifies that FinalReference.enqueue() always throws InternalError with the expected message and that the referent remains unchanged
 * @run main/othervm --add-opens java.base/java.lang.ref=ALL-UNNAMED FinalReferenceWitupTest
 */

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FinalReferenceWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("java.lang.ref.FinalReference");
        Constructor<?> ctor = cls.getDeclaredConstructor(Object.class, ReferenceQueue.class);
        ctor.setAccessible(true);
        Object referent = new Object();
        Object ref = ctor.newInstance(referent, new ReferenceQueue<Object>());

        Method enqueue = cls.getDeclaredMethod("enqueue");
        enqueue.setAccessible(true);
        try {
            enqueue.invoke(ref);
            throw new RuntimeException("enqueue() did not throw");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof InternalError)) {
                throw new RuntimeException("unexpected cause: " + cause, cause);
            }
            if (!"should never reach here".equals(cause.getMessage())) {
                throw new RuntimeException("unexpected message: " + cause.getMessage());
            }
        }

        if (((Reference<?>) ref).get() != referent) {
            throw new RuntimeException("referent changed after enqueue()");
        }
    }
}
