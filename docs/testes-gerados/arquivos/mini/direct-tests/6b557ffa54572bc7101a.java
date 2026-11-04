/*
 * @test
 * @summary Test LongPipeline.filter(LongPredicate) behavior
 * @run main LongPipelineWitupTest
 */

import java.util.Objects;
import java.util.stream.LongStream;
import java.util.function.LongPredicate;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFilterRejectsAll();
        testFilterAcceptsAll();
        testFilterSelective();
        testFilterNullPredicate();
    }

    private static void testFilterRejectsAll() {
        LongStream base = LongStream.of(1L, 2L, 3L);
        LongStream filtered = base.filter(new LongPredicate() {
            public boolean test(long value) {
                return false;
            }
        });
        // Expect no elements after filter rejects all
        long count = filtered.count();
        if (count != 0) {
            throw new AssertionError("Expected 0 elements after filter rejects all, got " + count);
        }
    }

    private static void testFilterAcceptsAll() {
        LongStream base = LongStream.of(4L, 5L, 6L);
        LongStream filtered = base.filter(new LongPredicate() {
            public boolean test(long value) {
                return true;
            }
        });
        // Expect all elements to pass through
        long count = filtered.count();
        if (count != 3) {
            throw new AssertionError("Expected 3 elements after filter accepts all, got " + count);
        }
    }

    private static void testFilterSelective() {
        LongStream base = LongStream.of(10L, 15L, 20L, 25L);
        LongStream filtered = base.filter(new LongPredicate() {
            public boolean test(long value) {
                return value % 20 == 0;
            }
        });
        // Expect only elements divisible by 20
        long[] expected = {20L};
        long[] actual = filtered.toArray();
        if (actual.length != expected.length) {
            throw new AssertionError("Expected " + expected.length + " elements, got " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Expected element " + expected[i] + " but got " + actual[i]);
            }
        }
    }

    private static void testFilterNullPredicate() {
        LongStream base = LongStream.of(1L);
        try {
            base.filter(null);
            throw new AssertionError("Expected NullPointerException when predicate is null");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
