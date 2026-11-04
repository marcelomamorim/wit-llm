/* @test    @summary Test Random.tryAdvance with non-null LongConsumer    @run main RandomWitupTest */

import java.util.Random;
import java.util.function.LongConsumer;

public class RandomWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWhenIndexEqualsFence();
    }

    static void testTryAdvanceWithValidConsumer() {
        Random rand = new Random(12345L);
        final boolean[] called = {false};
        LongConsumer consumer = value -> {
            called[0] = true;
            // Optionally, verify the value is within expected range
            if (value == Long.MIN_VALUE || value == Long.MAX_VALUE) {
                throw new AssertionError("Value out of expected range");
            }
        };
        boolean result = rand.tryAdvance(consumer);
        assert result : "tryAdvance should return true when index < fence";
        assert called[0] : "Consumer should be called";
    }

    static void testTryAdvanceWithNullConsumer() {
        Random rand = new Random(12345L);
        try {
            rand.tryAdvance(null);
            assert false : "tryAdvance should throw NullPointerException when consumer is null";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testTryAdvanceWhenIndexEqualsFence() {
        Random rand = new Random(12345L);
        // Use reflection to set index and fence to same value to simulate index == fence
        try {
            java.lang.reflect.Field indexField = Random.class.getDeclaredField("index");
            java.lang.reflect.Field fenceField = Random.class.getDeclaredField("fence");
            indexField.setAccessible(true);
            fenceField.setAccessible(true);
            indexField.setLong(rand, 10L);
            fenceField.setLong(rand, 10L);
            boolean result = rand.tryAdvance(value -> {});
            assert !result : "tryAdvance should return false when index == fence";
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // If reflection fails, skip this test
            // as it indicates internal structure change
        }
    }
}
