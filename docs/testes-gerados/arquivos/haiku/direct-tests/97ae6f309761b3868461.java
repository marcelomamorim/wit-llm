/*
 * @test
 * @summary Verify that Collections.emptyList().forEach rejects a null action and never invokes a consumer on an empty list
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testConsumerNotInvokedOnEmptyList();
        testNonNullActionCompletesNormally();
    }

    static void testNullActionThrowsNPE() {
        List<String> list = Collections.emptyList();
        boolean thrown = false;
        try {
            list.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
    }

    static void testConsumerNotInvokedOnEmptyList() {
        List<String> list = Collections.emptyList();
        AtomicInteger calls = new AtomicInteger();
        list.forEach(s -> calls.incrementAndGet());
        if (calls.get() != 0) {
            throw new RuntimeException("Consumer should not be invoked, calls=" + calls.get());
        }
    }

    static void testNonNullActionCompletesNormally() {
        List<Integer> list = Collections.emptyList();
        AtomicInteger calls = new AtomicInteger();
        list.forEach(i -> calls.incrementAndGet());
        if (calls.get() != 0 || !list.isEmpty()) {
            throw new RuntimeException("Unexpected state after forEach on empty list");
        }
    }
}
