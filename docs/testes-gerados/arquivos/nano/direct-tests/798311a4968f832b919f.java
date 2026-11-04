/*
 * @test
 * @summary Test tryAdvance(LongConsumer) method of java.util.Spliterators
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWhenIndexOutOfRange();
    }

    static void testTryAdvanceWithValidConsumer() {
        class DummyConsumer implements LongConsumer {
            long lastAccepted = -1;
            @Override
            public void accept(long value) {
                lastAccepted = value;
            }
        }
        DummyConsumer consumer = new DummyConsumer();
        // Accessing the method directly from the class
        boolean result = Spliterators.tryAdvance(consumer);
        // Since index is initialized to 0 and fence > 0, expect true
        if (result != true) {
            throw new AssertionError("Expected true from tryAdvance with valid consumer");
        }
        if (consumer.lastAccepted != 0) {
            throw new AssertionError("Consumer did not accept the correct value");
        }
    }

    static void testTryAdvanceWithNullConsumer() {
        try {
            Spliterators.tryAdvance((LongConsumer) null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testTryAdvanceWhenIndexOutOfRange() {
        // Save original index and fence
        int originalIndex = getIndex();
        int originalFence = getFence();
        try {
            setIndex(100);
            setFence(200);
            boolean result = Spliterators.tryAdvance(value -> {});
            if (result != false) {
                throw new AssertionError("Expected false when index out of range");
            }
        } finally {
            setIndex(originalIndex);
            setFence(originalFence);
        }
    }

    // Helper methods to manipulate index and fence for testing
    // Since index and fence are package-private, use reflection
    private static java.lang.reflect.Field indexField;
    private static java.lang.reflect.Field fenceField;

    static {
        try {
            indexField = java.util.Spliterators.class.getDeclaredField("index");
            fenceField = java.util.Spliterators.class.getDeclaredField("fence");
            indexField.setAccessible(true);
            fenceField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    static int getIndex() {
        try {
            return (int) indexField.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static void setIndex(int value) {
        try {
            indexField.set(null, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static int getFence() {
        try {
            return (int) fenceField.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static void setFence(int value) {
        try {
            fenceField.set(null, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
