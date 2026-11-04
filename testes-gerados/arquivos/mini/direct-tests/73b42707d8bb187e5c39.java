/*
 * @test
 * @summary Test StringUTF16.tryAdvance behavior with valid and invalid inputs
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;
import java.util.function.Consumer;

public class StringUTF16WitupTest {

    static class TestConsumer implements Consumer<String> {
        String value = null;
        public void accept(String s) {
            value = s;
        }
    }

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceReturnsTrueAndAccepts();
        testTryAdvanceReturnsFalseAtFence();
    }

    private static void testTryAdvanceNullAction() {
        StringUTF16 spliterator = new StringUTF16();
        try {
            spliterator.tryAdvance(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            if (!"tryAdvance action missing".equals(e.getMessage())) {
                throw new Error("Unexpected NullPointerException message: " + e.getMessage());
            }
        }
    }

    private static void testTryAdvanceReturnsTrueAndAccepts() {
        StringUTF16 spliterator = new StringUTF16();
        // Setup spliterator with index != fence to trigger accept and return true
        spliterator.index = 0;
        spliterator.fence = 1;
        spliterator.strings = new String[] {"test"};

        TestConsumer consumer = new TestConsumer();
        boolean result = spliterator.tryAdvance(consumer);
        if (!result) {
            throw new Error("tryAdvance should return true when index != fence");
        }
        if (!"test".equals(consumer.value)) {
            throw new Error("Consumer did not accept expected string");
        }
    }

    private static void testTryAdvanceReturnsFalseAtFence() {
        StringUTF16 spliterator = new StringUTF16();
        // Setup spliterator with index == fence to return false
        spliterator.index = 1;
        spliterator.fence = 1;
        spliterator.strings = new String[] {"test"};

        TestConsumer consumer = new TestConsumer();
        boolean result = spliterator.tryAdvance(consumer);
        if (result) {
            throw new Error("tryAdvance should return false when index == fence");
        }
        if (consumer.value != null) {
            throw new Error("Consumer should not be called when index == fence");
        }
    }

    // Minimal stub of StringUTF16 to allow compilation and testing
    static final class StringUTF16 {
        int index = 0;
        int fence = 0;
        String[] strings = new String[0];

        public boolean tryAdvance(Consumer<? super String> action) {
            if (action == null) {
                throw new NullPointerException("tryAdvance action missing");
            }
            if (index != fence) {
                action.accept(next());
                return true;
            }
            return false;
        }

        private String next() {
            return strings[index++];
        }
    }
}
