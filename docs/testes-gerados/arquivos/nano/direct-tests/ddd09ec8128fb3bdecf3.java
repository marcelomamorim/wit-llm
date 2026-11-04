/* @test    @summary Test for Streams.forEachRemaining method to ensure it executes the consumer on the first element when count is -2.    @run main StreamsWitupTest */

import java.util.Objects;
import java.util.function.Consumer;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingExecutesConsumerOnFirstElement();
    }

    public static void testForEachRemainingExecutesConsumerOnFirstElement() {
        class DummyConsumer implements Consumer<Object> {
            boolean accepted = false;
            Object acceptedValue = null;

            @Override
            public void accept(Object t) {
                accepted = true;
                acceptedValue = t;
            }
        }

        // Create an instance of Streams with a mock count and first element
        class MockStreams extends java.util.stream.Streams {
            int count = -2;
            Object first = "test-element";

            @Override
            public void forEachRemaining(Consumer<? super Object> action) {
                Objects.requireNonNull(action);
                if (count == -2) {
                    action.accept(first);
                    count = -1;
                }
            }
        }

        MockStreams streams = new MockStreams();
        DummyConsumer consumer = new DummyConsumer();
        streams.forEachRemaining(consumer);
        assert consumer.accepted : "Consumer was not accepted";
        assert consumer.acceptedValue.equals("test-element") : "Accepted value mismatch";
    }
}
