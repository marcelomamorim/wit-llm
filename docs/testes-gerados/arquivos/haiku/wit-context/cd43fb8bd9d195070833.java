/*
 * @test
 * @summary Checks descending tailMap views of TreeMap, including the out-of-range IllegalArgumentException for bounded views
 * @run main TreeMapWitupTest
 */

import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        TreeMap<Integer, String> base = new TreeMap<>();
        for (int i = 1; i <= 5; i++) {
            base.put(i, "v" + i);
        }

        NavigableMap<Integer, String> desc = base.descendingMap();
        check("[3, 2, 1]", desc.tailMap(3, true).keySet().toString());
        check("[2, 1]", desc.tailMap(3, false).keySet().toString());
        check("[5, 4, 3, 2, 1]", desc.tailMap(5, true).keySet().toString());

        NavigableMap<Integer, String> bounded =
                base.subMap(2, true, 4, true).descendingMap();
        check("[3, 2]", bounded.tailMap(3, true).keySet().toString());
        check("[2]", bounded.tailMap(3, false).keySet().toString());

        boolean thrown = false;
        try {
            bounded.tailMap(1, false);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for out-of-range fromKey");
        }
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
