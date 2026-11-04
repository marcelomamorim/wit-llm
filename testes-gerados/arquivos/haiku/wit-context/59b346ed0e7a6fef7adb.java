/*
 * @test
 * @summary Checks AbstractList RandomAccessSpliterator.forEachRemaining null handling, traversal order, partial consumption and modCount check
 * @run main AbstractListWitupTest
 */

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.RandomAccess;
import java.util.Spliterator;

public class AbstractListWitupTest {

    static class FixedList extends AbstractList<String> implements RandomAccess {
        private final String[] data;

        FixedList(String... d) {
            data = d;
        }

        public String get(int i) {
            return data[i];
        }

        public int size() {
            return data.length;
        }
    }

    static class GrowingList extends AbstractList<String> implements RandomAccess {
        private final List<String> backing = new ArrayList<>();

        public String get(int i) {
            return backing.get(i);
        }

        public int size() {
            return backing.size();
        }

        public boolean add(String s) {
            backing.add(s);
            modCount++;
            return true;
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    static void testNullActionThrowsNPE() {
        Spliterator<String> sp = new FixedList("a", "b").spliterator();
        try {
            sp.forEachRemaining(null);
            fail("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testTraversalOrder() {
        FixedList list = new FixedList("a", "b", "c");
        Spliterator<String> sp = list.spliterator();
        List<String> seen = new ArrayList<>();
        sp.forEachRemaining(s -> seen.add(s));
        if (!seen.equals(List.of("a", "b", "c"))) {
            fail("unexpected traversal: " + seen);
        }
        if (sp.tryAdvance(s -> fail("should not be called"))) {
            fail("spliterator should be exhausted");
        }
    }

    static void testPartialConsumption() {
        FixedList list = new FixedList("a", "b", "c");
        Spliterator<String> sp = list.spliterator();
        List<String> first = new ArrayList<>();
        if (!sp.tryAdvance(s -> first.add(s))) {
            fail("tryAdvance should consume first element");
        }
        List<String> rest = new ArrayList<>();
        sp.forEachRemaining(s -> rest.add(s));
        if (!first.equals(List.of("a")) || !rest.equals(List.of("b", "c"))) {
            fail("unexpected partial traversal: " + first + " " + rest);
        }
    }

    static void testEmptyList() {
        FixedList list = new FixedList();
        List<String> seen = new ArrayList<>();
        list.spliterator().forEachRemaining(s -> seen.add(s));
        if (!seen.isEmpty()) {
            fail("expected no elements, got " + seen);
        }
    }

    static void testStructuralModificationDetected() {
        GrowingList list = new GrowingList();
        list.add("a");
        Spliterator<String> sp = list.spliterator();
        try {
            sp.forEachRemaining(s -> list.add("x"));
            fail("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
        }
    }

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testTraversalOrder();
        testPartialConsumption();
        testEmptyList();
        testStructuralModificationDetected();
    }
}
