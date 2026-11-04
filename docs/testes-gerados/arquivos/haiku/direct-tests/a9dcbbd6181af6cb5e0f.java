/*
 * @test
 * @summary Checks SplittableRandom.forEachRemaining(LongConsumer) via the spliterator of longs(): null consumer, element count, determinism and exhaustion
 * @run main SplittableRandomWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.Spliterator;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testNullConsumerThrowsNPE();
        testZeroSizeProducesNoElements();
        testCountMatchesStreamSize();
        testSameSeedGivesSameSequence();
        testSecondCallAfterExhaustionProducesNothing();
    }

    static Spliterator.OfLong spliteratorOf(long seed, long size) {
        LongStream s = new SplittableRandom(seed).longs(size);
        return s.spliterator();
    }

    static List<Long> drain(Spliterator.OfLong sp) {
        List<Long> out = new ArrayList<>();
        LongConsumer c = out::add;
        sp.forEachRemaining(c);
        return out;
    }

    static void testNullConsumerThrowsNPE() {
        Spliterator.OfLong sp = spliteratorOf(42L, 5L);
        boolean thrown = false;
        try {
            sp.forEachRemaining((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null consumer");
        }
    }

    static void testZeroSizeProducesNoElements() {
        List<Long> values = drain(spliteratorOf(7L, 0L));
        if (!values.isEmpty()) {
            throw new RuntimeException("Expected no elements, got " + values.size());
        }
    }

    static void testCountMatchesStreamSize() {
        List<Long> values = drain(spliteratorOf(123L, 37L));
        if (values.size() != 37) {
            throw new RuntimeException("Expected 37 elements, got " + values.size());
        }
    }

    static void testSameSeedGivesSameSequence() {
        List<Long> a = drain(spliteratorOf(2024L, 100L));
        List<Long> b = drain(spliteratorOf(2024L, 100L));
        if (!a.equals(b)) {
            throw new RuntimeException("Same seed produced different sequences");
        }
    }

    static void testSecondCallAfterExhaustionProducesNothing() {
        Spliterator.OfLong sp = spliteratorOf(99L, 10L);
        List<Long> first = drain(sp);
        if (first.size() != 10) {
            throw new RuntimeException("Expected 10 elements on first pass, got " + first.size());
        }
        List<Long> second = drain(sp);
        if (!second.isEmpty()) {
            throw new RuntimeException("Expected no elements on second pass, got " + second.size());
        }
    }
}
