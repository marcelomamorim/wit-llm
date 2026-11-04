/*
 * @test
 * @summary Test for Spliterators.tryAdvance(LongConsumer)
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWhenNoNext();
    }

    private static void testTryAdvanceWithNullAction() {
        boolean threwException = false;
        try {
            Spliterators.tryAdvance((LongConsumer) null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testTryAdvanceWithValidConsumer() {
        final long[] value = {0};
        LongConsumer consumer = new LongConsumer() {
            public void accept(long t) {
                value[0] = t;
            }
        };
        // Mock iterator with hasNext() and nextLong()
        java.util.Iterator<Long> mockIterator = new java.util.Iterator<Long>() {
            private boolean hasNext = true;
            public boolean hasNext() {
                return hasNext;
            }
            public Long next() {
                hasNext = false;
                return 42L;
            }
            public long nextLong() {
                return 42L;
            }
        };
        // Create a Spliterator with the mock iterator
        Spliterators.OfLong spliterator = new Spliterators.OfLong() {
            public boolean tryAdvance(LongConsumer action) {
                if (mockIterator.hasNext()) {
                    action.accept(mockIterator.nextLong());
                    return true;
                }
                return false;
            }
        };
        boolean result = spliterator.tryAdvance(consumer);
        if (!result) {
            throw new AssertionError("tryAdvance should return true when iterator has next")
        }
        if (value[0] != 42L) {
            throw new AssertionError("Consumer did not accept the correct value")
        }
    }

    private static void testTryAdvanceWhenNoNext() {
        // Mock iterator with no next element
        java.util.Iterator<Long> mockIterator = new java.util.Iterator<Long>() {
            public boolean hasNext() {
                return false;
            }
            public Long next() {
                throw new java.util.NoSuchElementException();
            }
            public long nextLong() {
                throw new java.util.NoSuchElementException();
            }
        };
        Spliterators.OfLong spliterator = new Spliterators.OfLong() {
            public boolean tryAdvance(LongConsumer action) {
                if (mockIterator.hasNext()) {
                    action.accept(mockIterator.nextLong());
                    return true;
                }
                return false;
            }
        };
        LongConsumer consumer = t -> {};
        boolean result = spliterator.tryAdvance(consumer);
        if (result) {
            throw new AssertionError("tryAdvance should return false when no next element")
        }
    }
}
