/*
 * @test
 * @summary Test for ReferencePipeline.forEachOrdered to verify behavior with valid and null consumers.
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.ReferencePipeline;
import java.util.function.Consumer;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testForEachOrderedWithValidConsumer();
        testForEachOrderedWithNullConsumer();
    }

    static void testForEachOrderedWithValidConsumer() {
        class DummyConsumer implements Consumer<Object> {
            boolean called = false;
            @Override
            public void accept(Object t) {
                called = true;
            }
        }
        DummyConsumer consumer = new DummyConsumer();
        try {
            // Create a simple stream with a single element
            ReferencePipeline<Object> stream = ReferencePipeline.of(java.util.Arrays.asList("a"));
            stream.forEachOrdered(consumer);
            assert consumer.called : "Consumer should be called";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid consumer test", e);
        }
    }

    static void testForEachOrderedWithNullConsumer() {
        try {
            ReferencePipeline<Object> stream = ReferencePipeline.of(java.util.Arrays.asList("a"));
            stream.forEachOrdered(null);
            throw new AssertionError("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
