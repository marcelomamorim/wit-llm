/*
 * @test
 * @summary Checks observable public behavior of ArrayList.get for valid and out-of-range indexes
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add(null);
        list.add("c");

        check("a".equals(list.get(0)));
        check(list.get(1) == null);
        check("c".equals(list.get(2)));

        expectIndexOutOfBounds(list, -1);
        expectIndexOutOfBounds(list, 3);
        expectIndexOutOfBounds(list, Integer.MAX_VALUE);
        expectIndexOutOfBounds(list, Integer.MIN_VALUE);

        ArrayList<Integer> empty = new ArrayList<>();
        expectIndexOutOfBounds(empty, 0);
        expectIndexOutOfBounds(empty, -1);

        ArrayList<Integer> sized = new ArrayList<>(10);
        expectIndexOutOfBounds(sized, 0);

        ArrayList<Integer> copy = new ArrayList<>(list.subList(0, 2));
        check(copy.size() == 2);
        check(copy.get(1) == null);
        expectIndexOutOfBounds(copy, 2);
    }

    private static void expectIndexOutOfBounds(ArrayList<?> list, int index) {
        try {
            list.get(index);
            throw new RuntimeException("Expected IndexOutOfBoundsException for index " + index);
        } catch (IndexOutOfBoundsException expected) {
        }
    }

    private static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Assertion failed");
        }
    }
}
