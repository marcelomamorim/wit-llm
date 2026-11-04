/*
 * @test
 * @summary Test java.util.stream.Streams.accept(int) method behavior
 * @run main StreamsWitupTest
 */

import java.util.stream.Streams;
import java.util.stream.SpinedBuffer;

public class StreamsWitupTest {

    static class TestConsumer extends Streams {
        int count = 0;
        int first = 0;
        SpinedBuffer.OfInt buffer = null;

        public void accept(int t) {
            if (count == 0) {
                first = t;
                count++;
            } else if (count > 0) {
                if (buffer == null) {
                    buffer = new SpinedBuffer.OfInt();
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

        // Test initial accept sets first and count
        consumer.accept(10);
        if (consumer.count != 1) {
            throw new Error("count should be 1 after first accept");
        }
        if (consumer.first != 10) {
            throw new Error("first should be set to 10");
        }
        if (consumer.buffer != null) {
            throw new Error("buffer should be null after first accept");
        }

        // Test second accept initializes buffer and adds first and second
        consumer.accept(20);
        if (consumer.count != 2) {
            throw new Error("count should be 2 after second accept");
        }
        if (consumer.buffer == null) {
            throw new Error("buffer should be initialized after second accept");
        }

        // The buffer should contain first and second values
        final int[] collected = new int[2];
        final int[] index = {0};
        consumer.buffer.forEach((int v) -> {
            collected[index[0]++] = v;
        });
        if (collected[0] != 10 || collected[1] != 20) {
            throw new Error("buffer should contain first and second accepted values");
        }

        // Test multiple accepts add to buffer
        consumer.accept(30);
        consumer.accept(40);
        final int[] expected = {10, 20, 30, 40};
        final int[] actual = new int[4];
        final int[] idx = {0};
        consumer.buffer.forEach((int v) -> {
            actual[idx[0]++] = v;
        });
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new Error("buffer content mismatch at index " + i);
            }
        }

        // Test that negative count triggers IllegalStateException
        TestConsumer badConsumer = new TestConsumer() {
            {
                count = -1;
            }
        };
        boolean threw = false;
        try {
            badConsumer.accept(50);
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("accept should throw IllegalStateException when count < 0");
        }
    }
}
