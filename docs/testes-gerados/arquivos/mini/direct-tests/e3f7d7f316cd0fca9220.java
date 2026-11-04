/*
 * @test
 * @summary Test Streams.getComparator() behavior
 * @run main StreamsWitupTest
 */

import java.util.Comparator;
import java.util.Spliterator;

public class StreamsWitupTest {

    static class DummySpliterator<T> implements Spliterator<T> {
        private final Comparator<? super T> comp;

        DummySpliterator(Comparator<? super T> comp) {
            this.comp = comp;
        }

        @Override
        public boolean tryAdvance(java.util.function.Consumer<? super T> action) {
            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return 0;
        }

        @Override
        public int characteristics() {
            return 0;
        }

        @Override
        public Comparator<? super T> getComparator() {
            return comp;
        }
    }

    static class TestStreams<T> extends java.util.stream.Streams {
        boolean beforeSplit;
        DummySpliterator<T> bSpliterator;

        TestStreams(boolean beforeSplit, Comparator<? super T> comp) {
            this.beforeSplit = beforeSplit;
            this.bSpliterator = new DummySpliterator<>(comp);
        }

        public Comparator<? super T> getComparator() {
            if (beforeSplit)
                throw new IllegalStateException();
            return bSpliterator.getComparator();
        }
    }

    public static void main(String[] args) {
        // Test 1: beforeSplit == true should throw IllegalStateException
        try {
            TestStreams<String> s1 = new TestStreams<>(true, null);
            s1.getComparator();
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }

        // Test 2: beforeSplit == false returns the comparator from bSpliterator
        Comparator<String> comp = Comparator.naturalOrder();
        TestStreams<String> s2 = new TestStreams<>(false, comp);
        Comparator<? super String> result = s2.getComparator();
        if (result != comp) {
            throw new RuntimeException("Returned comparator does not match expected");
        }

        // Test 3: beforeSplit == false and bSpliterator.getComparator() returns null
        TestStreams<String> s3 = new TestStreams<>(false, null);
        Comparator<? super String> resultNull = s3.getComparator();
        if (resultNull != null) {
            throw new RuntimeException("Expected null comparator");
        }
    }
}
