/*
 * @test
 * @summary Test for CopyOnWriteArrayList.toArray() method, focusing on normal behavior and exception scenarios.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Arrays;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testToArrayNormal();
        testToArrayEmpty();
        testToArrayWithNullElements();
        testToArrayWithConcurrentModification();
        testToArrayWithInvalidState();
    }

    static void testToArrayNormal() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        Object[] result = list.toArray();
        if (!Arrays.equals(result, new Object[]{"a", "b"})) {
            throw new AssertionError("Normal toArray() failed: " + Arrays.toString(result));
        }
    }

    static void testToArrayEmpty() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        Object[] result = list.toArray();
        if (result.length != 0) {
            throw new AssertionError("Empty list toArray() should return empty array: " + Arrays.toString(result));
        }
    }

    static void testToArrayWithNullElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add(null);
        list.add("test");
        Object[] result = list.toArray();
        if (result.length != 2 || result[0] != null || !"test".equals(result[1])) {
            throw new AssertionError("toArray() with null elements failed: " + Arrays.toString(result));
        }
    }

    static void testToArrayWithConcurrentModification() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("x");
        list.add("y");
        Object[] array = list.toArray();
        list.add("z");
        Object[] result = list.toArray();
        if (result.length != 3 || !"z".equals(result[2])) {
            throw new AssertionError("Concurrent modification affected toArray(): " + Arrays.toString(result));
        }
    }

    static void testToArrayWithInvalidState() {
        // Simulate invalid internal state by subclassing and overriding getArrayChecked()
        class InvalidCopyOnWriteArrayList extends CopyOnWriteArrayList<Object> {
            @Override
            public Object[] getArrayChecked() {
                throw new IllegalArgumentException("Simulated invalid state");
            }
        }
        try {
            new InvalidCopyOnWriteArrayList().toArray();
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
