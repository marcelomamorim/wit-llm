/*
 * @test
 * @summary Test ConcurrentSkipListMap.ValueSpliterator.tryAdvance behavior including NPE on null action
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceReturnsTrueAndFalse();
    }

    static void testTryAdvanceNullAction() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("key", "value");
        ConcurrentSkipListMap.ValueSpliterator<String, String> spliterator =
            new ConcurrentSkipListMap.ValueSpliterator<>(map, null, null, null, map.firstEntry());

        boolean npeThrown = false;
        try {
            spliterator.tryAdvance(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testTryAdvanceReturnsTrueAndFalse() {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "A");
        map.put("b", "B");

        ConcurrentSkipListMap.ValueSpliterator<String, String> spliterator =
            new ConcurrentSkipListMap.ValueSpliterator<>(map, null, null, null, map.firstEntry());

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced1 = spliterator.tryAdvance(consumer);
        if (!advanced1) {
            throw new AssertionError("Expected tryAdvance to return true on first element");
        }
        if (!sb.toString().equals("A")) {
            throw new AssertionError("Expected consumer to receive 'A', got: " + sb.toString());
        }

        sb.setLength(0);
        boolean advanced2 = spliterator.tryAdvance(consumer);
        if (!advanced2) {
            throw new AssertionError("Expected tryAdvance to return true on second element");
        }
        if (!sb.toString().equals("B")) {
            throw new AssertionError("Expected consumer to receive 'B', got: " + sb.toString());
        }

        sb.setLength(0);
        boolean advanced3 = spliterator.tryAdvance(consumer);
        if (advanced3) {
            throw new AssertionError("Expected tryAdvance to return false after all elements consumed");
        }
        if (sb.length() != 0) {
            throw new AssertionError("Expected consumer not to be called when no elements remain");
        }
    }
}
