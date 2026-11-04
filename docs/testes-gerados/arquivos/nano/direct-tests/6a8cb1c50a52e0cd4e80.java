/*
 * @test
 * @summary Test tryAdvance method of ThreadLocalRandom for correct behavior
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWhenIndexLessThanFence();
        testTryAdvanceWhenIndexNotLessThanFence();
    }

    static void testTryAdvanceWithValidConsumer() {
        class DummyConsumer implements IntConsumer {
            int received = 0;
            @Override
            public void accept(int value) {
                received = value;
            }
        }
        DummyConsumer consumer = new DummyConsumer();
        ThreadLocalRandom.randomSeed = 0x1234; // forcibly set seed for deterministic behavior
        boolean result = ThreadLocalRandom.current().tryAdvance(consumer);
        assert result : "Expected tryAdvance to return true when index < fence";
        assert consumer.received != 0 : "Consumer should have received a value";
    }

    static void testTryAdvanceWithNullConsumer() {
        boolean threw = false;
        try {
            ThreadLocalRandom.current().tryAdvance(null);
        } catch (NullPointerException e) {
            threw = true;
        }
        assert threw : "Expected NullPointerException when consumer is null";
    }

    static void testTryAdvanceWhenIndexLessThanFence() {
        // Setup: forcibly set index less than fence
        ThreadLocalRandom.randomSeed = 0x5678; // reset seed for deterministic behavior
        // Save original index and fence
        long originalIndex = ThreadLocalRandom.index;
        long originalFence = ThreadLocalRandom.fence;
        try {
            ThreadLocalRandom.index = 0;
            ThreadLocalRandom.fence = 10;
            boolean result = ThreadLocalRandom.current().tryAdvance(v -> {});
            assert result : "Expected true when index < fence";
        } finally {
            ThreadLocalRandom.index = originalIndex;
            ThreadLocalRandom.fence = originalFence;
        }
    }

    static void testTryAdvanceWhenIndexNotLessThanFence() {
        // Setup: forcibly set index >= fence
        long originalIndex = ThreadLocalRandom.index;
        long originalFence = ThreadLocalRandom.fence;
        try {
            ThreadLocalRandom.index = 10;
            ThreadLocalRandom.fence = 10;
            boolean result = ThreadLocalRandom.current().tryAdvance(v -> {});
            assert !result : "Expected false when index >= fence";
        } finally {
            ThreadLocalRandom.index = originalIndex;
            ThreadLocalRandom.fence = originalFence;
        }
    }
}
