/* @test    @summary Test enqueue method of WeakCleanable class    @run main WeakCleanableWitupTest */

import java.lang.ref.Cleaner;
import jdk.internal.ref.WeakCleanable;

public class WeakCleanableWitupTest {

    public static void main(String[] args) {
        testEnqueueThrows();
    }

    public static void testEnqueueThrows() {
        WeakCleanable<Object> wc = new WeakCleanable<Object>(new Object(), Cleaner.create());
        try {
            boolean result = wc.enqueue();
            if (result) {
                System.out.println("enqueue() returned true");
            } else {
                System.out.println("enqueue() returned false");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }
}
