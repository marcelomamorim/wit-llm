/*
 * @test
 * @summary Test StringUTF16.LinesSpliterator.tryAdvance behavior including null action check
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;
import java.util.function.Consumer;

public class StringUTF16WitupTest {

    static class DummySpliterator extends StringUTF16.LinesSpliterator {
        private final String[] lines;
        private int index = 0;
        private final int fence;

        DummySpliterator(String[] lines) {
            super(); // LinesSpliterator has no visible constructor, but assuming default
            this.lines = lines;
            this.fence = lines.length;
        }

        @Override
        public String next() {
            return lines[index++];
        }

        @Override
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
    }

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormal();
        testTryAdvanceEnd();
    }

    private static void testTryAdvanceNullAction() {
        DummySpliterator spliterator = new DummySpliterator(new String[] {"line1"});
        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"tryAdvance action missing".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + e.getMessage());
            }
        }
    }

    private static void testTryAdvanceNormal() {
        final String[] lines = {"line1", "line2"};
        DummySpliterator spliterator = new DummySpliterator(lines);
        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced = spliterator.tryAdvance(consumer);
        if (!advanced) {
            throw new RuntimeException("tryAdvance should return true when elements remain");
        }
        if (!"line1".equals(sb.toString())) {
            throw new RuntimeException("Consumer did not receive expected string");
        }
    }

    private static void testTryAdvanceEnd() {
        final String[] lines = {};
        DummySpliterator spliterator = new DummySpliterator(lines);
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                throw new RuntimeException("Should not be called");
            }
        };

        boolean advanced = spliterator.tryAdvance(consumer);
        if (advanced) {
            throw new RuntimeException("tryAdvance should return false when no elements remain");
        }
    }
}
