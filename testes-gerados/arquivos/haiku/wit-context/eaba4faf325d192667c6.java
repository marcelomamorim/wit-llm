/*
 * @test
 * @summary WITUP-derived behavioral tests for LinkedList.remove(int) index validation
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        removeOnEmptyListThrows();
        removeOutOfRangeThrowsAndKeepsList();
        removeNegativeIndexThrowsAndKeepsList();
        removeValidIndexReturnsElementAndShrinksList();
        removeFromCopiedListBehavesLikeOriginal();
    }

    static void removeOnEmptyListThrows() {
        LinkedList<String> list = new LinkedList<>();
        expectIndexOutOfBounds(list, 0);
        check(list.size() == 0, "size changed on empty list");
    }

    static void removeOutOfRangeThrowsAndKeepsList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        expectIndexOutOfBounds(list, 3);
        expectIndexOutOfBounds(list, 100);
        check(list.size() == 3, "size changed after failed remove");
        check("[a, b, c]".equals(list.toString()), "contents changed after failed remove: " + list);
    }

    static void removeNegativeIndexThrowsAndKeepsList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        expectIndexOutOfBounds(list, -1);
        expectIndexOutOfBounds(list, Integer.MIN_VALUE);
        check(list.size() == 2, "size changed after negative index remove");
        check("[a, b]".equals(list.toString()), "contents changed after negative index remove: " + list);
    }

    static void removeValidIndexReturnsElementAndShrinksList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        String removed = list.remove(1);
        check("b".equals(removed), "wrong element returned: " + removed);
        check("[a, c]".equals(list.toString()), "wrong contents after remove(1): " + list);
        check("a".equals(list.remove(0)), "wrong element returned for remove(0)");
        check("c".equals(list.remove(0)), "wrong element returned for second remove(0)");
        check(list.isEmpty(), "list should be empty");
        expectIndexOutOfBounds(list, 0);
    }

    static void removeFromCopiedListBehavesLikeOriginal() {
        LinkedList<String> original = new LinkedList<>();
        original.add("x");
        original.add("y");
        LinkedList<String> copy = new LinkedList<>(original);
        expectIndexOutOfBounds(copy, 2);
        check("y".equals(copy.remove(1)), "copy remove(1) returned wrong element");
        check(copy.size() == 1, "copy size wrong after remove");
        check(original.size() == 2, "original affected by removal on copy");
    }

    static void expectIndexOutOfBounds(LinkedList<String> list, int index) {
        try {
            list.remove(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + index);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
