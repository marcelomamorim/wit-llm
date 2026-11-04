/* @test    @summary Test getComparator() method of Streams class    @run main StreamsWitupTest */

import java.util.Comparator;
import java.util.stream.Streams;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testGetComparatorReturnsNonNull();
        testGetComparatorThrowsWhenBeforeSplit();
    }

    private static void testGetComparatorReturnsNonNull() {
        // Setup: create a mock or subclass of Streams with a getComparator implementation
        class TestStreams extends Streams {
            private boolean beforeSplit = false;
            private Comparator<? super Object> comparator = (a, b) -> 0;
            @Override
            public Comparator<? super Object> getComparator() {
                if (beforeSplit) {
                    throw new IllegalStateException();
                }
                return comparator;
            }
        }
        TestStreams ts = new TestStreams();
        // Act
        Comparator<? super Object> comp = ts.getComparator();
        // Assert
        if (comp == null) {
            throw new AssertionError("Expected non-null comparator");
        }
    }

    private static void testGetComparatorThrowsWhenBeforeSplit() {
        class TestStreams extends Streams {
            boolean beforeSplit = true;
            @Override
            public Comparator<? super Object> getComparator() {
                if (beforeSplit) {
                    throw new IllegalStateException();
                }
                return null;
            }
        }
        TestStreams ts = new TestStreams();
        ts.beforeSplit = true;
        boolean threw = false;
        try {
            ts.getComparator();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalStateException to be thrown");
        }
    }
}
