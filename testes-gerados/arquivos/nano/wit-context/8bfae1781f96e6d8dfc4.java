/* @test    @summary Test for PhantomCleanable.enqueue() method to observe behavior and exception throwing    @run main PhantomCleanableWitupTest */

import java.lang.ref.Cleaner;
import jdk.internal.ref.PhantomCleanable;

public class PhantomCleanableWitupTest {

    public static void main(String[] args) {
        testEnqueueThrowsException();
    }

    private static void testEnqueueThrowsException() {
        try {
            // Create an anonymous subclass instance of PhantomCleanable to invoke enqueue
            PhantomCleanable<Object> cleanable = new PhantomCleanable<Object>(new Object(), Cleaner.create());
            boolean result = cleanable.enqueue();
            System.out.println("enqueue() returned: " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected exception: " + e.getClass().getName());
        } catch (Throwable t) {
            System.out.println("Caught unexpected exception: " + t.getClass().getName());
        }
    }
}
