/*
 * @test
 * @summary Verify that ListIterator.add on immutable lists throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.ListIterator;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testAddThrowsOnEmptyList();
        testAddThrowsOnSingleElementList();
        testAddThrowsOnMultiElementList();
        testAddThrowsForNullElement();
        testAddLeavesListUnchanged();
    }

    static void testAddThrowsOnEmptyList() {
        List<String> list = List.of();
        ListIterator<String> it = list.listIterator();
        expectUoe(it, "x", "empty list add");
        check(list.size() == 0, "empty list size changed");
    }

    static void testAddThrowsOnSingleElementList() {
        List<String> list = List.of("a");
        ListIterator<String> it = list.listIterator();
        expectUoe(it, "b", "single element list add");
        check(list.size() == 1, "single element list size changed");
    }

    static void testAddThrowsOnMultiElementList() {
        List<String> list = List.of("a", "b", "c");
        ListIterator<String> it = list.listIterator();
        it.next();
        expectUoe(it, "z", "multi element list add after next");
        check(list.size() == 3, "multi element list size changed");
    }

    static void testAddThrowsForNullElement() {
        List<String> list = List.of("a", "b");
        ListIterator<String> it = list.listIterator();
        expectUoe(it, null, "null element add");
    }

    static void testAddLeavesListUnchanged() {
        List<Integer> list = List.of(1, 2, 3);
        ListIterator<Integer> it = list.listIterator(1);
        expectUoe(it, 4, "add at middle index");
        check(list.size() == 3, "size changed after failed add");
        check(list.get(0) == 1 && list.get(1) == 2 && list.get(2) == 3,
              "contents changed after failed add");
    }

    static void expectUoe(ListIterator<String> it, String e, String label) {
        try {
            it.add(e);
        } catch (UnsupportedOperationException ok) {
            return;
        }
        throw new AssertionError(label + ": expected UnsupportedOperationException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
