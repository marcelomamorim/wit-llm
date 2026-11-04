/* @test    @summary Test enqueue method of PhantomCleanable class    @run main PhantomCleanableWitupTest */

import java.lang.ref.Cleaner;
import jdk.internal.ref.PhantomCleanable;

public class PhantomCleanableWitupTest {

    public static void main(String[] args) {
        testEnqueueThrowsUnsupported();
    }

    private static void testEnqueueThrowsUnsupported() {
        Object dummyReferent = new Object();
        Cleaner dummyCleaner = Cleaner.create();
        PhantomCleanable<Object> cleanable = new PhantomCleanable<Object>(dummyReferent, dummyCleaner);
        try {
            boolean result = cleanable.enqueue();
            // Since enqueue() throws UnsupportedOperationException, this line should not be reached.
            System.out.println("enqueue() returned: " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}
