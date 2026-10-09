/*
 * @test
 * @summary Test ConcurrentSkipListMap.forEach method behavior including null action
 * @run main ConcurrentSkipListMapWitupTest_Pa9d924d2999c
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;

public class ConcurrentSkipListMapWitupTest_Pa9d924d2999c {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachEmptyMap();
        testForEachWithEntries();
    }

    private static void testForEachNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testForEachEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        final int[] count = {0};
        map.forEach(new BiConsumer<Integer, String>() {
            public void accept(Integer k, String v) {
                count[0]++;
            }
        });
        if (count[0] != 0) {
            throw new RuntimeException("forEach on empty map should not invoke action");
        }
    }

    private static void testForEachWithEntries() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        final StringBuilder sb = new StringBuilder();
        map.forEach(new BiConsumer<Integer, String>() {
            public void accept(Integer k, String v) {
                sb.append(k).append("=").append(v).append(",");
            }
        });

        String result = sb.toString();
        // The map is sorted by natural ordering of keys
        if (!result.equals("1=one,2=two,3=three,")) {
            throw new RuntimeException("forEach did not visit entries in sorted order or missed entries: " + result);
        }
    }
}
