/*
 * @test
 * @summary Witup-generated tests for Vector(int initialCapacity, int capacityIncrement)
 * @run main VectorWitupTest
 */
import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testNegativeInitialCapacityThrows();
        testZeroInitialCapacityAccepted();
        testInitialCapacityAndIncrementObservable();
        testZeroIncrementDoublesCapacity();
    }

    static void testNegativeInitialCapacityThrows() {
        try {
            new Vector<String>(-1, 5);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for negative initialCapacity");
    }

    static void testZeroInitialCapacityAccepted() {
        Vector<String> v = new Vector<>(0, 1);
        if (v.size() != 0) {
            throw new RuntimeException("Expected size 0, got " + v.size());
        }
        if (v.capacity() != 0) {
            throw new RuntimeException("Expected capacity 0, got " + v.capacity());
        }
    }

    static void testInitialCapacityAndIncrementObservable() {
        Vector<String> v = new Vector<>(2, 3);
        if (v.capacity() != 2) {
            throw new RuntimeException("Expected initial capacity 2, got " + v.capacity());
        }
        v.add("a");
        v.add("b");
        if (v.capacity() != 2) {
            throw new RuntimeException("Expected capacity 2 after two adds, got " + v.capacity());
        }
        v.add("c");
        if (v.capacity() != 5) {
            throw new RuntimeException("Expected capacity 5 after growth by increment 3, got " + v.capacity());
        }
        if (v.size() != 3 || !v.get(2).equals("c")) {
            throw new RuntimeException("Unexpected contents after growth");
        }
    }

    static void testZeroIncrementDoublesCapacity() {
        Vector<String> v = new Vector<>(1, 0);
        v.add("a");
        if (v.capacity() != 1) {
            throw new RuntimeException("Expected capacity 1 after first add, got " + v.capacity());
        }
        v.add("b");
        if (v.capacity() != 2) {
            throw new RuntimeException("Expected capacity doubled to 2, got " + v.capacity());
        }
    }
}
