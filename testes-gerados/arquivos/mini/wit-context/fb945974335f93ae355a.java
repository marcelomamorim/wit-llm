/*
 * @test
 * @summary Test ReferencePipeline.limit(long) for valid and invalid inputs
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.Stream;
import java.util.stream.ReferencePipeline;
import java.util.stream.SliceOps;
import java.util.function.Supplier;
import java.util.Spliterator;
import java.util.Spliterators;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testLimitNegative();
        testLimitZero();
        testLimitPositive();
    }

    private static void testLimitNegative() {
        try {
            // Create a dummy ReferencePipeline instance via anonymous subclass to call limit
            ReferencePipeline<Object, Object> rp = new ReferencePipeline<Object, Object>(new Supplier<Spliterator<?>>() {
                public Spliterator<?> get() {
                    return Spliterators.emptySpliterator();
                }
            }, 0, false) {
                @Override
                public Stream<Object> limit(long maxSize) {
                    return super.limit(maxSize);
                }
            };
            rp.limit(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for negative limit");
        } catch (IllegalArgumentException iae) {
            // Expected
        }
    }

    private static void testLimitZero() {
        ReferencePipeline<Object, Object> rp = new ReferencePipeline<Object, Object>(new Supplier<Spliterator<?>>() {
            public Spliterator<?> get() {
                return Spliterators.emptySpliterator();
            }
        }, 0, false) {
            @Override
            public Stream<Object> limit(long maxSize) {
                return super.limit(maxSize);
            }
        };
        Stream<Object> limited = rp.limit(0L);
        if (limited == null) {
            throw new RuntimeException("limit(0) returned null");
        }
    }

    private static void testLimitPositive() {
        ReferencePipeline<Object, Object> rp = new ReferencePipeline<Object, Object>(new Supplier<Spliterator<?>>() {
            public Spliterator<?> get() {
                return Spliterators.spliterator(new Object[] {"a", "b", "c"}, 0);
            }
        }, 0, false) {
            @Override
            public Stream<Object> limit(long maxSize) {
                return super.limit(maxSize);
            }
        };
        Stream<Object> limited = rp.limit(2L);
        if (limited == null) {
            throw new RuntimeException("limit(2) returned null");
        }
    }
}
