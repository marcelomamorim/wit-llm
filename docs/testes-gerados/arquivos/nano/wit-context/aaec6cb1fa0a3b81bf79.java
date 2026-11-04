/*
 * @test
 * @summary Test for Spliterators.tryAdvance handling null Consumer
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.Iterator;
import java.util.Collection;
import java.util.Arrays;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
    }

    public static void testTryAdvanceWithNullConsumer() {
        Collection<Integer> coll = Arrays.asList(1, 2, 3);
        Spliterators.IteratorSpliterator<Integer> spliter = new Spliterators.IteratorSpliterator<>(coll.iterator(), coll.size());
        boolean exceptionThrown = false;
        try {
            spliter.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null Consumer");
        }
    }
}
