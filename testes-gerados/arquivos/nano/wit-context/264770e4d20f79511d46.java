/* @test    @summary Test for WeakCleanable.enqueue() method to observe exception behavior    @run main WeakCleanableWitupTest */

import java.lang.ref.Cleaner;
import jdk.internal.ref.WeakCleanable;

public class WeakCleanableWitupTest {

    public static void main(String[] args) {
        testEnqueueThrowsException();
    }

    private static void testEnqueueThrowsException() {
        WeakCleanable<Object> weakCleanable = new WeakCleanable<Object>(new Object(), Cleaner.create());
        try {
            boolean result = weakCleanable.enqueue();
            System.out.println("enqueue() returned: " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected exception: " + e.getClass().getName());
        } catch (Throwable t) {
            System.out.println("Caught unexpected exception: " + t.getClass().getName());
        }
    }
}
