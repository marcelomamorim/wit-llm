/*
 * @test
 * @summary Test ReferencePipeline.mapToLong behavior with valid and null mapper
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.stream.ReferencePipeline;
import java.util.function.ToLongFunction;
import java.util.Objects;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testMapToLongWithValidMapper();
        testMapToLongWithNullMapper();
    }

    static void testMapToLongWithValidMapper() {
        ReferencePipeline<String, String> pipeline = new ReferencePipelineHead<>(new String[]{"a", "bb", "ccc"});
        ToLongFunction<String> mapper = new ToLongFunction<String>() {
            public long applyAsLong(String s) {
                return s.length();
            }
        };
        LongStream longStream = pipeline.mapToLong(mapper);
        long[] results = longStream.toArray();
        if (results.length != 3 || results[0] != 1 || results[1] != 2 || results[2] != 3) {
            throw new AssertionError("mapToLong did not produce expected lengths");
        }
    }

    static void testMapToLongWithNullMapper() {
        ReferencePipeline<String, String> pipeline = new ReferencePipelineHead<>(new String[]{"a"});
        try {
            pipeline.mapToLong(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Minimal concrete subclass of ReferencePipeline to allow instantiation for testing
    static class ReferencePipelineHead<P_IN> extends ReferencePipeline<P_IN, P_IN> {
        ReferencePipelineHead(P_IN[] array) {
            super(new ArraySpliterator<>(array), 0, false);
        }

        @Override
        public void forEach(java.util.function.Consumer<? super P_IN> action) {
            Objects.requireNonNull(action);
            Spliterator<P_IN> spliterator = sourceSpliterator();
            spliterator.forEachRemaining(action);
        }

        private Spliterator<P_IN> sourceSpliterator() {
            try {
                java.lang.reflect.Field f = ReferencePipeline.class.getDeclaredField("sourceSpliterator");
                f.setAccessible(true);
                return (Spliterator<P_IN>) f.get(this);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Minimal Spliterator implementation over array
    static class ArraySpliterator<T> implements java.util.Spliterator<T> {
        private final T[] array;
        private int index;

        ArraySpliterator(T[] array) {
            this.array = array;
            this.index = 0;
        }

        public boolean tryAdvance(java.util.function.Consumer<? super T> action) {
            if (index < array.length) {
                action.accept(array[index++]);
                return true;
            }
            return false;
        }

        public Spliterator<T> trySplit() {
            int lo = index, mid = (lo + array.length) >>> 1;
            if (lo >= mid) {
                return null;
            }
            index = mid;
            return new ArraySpliterator<>(java.util.Arrays.copyOfRange(array, lo, mid));
        }

        public long estimateSize() {
            return array.length - index;
        }

        public int characteristics() {
            return java.util.Spliterator.SIZED | java.util.Spliterator.SUBSIZED | java.util.Spliterator.ORDERED;
        }
    }
}
