/* @test
   @summary Verifies the behavior of forEach method in ConcurrentSkipListMap.
   @run main ConcurrentSkipListMapWitupTest_Pa20fca6d9b95
 */
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;
import java.util.ArrayList;
import java.util.List;

public class ConcurrentSkipListMapWitupTest_Pa20fca6d9b95 {
    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithEmptyMap();
        testForEachWithNullAction();
    }

    static void testForEachWithElements() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        List<String> collectedValues = new ArrayList<>();
        BiConsumer<Integer, String> consumer = (k, v) -> {
            collectedValues.add(v);
        };
        map.forEach(consumer);
        boolean hasOne = collectedValues.contains("one");
        boolean hasTwo = collectedValues.contains("two");
        boolean hasThree = collectedValues.contains("three");
        if (!(hasOne && hasTwo && hasThree)) {
            throw new AssertionError("forEach did not process all elements");
        }
    }

    static void testForEachWithEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        List<String> collectedValues = new ArrayList<>();
        BiConsumer<Integer, String> consumer = (k, v) -> {
            collectedValues.add(v);
        };
        map.forEach(consumer);
        if (!collectedValues.isEmpty()) {
            throw new AssertionError("forEach on empty map should not process any elements");
        }
    }

    static void testForEachWithNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("forEach with null action should throw NullPointerException");
        }
    }
}
