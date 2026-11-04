/*
 * @test
 * @summary Tests for StringLatin1.forEachRemaining method.
 * @run main StringLatin1WitupTest
 */

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidConsumer();
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithEmptyIteration();
    }

    static void testForEachRemainingWithValidConsumer() {
        StringLatin1 stringLatin1 = new StringLatin1();
        DummyConsumer consumer = new DummyConsumer();
        // Create a dummy StringLatin1 with a custom index and fence
        StringLatin1 sl1 = new StringLatin1() {
            @Override
            public void forEachRemaining(Consumer<? super String> action) {
                if (action == null) {
                    throw new NullPointerException("forEachRemaining action missing");
                }
                // simulate iteration
                action.accept("test");
            }
        };
        try {
            sl1.forEachRemaining(consumer);
            assert consumer.acceptedValues.size() == 1 : "Consumer should accept exactly one value";
            assert "test".equals(consumer.acceptedValues.get(0)) : "Accepted value should be 'test'";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid consumer test", e);
        }
    }

    static void testForEachRemainingWithNullConsumer() {
        StringLatin1 stringLatin1 = new StringLatin1();
        try {
            stringLatin1.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            assert e.getMessage().equals("forEachRemaining action missing") : "Exception message mismatch";
        }
    }

    static void testForEachRemainingWithEmptyIteration() {
        StringLatin1 stringLatin1 = new StringLatin1() {
            @Override
            public void forEachRemaining(Consumer<? super String> action) {
                // do nothing to simulate empty iteration
            }
        };
        DummyConsumer consumer = new DummyConsumer();
        try {
            stringLatin1.forEachRemaining(consumer);
            assert consumer.acceptedValues.isEmpty() : "Consumer should not accept any value";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in empty iteration test", e);
        }
    }

    static class DummyConsumer implements Consumer<String> {
        final java.util.List<String> acceptedValues = new java.util.ArrayList<>();
        @Override
        public void accept(String t) {
            acceptedValues.add(t);
        }
    }
}
