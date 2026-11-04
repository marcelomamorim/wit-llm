/*
 * @test
 * @summary Witup-derived checks for TreeMap submap tailMap range validation
 * @run main TreeMapWitupTest
 */

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TreeMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIAE(NavigableMap<Integer, String> view, int key, boolean inclusive) {
        try {
            view.tailMap(key, inclusive);
            throw new RuntimeException("Expected IllegalArgumentException for key " + key
                    + " inclusive=" + inclusive);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        TreeMap<Integer, String> base = new TreeMap<>();
        for (int i = 1; i <= 9; i++) {
            base.put(i, "v" + i);
        }

        NavigableMap<Integer, String> sub = base.subMap(3, true, 7, true);
        check(sub.keySet().toString().equals("[3, 4, 5, 6, 7]"), "unexpected base submap keys");

        expectIAE(sub, 2, true);
        expectIAE(sub, 2, false);
        expectIAE(sub, 8, true);
        expectIAE(sub, 8, false);

        check(sub.tailMap(5, true).keySet().toString().equals("[5, 6, 7]"),
                "tailMap(5, true) keys mismatch");
        check(sub.tailMap(3, false).keySet().toString().equals("[4, 5, 6, 7]"),
                "tailMap(3, false) keys mismatch");
        check(sub.tailMap(3, true).keySet().toString().equals("[3, 4, 5, 6, 7]"),
                "tailMap(3, true) keys mismatch");
        check(sub.tailMap(7, true).keySet().toString().equals("[7]"),
                "tailMap(7, true) keys mismatch");
        check(sub.tailMap(7, false).isEmpty(), "tailMap(7, false) should be empty");

        Map<Integer, String> nested = sub.tailMap(4, true);
        check(nested.size() == 4, "nested view size mismatch");
        check("v7".equals(nested.get(7)), "nested view value mismatch");
    }
}
