/*
 * @test
 * @summary Verifies LinkedList(Collection) constructor copies elements in iteration order and handles empty and null-element collections
 * @run main LinkedListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LinkedListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCopiesElementsInOrder() {
        List<String> source = Arrays.asList("a", "b", "c");
        LinkedList<String> list = new LinkedList<>(source);
        check(list.size() == 3, "size should be 3");
        check(list.get(0).equals("a"), "index 0 should be a");
        check(list.get(1).equals("b"), "index 1 should be b");
        check(list.get(2).equals("c"), "index 2 should be c");
    }

    static void testEmptySourceCreatesEmptyList() {
        LinkedList<Integer> list = new LinkedList<>(new ArrayList<Integer>());
        check(list.isEmpty(), "list built from empty collection should be empty");
        check(list.size() == 0, "size should be 0");
    }

    static void testPreservesNullElements() {
        List<String> source = Arrays.asList(null, "x", null);
        LinkedList<String> list = new LinkedList<>(source);
        check(list.size() == 3, "size should be 3 including nulls");
        check(list.get(0) == null, "first element should be null");
        check(list.get(1).equals("x"), "second element should be x");
        check(list.get(2) == null, "third element should be null");
    }

    static void testCopyIsIndependentOfSource() {
        ArrayList<Integer> source = new ArrayList<>();
        source.add(1);
        source.add(2);
        LinkedList<Integer> list = new LinkedList<>(source);
        source.add(3);
        source.set(0, 99);
        check(list.size() == 2, "copy should not reflect later additions to source");
        check(list.get(0).equals(1), "copy should not reflect later updates to source");
    }

    static void testResultIsUsableAsDeque() {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("m", "n"));
        list.addFirst("f");
        list.addLast("l");
        check(list.getFirst().equals("f"), "first should be f");
        check(list.getLast().equals("l"), "last should be l");
        check(list.size() == 4, "size should be 4");
    }

    static void testIteratorOrderMatchesSource() {
        List<Integer> source = Arrays.asList(5, 4, 3, 2, 1);
        LinkedList<Integer> list = new LinkedList<>(source);
        Iterator<Integer> it = list.iterator();
        for (Integer expected : source) {
            check(it.hasNext(), "iterator should have next");
            check(it.next().equals(expected), "iterator order mismatch");
        }
        check(!it.hasNext(), "iterator should be exhausted");
    }

    public static void main(String[] args) {
        testCopiesElementsInOrder();
        testEmptySourceCreatesEmptyList();
        testPreservesNullElements();
        testCopyIsIndependentOfSource();
        testResultIsUsableAsDeque();
        testIteratorOrderMatchesSource();
    }
}
