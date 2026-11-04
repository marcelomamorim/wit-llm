/*
 * @test
 * @summary Behavioral tests for TreeMap.forEach: null action, ordering, empty map, and fail-fast behavior
 * @run main TreeMapWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testVisitsEntriesInAscendingKeyOrder();
        testEmptyMapDoesNotInvokeAction();
        testStructuralModificationThrowsCME();
        testValueUpdateIsNotStructuralModification();
    }

    static void testNullActionThrowsNPE() {
        TreeMap<Integer, Integer> m = new TreeMap<>();
        m.put(1, 1);
        boolean thrown = false;
        try {
            m.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null action");
        }
    }

    static void testVisitsEntriesInAscendingKeyOrder() {
        TreeMap<Integer, Integer> m = new TreeMap<>();
        m.put(3, 30);
        m.put(1, 10);
        m.put(2, 20);
        final List<Integer> keys = new ArrayList<>();
        final List<Integer> values = new ArrayList<>();
        m.forEach((k, v) -> {
            keys.add(k);
            values.add(v);
        });
        List<Integer> expectedKeys = new ArrayList<>();
        expectedKeys.add(1);
        expectedKeys.add(2);
        expectedKeys.add(3);
        List<Integer> expectedValues = new ArrayList<>();
        expectedValues.add(10);
        expectedValues.add(20);
        expectedValues.add(30);
        if (!keys.equals(expectedKeys)) {
            throw new RuntimeException("unexpected key order: " + keys);
        }
        if (!values.equals(expectedValues)) {
            throw new RuntimeException("unexpected value order: " + values);
        }
    }

    static void testEmptyMapDoesNotInvokeAction() {
        TreeMap<Integer, Integer> m = new TreeMap<>();
        final int[] calls = new int[1];
        m.forEach((k, v) -> calls[0]++);
        if (calls[0] != 0) {
            throw new RuntimeException("action invoked " + calls[0] + " times on empty map");
        }
    }

    static void testStructuralModificationThrowsCME() {
        final TreeMap<Integer, Integer> m = new TreeMap<>();
        m.put(1, 1);
        m.put(2, 2);
        m.put(3, 3);
        boolean thrown = false;
        try {
            m.forEach((k, v) -> {
                if (k.intValue() == 1) {
                    m.put(10, 10);
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected ConcurrentModificationException after structural change");
        }
    }

    static void testValueUpdateIsNotStructuralModification() {
        final TreeMap<Integer, Integer> m = new TreeMap<>();
        m.put(1, 1);
        m.put(2, 2);
        m.put(3, 3);
        m.forEach((k, v) -> {
            if (k.intValue() == 2) {
                m.put(2, 200);
            }
        });
        if (m.get(2).intValue() != 200) {
            throw new RuntimeException("value update during forEach was not applied");
        }
        if (m.size() != 3) {
            throw new RuntimeException("size changed unexpectedly: " + m.size());
        }
    }
}
