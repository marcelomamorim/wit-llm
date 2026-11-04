/*
 * @test
 * @summary Witup-derived checks for index validation in ImmutableCollections SubList.get via public List.of(...).subList(...)
 * @run main ImmutableCollectionsWitupTest
 */
import java.util.List;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        List<Integer> base = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<Integer> sub = base.subList(2, 5);
        check(sub.size() == 3, "unexpected size");
        check(sub.get(0) == 2, "unexpected element at 0");
        check(sub.get(1) == 3, "unexpected element at 1");
        check(sub.get(2) == 4, "unexpected element at 2");

        expectIndexOutOfBounds(sub, 3);
        expectIndexOutOfBounds(sub, -1);
        expectIndexOutOfBounds(sub, Integer.MAX_VALUE);
        expectIndexOutOfBounds(sub, Integer.MIN_VALUE);

        List<Integer> empty = base.subList(4, 4);
        check(empty.size() == 0, "empty sublist size");
        expectIndexOutOfBounds(empty, 0);
    }

    static void expectIndexOutOfBounds(List<Integer> list, int index) {
        try {
            Object o = list.get(index);
            throw new AssertionError("no exception for index " + index + ", returned " + o);
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
