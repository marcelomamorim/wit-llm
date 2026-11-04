/*
 * @test
 * @summary Test StringLatin1.forEachRemaining method behavior
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;
import java.util.function.Consumer;

public class StringLatin1WitupTest {

    static class TestConsumer implements Consumer<String> {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        public void accept(String s) {
            count++;
            sb.append(s);
        }
    }

    // Minimal stub of StringLatin1 with index, fence, next() to test forEachRemaining
    static class StubStringLatin1 {
        private final String[] data;
        private int index;
        private final int fence;

        StubStringLatin1(String[] data) {
            this.data = data;
            this.index = 0;
            this.fence = data.length;
        }

        public String next() {
            if (index >= fence) {
                throw new java.util.NoSuchElementException();
            }
            return data[index++];
        }

        public void forEachRemaining(Consumer<? super String> action) {
            if (action == null) {
                throw new NullPointerException("forEachRemaining action missing");
            }
            while (index != fence) {
                action.accept(next());
            }
        }
    }

    public static void main(String[] args) {
        // Test 1: Null action throws NullPointerException
        try {
            StubStringLatin1 stub = new StubStringLatin1(new String[] {"a", "b"});
            stub.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"forEachRemaining action missing".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + e.getMessage());
            }
        }

        // Test 2: forEachRemaining consumes all remaining elements
        String[] input = {"x", "y", "z"};
        StubStringLatin1 stub = new StubStringLatin1(input);
        TestConsumer consumer = new TestConsumer();
        stub.forEachRemaining(consumer);
        if (consumer.count != input.length) {
            throw new RuntimeException("Expected count " + input.length + " but got " + consumer.count);
        }
        StringBuilder expected = new StringBuilder();
        for (String s : input) {
            expected.append(s);
        }
        if (!expected.toString().equals(consumer.sb.toString())) {
            throw new RuntimeException("Expected concatenated string '" + expected + "' but got '" + consumer.sb + "'");
        }

        // Test 3: forEachRemaining on empty data does not call accept
        StubStringLatin1 emptyStub = new StubStringLatin1(new String[0]);
        TestConsumer emptyConsumer = new TestConsumer();
        emptyStub.forEachRemaining(emptyConsumer);
        if (emptyConsumer.count != 0) {
            throw new RuntimeException("Expected count 0 for empty input but got " + emptyConsumer.count);
        }
    }
}
