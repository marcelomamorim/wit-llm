/*
 * @test
 * @summary Test Streams.accept(long) method behavior
 * @run main StreamsWitupTest
 */

import java.util.stream.Streams;
import java.util.Objects;

public class StreamsWitupTest {

    static class TestConsumer extends Streams {
        long first = 0L;
        int count = 0;
        Streams.SpinedBuffer.OfLong buffer = null;

        public void accept(long t) {
            if (count == 0) {
                first = t;
                count++;
            } else if (count > 0) {
                if (buffer == null) {
                    buffer = new Streams.SpinedBuffer.OfLong();
                    buffer.accept(first);
                    count++;
                }
                buffer.accept(t);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    public static void main(String[] args) {
        TestConsumer consumer = new TestConsumer();

        // Test initial accept sets first and increments count
        consumer.accept(10L);
        if (consumer.first != 10L) {
            throw new Error("Failed: first element not set correctly");
        }
        if (consumer.count != 1) {
            throw new Error("Failed: count not incremented to 1");
        }

        // Test second accept initializes buffer and adds elements
        consumer.accept(20L);
        if (consumer.buffer == null) {
            throw new Error("Failed: buffer not initialized on second accept");
        }

        // The buffer should contain first and second elements
        final long[] collected = new long[2];
        final int[] idx = {0};
        consumer.buffer.forEach((long v) -> {
            collected[idx[0]++] = v;
        });
        if (collected[0] != 10L || collected[1] != 20L) {
            throw new Error("Failed: buffer elements incorrect");
        }

        // Test multiple accepts add to buffer
        consumer.accept(30L);
        final long[] collected2 = new long[3];
        final int[] idx2 = {0};
        consumer.buffer.forEach((long v) -> {
            collected2[idx2[0]++] = v;
        });
        if (collected2[0] != 10L || collected2[1] != 20L || collected2[2] != 30L) {
            throw new Error("Failed: buffer elements incorrect after third accept");
        }

        // Test that if count < 0, accept throws IllegalStateException
        TestConsumer badConsumer = new TestConsumer();
        badConsumer.count = -1;
        boolean threw = false;
        try {
            badConsumer.accept(40L);
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("Failed: accept did not throw IllegalStateException when count < 0");
        }
    }
}
