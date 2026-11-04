/*
 * @test
 * @summary Test for Spliterators.tryAdvance handling null Consumer
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.Consumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullConsumer();
    }

    public static void testTryAdvanceWithNullConsumer() {
        Spliterators.OfRef<Integer> spliterator = new Spliterators.EmptySpliterator.OfRef<>();
        Consumer<? super Integer> consumer = null;
        boolean threwException = false;
        try {
            spliterator.tryAdvance(consumer);
        } catch (NullPointerException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected NullPointerException when passing null Consumer");
        }
    }
}
