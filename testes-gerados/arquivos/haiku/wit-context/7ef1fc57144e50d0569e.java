/*
 * @test
 * @summary Verify that set(int, E) on immutable lists created by List.of always throws UnsupportedOperationException and leaves the list unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        List<Integer> single = List.of(1);
        checkSetThrows(single, 0, 9);
        check(single.size() == 1 && single.get(0) == 1, "single-element list changed");

        List<Integer> multi = List.of(1, 2, 3);
        checkSetThrows(multi, 1, 9);
        checkSetThrows(multi, 0, null);
        check(multi.size() == 3 && multi.get(0) == 1 && multi.get(1) == 2 && multi.get(2) == 3,
              "multi-element list changed");

        List<Integer> empty = List.of();
        checkSetThrows(empty, 0, 9);
        check(empty.isEmpty(), "empty list changed");
    }

    static void checkSetThrows(List<Integer> list, int index, Integer value) {
        try {
            list.set(index, value);
            throw new AssertionError("expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
