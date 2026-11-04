/*
 * @test
 * @summary Verify that remove(int) on immutable lists from List.of throws UnsupportedOperationException and leaves the list unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        checkRemoveIndexThrows(List.of(1, 2, 3), 0);
        checkRemoveIndexThrows(List.of(1, 2, 3), 2);
        checkRemoveIndexThrows(List.of("a"), 0);
        checkRemoveIndexThrows(List.of(), 0);

        List<Integer> list = List.of(1, 2, 3);
        checkRemoveIndexThrows(list, 1);
        if (!list.equals(List.of(1, 2, 3))) {
            throw new RuntimeException("List was modified by remove(int)");
        }
    }

    static void checkRemoveIndexThrows(List<?> list, int index) {
        try {
            list.remove(index);
            throw new RuntimeException("No UnsupportedOperationException for remove(" + index + ")");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
