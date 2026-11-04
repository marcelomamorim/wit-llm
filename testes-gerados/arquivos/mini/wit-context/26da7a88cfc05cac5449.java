/*
 * @test
 * @summary Test java.util.TreeMap next() method behavior including exception paths
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsValue();
        testNextThrowsNoSuchElementException();
        testNextThrowsConcurrentModificationException();
    }

    static void testNextReturnsValue() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        Iterator<String> values = map.values().iterator();

        // next() should return the first value "one"
        String first = values.next();
        if (!"one".equals(first)) {
            throw new AssertionError("Expected first value 'one', got: " + first);
        }

        // next() should return the second value "two"
        String second = values.next();
        if (!"two".equals(second)) {
            throw new AssertionError("Expected second value 'two', got: " + second);
        }
    }

    static void testNextThrowsNoSuchElementException() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");

        Iterator<String> values = map.values().iterator();

        // Consume the only element
        values.next();

        // next() now should throw NoSuchElementException
        try {
            values.next();
            throw new AssertionError("Expected NoSuchElementException not thrown");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testNextThrowsConcurrentModificationException() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");

        Iterator<String> values = map.values().iterator();

        // Modify map structurally after iterator creation
        map.put(3, "three");

        try {
            // The first call to next() should detect concurrent modification
            values.next();
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
