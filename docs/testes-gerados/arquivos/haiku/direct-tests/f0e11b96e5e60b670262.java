/*
 * @test
 * @summary Verifies that forEachRemaining on the empty spliterators from java.util.Spliterators rejects a null consumer with NullPointerException and accepts a non-null consumer without invoking it
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        Spliterator<Object> ref = Spliterators.emptySpliterator();
        Spliterator.OfInt intSp = Spliterators.emptyIntSpliterator();
        Spliterator.OfLong longSp = Spliterators.emptyLongSpliterator();
        Spliterator.OfDouble doubleSp = Spliterators.emptyDoubleSpliterator();

        expectNpe(() -> ref.forEachRemaining(null), "ref");
        expectNpe(() -> intSp.forEachRemaining((IntConsumer) null), "int");
        expectNpe(() -> longSp.forEachRemaining((LongConsumer) null), "long");
        expectNpe(() -> doubleSp.forEachRemaining((DoubleConsumer) null), "double");

        int[] calls = {0};
        ref.forEachRemaining(o -> calls[0]++);
        intSp.forEachRemaining((IntConsumer) i -> calls[0]++);
        longSp.forEachRemaining((LongConsumer) l -> calls[0]++);
        doubleSp.forEachRemaining((DoubleConsumer) d -> calls[0]++);
        if (calls[0] != 0) {
            throw new RuntimeException("consumer invoked on empty spliterator: " + calls[0]);
        }
    }

    private static void expectNpe(Runnable r, String name) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(name + ": expected NullPointerException");
    }
}
