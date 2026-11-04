/*
 * @test
 * @summary Verifies that sort on immutable lists created via List.of throws UnsupportedOperationException and leaves the content unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        checkSortThrowsUoe(() -> List.of(3, 1, 2).sort(null), "sort(null) on List.of(3,1,2)");
        checkSortThrowsUoe(() -> List.of(3, 1, 2).sort(Comparator.naturalOrder()), "sort(naturalOrder) on List.of(3,1,2)");
        checkSortThrowsUoe(() -> List.of("b", "a").sort(Comparator.reverseOrder()), "sort(reverseOrder) on List.of(b,a)");
        checkSortThrowsUoe(() -> List.of().sort(null), "sort(null) on List.of()");

        List<Integer> list = List.of(3, 1, 2);
        try {
            list.sort(null);
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (!list.equals(Arrays.asList(3, 1, 2))) {
            throw new RuntimeException("List content changed after failed sort: " + list);
        }
    }

    private static void checkSortThrowsUoe(Runnable action, String description) {
        try {
            action.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException for " + description);
    }
}
