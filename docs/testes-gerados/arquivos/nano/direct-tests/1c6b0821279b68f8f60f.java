/* @test    @summary Test for WeakCleanable.isEnqueued() method behavior    @run main WeakCleanableWitupTest */

import java.lang.ref.Cleaner;
import jdk.internal.ref.WeakCleanable;

public class WeakCleanableWitupTest {

    public static void main(String[] args) {
        testIsEnqueuedThrowsException();
    }

    private static void testIsEnqueuedThrowsException() {
        WeakCleanable<Object> wc = new WeakCleanable<Object>(new Object(), Cleaner.create());
        try {
            boolean result = wc.isEnqueued();
            // Since the method throws UnsupportedOperationException, reaching this line indicates failure
            System.err.println("Expected UnsupportedOperationException, but got result: " + result);
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception caught, test passes
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
