/*
 * @test
 * @summary Checks Spliterator.tryAdvance behavior of the iterate-based stream source, including null action rejection
 * @run main StreamWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class StreamWitupTest {

    public static void main(String[] args) {
        testSequentialAdvance();
        testNullActionThrowsNPE();
    }

    static void testSequentialAdvance() {
        UnaryOperator<Integer> f = x -> x * 2;
        Spliterator<Integer> sp = Stream.iterate(1, f).spliterator();
        List<Integer> out = new ArrayList<>();
        Consumer<Integer> c = out::add;
        for (int i = 0; i < 3; i++) {
            if (!sp.tryAdvance(c)) {
                throw new AssertionError("tryAdvance returned false at step " + i);
            }
        }
        List<Integer> expected = Arrays.asList(1, 2, 4);
        if (!out.equals(expected)) {
            throw new AssertionError("expected " + expected + " but was " + out);
        }
    }

    static void testNullActionThrowsNPE() {
        Spliterator<Integer> sp = Stream.iterate(1, x -> x + 1).spliterator();
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null action");
    }
}
