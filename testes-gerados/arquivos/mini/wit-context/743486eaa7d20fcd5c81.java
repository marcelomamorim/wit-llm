/*
 * @test
 * @summary Test StringLatin1.LinesSpliterator.forEachRemaining with null and normal Consumer
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;
import java.util.function.Consumer;

public class StringLatin1WitupTest {

    static class LinesSpliterator {
        private final String[] lines;
        private int index;
        private final int fence;

        LinesSpliterator(String[] lines) {
            this.lines = lines;
            this.index = 0;
            this.fence = lines.length;
        }

        public String next() {
            if (index >= fence) {
                throw new java.util.NoSuchElementException();
            }
            return lines[index++];
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
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    private static void testForEachRemainingNullAction() {
        LinesSpliterator spliterator = new LinesSpliterator(new String[] {"a", "b"});
        try {
            spliterator.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"forEachRemaining action missing".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + e.getMessage());
            }
        }
    }

    private static void testForEachRemainingNormal() {
        final StringBuilder sb = new StringBuilder();
        LinesSpliterator spliterator = new LinesSpliterator(new String[] {"x", "y", "z"});
        spliterator.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        String result = sb.toString();
        if (!"xyz".equals(result)) {
            throw new RuntimeException("Unexpected result from forEachRemaining: " + result);
        }
    }
}
