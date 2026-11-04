/*
 * @test
 * @summary Tests SubList.get(int) bounds checking and offset-based element access on immutable lists
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        List<Integer> base = List.of(1, 2, 3, 4, 5);
        List<Integer> sub = base.subList(1, 4);

        check(sub.get(0).intValue() == 2, "sub.get(0) should be 2");
        check(sub.get(1).intValue() == 3, "sub.get(1) should be 3");
        check(sub.get(2).intValue() == 4, "sub.get(2) should be 4");

        expectIOOBE(sub, -1);
        expectIOOBE(sub, 3);
        expectIOOBE(sub, Integer.MAX_VALUE);

        List<Integer> nested = sub.subList(1, 3);
        check(nested.get(0).intValue() == 3, "nested.get(0) should be 3");
        check(nested.get(1).intValue() == 4, "nested.get(1) should be 4");
        expectIOOBE(nested, 2);

        List<String> strs = List.of("a", "b", "c");
        List<String> empty = strs.subList(0, 0);
        expectIOOBE(empty, 0);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectIOOBE(List<?> list, int index) {
        try {
            list.get(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + index);
    }
}
