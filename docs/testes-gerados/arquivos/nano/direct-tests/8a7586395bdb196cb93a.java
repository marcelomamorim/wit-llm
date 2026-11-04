/* @test    @summary Test enqueue method of SoftCleanable class    @run main SoftCleanableWitupTest */

import java.lang.ref.Cleaner;
import jdk.internal.ref.SoftCleanable;

public class SoftCleanableWitupTest {

    public static void main(String[] args) {
        testEnqueueThrowsUnsupported();
    }

    private static void testEnqueueThrowsUnsupported() {
        try {
            SoftCleanable<Object> sc = new SoftCleanable<Object>(new Object(), Cleaner.create());
            sc.enqueue();
            System.out.println("Test failed: enqueue() did not throw.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: enqueue() threw UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.out.println("Test failed: enqueue() threw unexpected exception: " + t);
        }
    }
}
