/*
 * @test
 * @summary Test tryAdvance method of ConcurrentSkipListMap for correct iteration over elements.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithElements();
        testTryAdvanceWithEmptyMap();
        testTryAdvanceWithNullConsumer();
    }

    static void testTryAdvanceWithElements() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s).append(",");
        boolean hasNext = map.tryAdvance(consumer);
        assert hasNext : "Expected tryAdvance to return true when elements are present";
        assert result.toString().contains("one") || result.toString().contains("two") || result.toString().contains("three") : "Consumer should have been called with element value";
    }

    static void testTryAdvanceWithEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s);
        boolean hasNext = map.tryAdvance(consumer);
        assert !hasNext : "Expected tryAdvance to return false when map is empty";
        assert result.length() == 0 : "Consumer should not have been called";
    }

    static void testTryAdvanceWithNullConsumer() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when consumer is null";
    }
}
