/*
 * @test
 * @summary Deterministic tests for java.lang.ref.ReferenceQueue remove() method
 * @run main ReferenceQueueWitupTest
 */

import java.lang.ref.ReferenceQueue;
import java.lang.ref.Reference;

public class ReferenceQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testRemoveReturnsNullWhenEmpty();
    }

    private static void testRemoveReturnsNullWhenEmpty() throws InterruptedException {
        ReferenceQueue<Object> rq = new ReferenceQueue<>();
        Reference<?> ref = rq.remove();
        if (ref != null) {
            throw new AssertionError("Expected null when removing from empty ReferenceQueue, but got: " + ref);
        }
    }

}
