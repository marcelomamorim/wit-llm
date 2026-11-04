/*
 * @test
 * @summary Test for higherEntry method in ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testHigherEntryWithNullKey();
        testHigherEntryWithExistingKey();
        testHigherEntryWithNonExistingKey();
    }

    static void testHigherEntryWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            map.higherEntry(null);
            System.out.println("FAIL: Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null key");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception type: " + e);
        }
    }

    static void testHigherEntryWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        Map.Entry<Integer, String> entry = map.higherEntry(1);
        if (entry != null && entry.getKey() == 3 && "three".equals(entry.getValue())) {
            System.out.println("PASS: higherEntry for key 1 returned correct entry");
        } else {
            System.out.println("FAIL: higherEntry for key 1 returned: " + entry);
        }
    }

    static void testHigherEntryWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        Map.Entry<Integer, String> entry = map.higherEntry(2);
        if (entry != null && entry.getKey() == 3 && "three".equals(entry.getValue())) {
            System.out.println("PASS: higherEntry for key 2 returned correct entry");
        } else {
            System.out.println("FAIL: higherEntry for key 2 returned: " + entry);
        }
    }
}
