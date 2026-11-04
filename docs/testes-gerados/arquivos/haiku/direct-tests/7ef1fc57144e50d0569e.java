/*
 * @test
 * @summary Verifies that immutable lists from List.of reject set() with UnsupportedOperationException and remain unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        // List12 (1 or 2 elements)
        checkSet(List.of("a"), 0, "x");
        checkSet(List.of("a", "b"), 1, null);

        // ListN (3 or more elements)
        checkSet(List.of("a", "b", "c"), 0, "x");
        checkSet(List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"), 5, "x");

        // Empty immutable list
        checkSet(List.<String>of(), 0, "x");

        // Out-of-range index still yields UnsupportedOperationException
        checkSet(List.of("a", "b", "c"), 10, "x");
        checkSet(List.of("a"), -1, "x");
    }

    static void checkSet(List<String> list, int index, String value) {
        String before = list.toString();
        try {
            list.set(index, value);
            throw new RuntimeException("set did not throw UnsupportedOperationException for index " + index);
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (!before.equals(list.toString())) {
            throw new RuntimeException("list was modified by failed set: " + before + " -> " + list);
        }
    }
}
