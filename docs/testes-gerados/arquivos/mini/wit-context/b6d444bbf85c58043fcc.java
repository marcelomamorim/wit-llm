/*
 * @test
 * @summary Test ConcurrentSkipListMap ceilingKey method behavior including null key handling
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");

        // Test ceilingKey with existing keys
        Integer ck1 = map.ceilingKey(1);
        if (!Integer.valueOf(1).equals(ck1)) {
            throw new AssertionError("ceilingKey(1) should return 1 but returned " + ck1);
        }

        Integer ck2 = map.ceilingKey(2);
        if (!Integer.valueOf(3).equals(ck2)) {
            throw new AssertionError("ceilingKey(2) should return 3 but returned " + ck2);
        }

        Integer ck3 = map.ceilingKey(5);
        if (!Integer.valueOf(5).equals(ck3)) {
            throw new AssertionError("ceilingKey(5) should return 5 but returned " + ck3);
        }

        Integer ck4 = map.ceilingKey(6);
        if (ck4 != null) {
            throw new AssertionError("ceilingKey(6) should return null but returned " + ck4);
        }

        // Test ceilingKey with null key - expect NullPointerException
        boolean npeThrown = false;
        try {
            map.ceilingKey(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("ceilingKey(null) should throw NullPointerException");
        }
    }
}
