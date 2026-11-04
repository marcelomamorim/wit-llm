/* @test    @summary Test TreeMap.tryAdvance method for correct iteration and concurrent modification detection    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.function.Consumer;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNormal();
        testTryAdvanceNullConsumer();
        testConcurrentModification();
    }

    static void testTryAdvanceNormal() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = key -> sb.append(key).append(",");
        boolean result = map.tryAdvance(consumer);
        assert result : "Expected tryAdvance to return true";
        assert sb.toString().startsWith("1,") || sb.toString().startsWith("2,") || sb.toString().startsWith("3,") : "Consumer did not process a key";
    }

    static void testTryAdvanceNullConsumer() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        try {
            map.tryAdvance(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        Consumer<Integer> consumer = key -> {
            map.put(4, "four"); // structural modification during iteration
        };
        try {
            map.tryAdvance(consumer);
            assert false : "Expected ConcurrentModificationException";
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
