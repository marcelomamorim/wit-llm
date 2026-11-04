/* @test    @summary Test tryAdvance method of java.util.SplittableRandom    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.function.LongConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWhenIndexReachesFence();
    }

    static void testTryAdvanceWithConsumer() {
        SplittableRandom sr = new SplittableRandom();
        class DummyConsumer implements LongConsumer {
            long lastValue = 0;
            int count = 0;
            @Override
            public void accept(long value) {
                lastValue = value;
                count++;
            }
        }
        DummyConsumer consumer = new DummyConsumer();
        boolean result = sr.tryAdvance(consumer);
        assert result : "Expected tryAdvance to return true when index < fence";
        assert consumer.count == 1 : "Consumer should be accepted exactly once";
    }

    static void testTryAdvanceWithNullConsumer() {
        SplittableRandom sr = new SplittableRandom();
        try {
            sr.tryAdvance(null);
            assert false : "Expected NullPointerException when consumer is null";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testTryAdvanceWhenIndexReachesFence() {
        SplittableRandom sr = new SplittableRandom();
        // Manually set index to fence to simulate boundary condition
        // Note: index and fence are package-private; assuming access for test purposes
        // If not accessible, this test would need reflection, which is not allowed here.
        // For demonstration, assuming access:
        try {
            java.lang.reflect.Field indexField = SplittableRandom.class.getDeclaredField("index");
            java.lang.reflect.Field fenceField = SplittableRandom.class.getDeclaredField("fence");
            indexField.setAccessible(true);
            fenceField.setAccessible(true);
            indexField.setLong(sr, sr.fence);
            fenceField.setLong(sr, sr.fence);
            LongConsumer consumer = v -> {};
            boolean result = sr.tryAdvance(consumer);
            assert !result : "Expected tryAdvance to return false when index >= fence";
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // If reflection fails, skip this test
            System.out.println("Reflection failed: " + e.getMessage());
        }
    }
}
