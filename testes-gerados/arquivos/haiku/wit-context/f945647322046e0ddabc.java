/*
 * @test
 * @summary Behavioral checks for ArrayList.SubList spliterator tryAdvance: null action, ordered traversal, empty range, and comodification detection
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Spliterator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testAdvanceVisitsInOrderAndReturnsFalseWhenDone();
        testEmptySubListReturnsFalseWithoutAction();
        testStructuralModificationThrowsCme();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullActionThrowsNpe() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        Spliterator<String> sp = list.subList(0, 1).spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null action");
    }

    static void testAdvanceVisitsInOrderAndReturnsFalseWhenDone() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Spliterator<Integer> sp = list.subList(1, 3).spliterator();
        List<Integer> seen = new ArrayList<>();
        boolean r1 = sp.tryAdvance(x -> seen.add(x));
        check(r1, "first tryAdvance should return true");
        check(seen.equals(List.of(2)) || seen.size() == 1 && seen.get(0) == 2,
              "first element should be 2, got " + seen);
        boolean r2 = sp.tryAdvance(x -> seen.add(x));
        check(r2, "second tryAdvance should return true");
        check(seen.size() == 2 && seen.get(1) == 3,
              "second element should be 3, got " + seen);
        boolean r3 = sp.tryAdvance(x -> seen.add(x));
        check(!r3, "tryAdvance past end should return false");
        check(seen.size() == 2, "no action should run past end, got " + seen);
    }

    static void testEmptySubListReturnsFalseWithoutAction() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        Spliterator<String> sp = list.subList(0, 0).spliterator();
        int[] calls = new int[1];
        boolean r = sp.tryAdvance(x -> calls[0]++);
        check(!r, "empty sublist tryAdvance should return false");
        check(calls[0] == 0, "action must not run for empty sublist");
    }

    static void testStructuralModificationThrowsCme() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Spliterator<Integer> sp = list.subList(0, 3).spliterator();
        int[] calls = new int[1];
        boolean first = sp.tryAdvance(x -> calls[0]++);
        check(first, "first tryAdvance before modification should return true");
        list.add(4);
        boolean thrown = false;
        try {
            sp.tryAdvance(x -> calls[0]++);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after structural modification");
    }
}
