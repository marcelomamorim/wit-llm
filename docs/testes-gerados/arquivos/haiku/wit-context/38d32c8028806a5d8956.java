/*
 * @test
 * @summary Witup-derived checks for ArrayList.subList range validation
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        expectThrows(IndexOutOfBoundsException.class, () -> list.subList(-1, 2));
        expectThrows(IndexOutOfBoundsException.class, () -> list.subList(0, 4));
        expectThrows(IllegalArgumentException.class, () -> list.subList(2, 1));

        List<Integer> sub = list.subList(0, 2);
        check(sub.size() == 2, "subList(0,2) size");
        check(sub.get(0) == 1 && sub.get(1) == 2, "subList(0,2) contents");

        List<Integer> empty = list.subList(1, 1);
        check(empty.isEmpty(), "subList(1,1) must be empty");

        sub.clear();
        check(list.size() == 1 && list.get(0) == 3, "clear on subList must affect backing list");
    }

    private static void expectThrows(Class<? extends Throwable> type, Runnable r) {
        try {
            r.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException("Unexpected exception: " + t, t);
        }
        throw new RuntimeException("Expected " + type.getName());
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
