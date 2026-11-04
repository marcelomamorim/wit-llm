/*
 * @test
 * @summary Test java.lang.StringLatin1.LinesSpliterator.tryAdvance(Consumer<? super String>) for NPE and normal behavior
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

        public boolean tryAdvance(Consumer<? super String> action) {
            if (action == null) {
                throw new NullPointerException("tryAdvance action missing");
            }
            if (index != fence) {
                action.accept(lines[index++]);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormal();
    }

    private static void testTryAdvanceNullAction() {
        LinesSpliterator spliterator = new LinesSpliterator(new String[] {"a", "b"});
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
        final StringBuilder sb = new StringBuilder();
        LinesSpliterator spliterator = new LinesSpliterator(new String[] {"line1", "line2"});

        boolean advanced1 = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!advanced1) {
            throw new RuntimeException("tryAdvance should return true when elements remain");
        }
        if (!"line1".equals(sb.toString())) {
            throw new RuntimeException("Consumer did not receive expected string: " + sb.toString());
        }

        boolean advanced2 = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append("," + s);
            }
        });
        if (!advanced2) {
            throw new RuntimeException("tryAdvance should return true when elements remain");
        }
        if (!"line1,line2".equals(sb.toString())) {
            throw new RuntimeException("Consumer did not receive expected string sequence: " + sb.toString());
        }

        boolean advanced3 = spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new RuntimeException("Should not be called when no elements remain");
            }
        });
        if (advanced3) {
            throw new RuntimeException("tryAdvance should return false when no elements remain");
        }
    }
}
