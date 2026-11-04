/*
 * @test
 * @summary Test StringUTF16.LinesSpliterator.forEachRemaining behavior and exception
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;
import java.util.function.Consumer;

public class StringUTF16WitupTest {

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
        testForEachRemainingConsumesAll();
    }

    private static void testForEachRemainingNullAction() {
        LinesSpliterator spliterator = new LinesSpliterator(new String[] {"a", "b"});
        try {
            spliterator.forEachRemaining(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"forEachRemaining action missing".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testForEachRemainingConsumesAll() {
        final StringBuilder sb = new StringBuilder();
        LinesSpliterator spliterator = new LinesSpliterator(new String[] {"line1", "line2", "line3"});
        spliterator.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        });
        String result = sb.toString();
        if (!result.equals("line1,line2,line3,")) {
            throw new Error("forEachRemaining did not consume all elements, got: " + result);
        }
    }
}
