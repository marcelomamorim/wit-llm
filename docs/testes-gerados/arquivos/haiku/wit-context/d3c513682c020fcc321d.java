/*
 * @test
 * @summary Tests that Vector.remove(int) throws ArrayIndexOutOfBoundsException for indexes at or beyond size and removes/shifts elements correctly for valid indexes
 * @run main VectorWitupTest
 */
import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testRemoveValidIndexShiftsElements();
        testRemoveLastValidIndex();
        testRemoveAtSizeThrows();
        testRemoveBeyondSizeThrows();
        testRemoveFromEmptyThrows();
    }

    static Vector<String> abc() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        return v;
    }

    static void testRemoveValidIndexShiftsElements() {
        Vector<String> v = abc();
        String removed = v.remove(1);
        if (!"b".equals(removed)) {
            throw new RuntimeException("expected b but got " + removed);
        }
        if (v.size() != 2) {
            throw new RuntimeException("expected size 2 but got " + v.size());
        }
        if (!"a".equals(v.get(0)) || !"c".equals(v.get(1))) {
            throw new RuntimeException("unexpected contents after remove: " + v);
        }
    }

    static void testRemoveLastValidIndex() {
        Vector<String> v = abc();
        String removed = v.remove(2);
        if (!"c".equals(removed)) {
            throw new RuntimeException("expected c but got " + removed);
        }
        if (v.size() != 2 || !"a".equals(v.get(0)) || !"b".equals(v.get(1))) {
            throw new RuntimeException("unexpected contents after remove: " + v);
        }
    }

    static void testRemoveAtSizeThrows() {
        Vector<String> v = abc();
        assertRemoveThrows(v, 3);
        if (v.size() != 3 || !"a".equals(v.get(0)) || !"c".equals(v.get(2))) {
            throw new RuntimeException("vector changed after failed remove: " + v);
        }
    }

    static void testRemoveBeyondSizeThrows() {
        Vector<String> v = abc();
        assertRemoveThrows(v, 5);
        if (v.size() != 3) {
            throw new RuntimeException("size changed after failed remove: " + v.size());
        }
    }

    static void testRemoveFromEmptyThrows() {
        Vector<String> v = new Vector<>();
        assertRemoveThrows(v, 0);
        if (!v.isEmpty()) {
            throw new RuntimeException("empty vector changed after failed remove");
        }
    }

    static void assertRemoveThrows(Vector<String> v, int index) {
        try {
            v.remove(index);
        } catch (ArrayIndexOutOfBoundsException expected) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for index " + index);
    }
}
