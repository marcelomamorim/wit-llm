/*
 * @test
 * @summary Verify that empty spliterators reject null consumers and never invoke a consumer
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {
    public static void main(String[] args) {
        testRef();
        testInt();
        testLong();
        testDouble();
    }

    static void testRef() {
        Spliterator<String> s = Spliterators.emptySpliterator();
        expectNpe(() -> s.tryAdvance((Consumer<String>) null));
        boolean advanced = s.tryAdvance(x -> { throw new AssertionError("consumer must not be called"); });
        if (advanced) {
            throw new AssertionError("tryAdvance on empty spliterator returned true");
        }
    }

    static void testInt() {
        Spliterator.OfInt s = Spliterators.emptyIntSpliterator();
        expectNpe(() -> s.tryAdvance((IntConsumer) null));
        boolean advanced = s.tryAdvance((IntConsumer) x -> { throw new AssertionError("consumer must not be called"); });
        if (advanced) {
            throw new AssertionError("tryAdvance on empty int spliterator returned true");
        }
    }

    static void testLong() {
        Spliterator.OfLong s = Spliterators.emptyLongSpliterator();
        expectNpe(() -> s.tryAdvance((LongConsumer) null));
        boolean advanced = s.tryAdvance((LongConsumer) x -> { throw new AssertionError("consumer must not be called"); });
        if (advanced) {
            throw new AssertionError("tryAdvance on empty long spliterator returned true");
        }
    }

    static void testDouble() {
        Spliterator.OfDouble s = Spliterators.emptyDoubleSpliterator();
        expectNpe(() -> s.tryAdvance((DoubleConsumer) null));
        boolean advanced = s.tryAdvance((DoubleConsumer) x -> { throw new AssertionError("consumer must not be called"); });
        if (advanced) {
            throw new AssertionError("tryAdvance on empty double spliterator returned true");
        }
    }

    static void expectNpe(Runnable r) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("NullPointerException expected");
    }
}
