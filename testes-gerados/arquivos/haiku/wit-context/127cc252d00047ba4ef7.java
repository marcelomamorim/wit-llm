/*
 * @test
 * @summary Verify that immutable list add(int, E) always throws UnsupportedOperationException and leaves the list unchanged
 * @run main ImmutableCollectionsWitupTest
 */
import java.util.Arrays;
import java.util.List;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        check(List.of(), new Integer[0]);
        check(List.of(1), 1);
        check(List.of(1, 2, 3), 1, 2, 3);
    }

    static void check(List<Integer> list, Integer... expected) {
        int size = list.size();
        expectUoe(() -> list.add(0, 42));
        expectUoe(() -> list.add(size, 42));
        expectUoe(() -> list.add(0, null));
        if (list.size() != size) {
            throw new AssertionError("size changed");
        }
        if (!Arrays.asList(expected).equals(list)) {
            throw new AssertionError("contents changed");
        }
    }

    static void expectUoe(Runnable r) {
        try {
            r.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new AssertionError("expected UnsupportedOperationException");
    }
}
