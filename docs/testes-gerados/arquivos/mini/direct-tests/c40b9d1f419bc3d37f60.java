/*
 * @test
 * @summary Test StringUTF16.forEachRemaining with valid and invalid inputs
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StringUTF16WitupTest {

    static class DummyStringUTF16 {
        private final String[] data;
        private int index;
        private final int fence;

        DummyStringUTF16(String[] data) {
            this.data = data;
            this.index = 0;
            this.fence = data.length;
        }

        public String next() {
            if (index >= fence) {
                throw new IllegalStateException("No more elements");
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
        testForEachRemainingNormal();
        testForEachRemainingNullAction();
    }

    private static void testForEachRemainingNormal() {
        String[] elements = {"a", "b", "c"};
        DummyStringUTF16 iter = new DummyStringUTF16(elements);
        final List<String> collected = new ArrayList<>();
        iter.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                collected.add(s);
            }
        });
        if (collected.size() != elements.length) {
            throw new RuntimeException("Expected " + elements.length + " elements, got " + collected.size());
        }
        for (int i = 0; i < elements.length; i++) {
            if (!elements[i].equals(collected.get(i))) {
                throw new RuntimeException("Element mismatch at index " + i + ": expected '" + elements[i] + "' but got '" + collected.get(i) + "'");
            }
        }
    }

    private static void testForEachRemainingNullAction() {
        String[] elements = {"x"};
        DummyStringUTF16 iter = new DummyStringUTF16(elements);
        try {
            iter.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            if (!"forEachRemaining action missing".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + e.getMessage());
            }
        }
    }
}
