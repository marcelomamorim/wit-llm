/*
 * @test
 * @summary Verifies StreamSpliterators tryAdvance through Stream.generate(...).spliterator(): one supplier call per advance, true result, and NPE for null action without invoking the supplier.
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class StreamSpliteratorsWitupTest {

    public static void main(String[] args) {
        testAdvanceInvokesSupplierOncePerCall();
        testNullActionRejectedWithoutSupplierCall();
    }

    static void testAdvanceInvokesSupplierOncePerCall() {
        AtomicInteger calls = new AtomicInteger();
        Spliterator<String> sp = Stream.generate(() -> "v" + calls.incrementAndGet()).spliterator();

        String[] box = new String[1];
        boolean r1 = sp.tryAdvance(v -> box[0] = v);
        if (!r1) {
            throw new RuntimeException("tryAdvance should return true on first call");
        }
        if (calls.get() != 1) {
            throw new RuntimeException("expected 1 supplier call, got " + calls.get());
        }
        if (!"v1".equals(box[0])) {
            throw new RuntimeException("expected v1 but got " + box[0]);
        }

        boolean r2 = sp.tryAdvance(v -> box[0] = v);
        if (!r2) {
            throw new RuntimeException("tryAdvance should return true on second call");
        }
        if (calls.get() != 2) {
            throw new RuntimeException("expected 2 supplier calls, got " + calls.get());
        }
        if (!"v2".equals(box[0])) {
            throw new RuntimeException("expected v2 but got " + box[0]);
        }
    }

    static void testNullActionRejectedWithoutSupplierCall() {
        AtomicInteger calls = new AtomicInteger();
        Spliterator<String> sp = Stream.generate(() -> {
            calls.incrementAndGet();
            return "x";
        }).spliterator();

        try {
            sp.tryAdvance(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
        if (calls.get() != 0) {
            throw new RuntimeException("supplier must not be invoked when action is null, calls=" + calls.get());
        }
    }
}
