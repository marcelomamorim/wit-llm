/*
 * @test
 * @summary Test ReferencePipeline.limit(long) method behavior
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.Stream;
import java.util.stream.ReferencePipeline;
import java.util.stream.SliceOps;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReferencePipelineWitupTest {

    // Minimal concrete subclass of ReferencePipeline<String> to test limit
    static class TestPipeline extends ReferencePipeline<String, String> {

        TestPipeline(Supplier<? extends Spliterator<?>> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestPipeline(Spliterator<?> source, int sourceFlags, boolean parallel) {
            super(source, sourceFlags, parallel);
        }

        TestPipeline(AbstractPipeline<?, String, ?> upstream, int opFlags) {
            super(upstream, opFlags);
        }

        @Override
        public Iterator<String> iterator() {
            // Provide a simple iterator over fixed data
            return Stream.of("a", "b", "c", "d", "e").iterator();
        }

        @Override
        public Spliterator<String> spliterator() {
            return Spliterators.spliteratorUnknownSize(iterator(), 0);
        }
    }

    public static void main(String[] args) {
        testLimitPositive();
        testLimitZero();
        testLimitNegative();
    }

    private static void testLimitPositive() {
        TestPipeline pipeline = new TestPipeline(() -> Spliterators.spliterator(new String[] {"a", "b", "c", "d", "e"}, 0), 0, false);
        Stream<String> limited = pipeline.limit(3);
        Iterator<String> it = limited.iterator();
        int count = 0;
        while (it.hasNext()) {
            String s = it.next();
            if (count == 0) {
                if (!"a".equals(s)) throw new AssertionError("Expected 'a' at position 0");
            } else if (count == 1) {
                if (!"b".equals(s)) throw new AssertionError("Expected 'b' at position 1");
            } else if (count == 2) {
                if (!"c".equals(s)) throw new AssertionError("Expected 'c' at position 2");
            } else {
                throw new AssertionError("More elements than limit");
            }
            count++;
        }
        if (count != 3) throw new AssertionError("Expected 3 elements, got " + count);
    }

    private static void testLimitZero() {
        TestPipeline pipeline = new TestPipeline(() -> Spliterators.spliterator(new String[] {"a", "b"}, 0), 0, false);
        Stream<String> limited = pipeline.limit(0);
        Iterator<String> it = limited.iterator();
        if (it.hasNext()) throw new AssertionError("Expected no elements when limit is zero");
    }

    private static void testLimitNegative() {
        TestPipeline pipeline = new TestPipeline(() -> Spliterators.spliterator(new String[] {"a"}, 0), 0, false);
        try {
            pipeline.limit(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative limit");
        } catch (IllegalArgumentException e) {
            if (!"-1".equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch: " + e.getMessage());
            }
        }
    }
}
