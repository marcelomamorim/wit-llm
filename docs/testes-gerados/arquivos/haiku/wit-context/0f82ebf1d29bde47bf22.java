/*
 * @test
 * @summary Witup-generated tests for Vector.set exception and normal paths
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testSetOnEmptyVectorThrows();
        testSetIndexEqualToSizeThrows();
        testSetIndexGreaterThanSizeThrows();
        testSetNegativeIndexThrows();
        testSetReturnsOldValueAndReplaces();
        testSetLastValidIndex();
    }

    static void expectAioobe(Vector<String> v, int index) {
        try {
            v.set(index, "x");
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for index " + index);
    }

    static void testSetOnEmptyVectorThrows() {
        Vector<String> v = new Vector<>();
        expectAioobe(v, 0);
    }

    static void testSetIndexEqualToSizeThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        expectAioobe(v, 2);
        if (v.size() != 2 || !"a".equals(v.get(0)) || !"b".equals(v.get(1))) {
            throw new RuntimeException("Vector modified by failed set");
        }
    }

    static void testSetIndexGreaterThanSizeThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        expectAioobe(v, 5);
        if (v.size() != 1 || !"a".equals(v.get(0))) {
            throw new RuntimeException("Vector modified by failed set");
        }
    }

    static void testSetNegativeIndexThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        expectAioobe(v, -1);
    }

    static void testSetReturnsOldValueAndReplaces() {
        Vector<String> v = new Vector<>();
        v.add("old");
        v.add("keep");
        String previous = v.set(0, "new");
        if (!"old".equals(previous)) {
            throw new RuntimeException("Expected old value 'old' but got " + previous);
        }
        if (!"new".equals(v.get(0))) {
            throw new RuntimeException("Expected replaced value 'new' but got " + v.get(0));
        }
        if (!"keep".equals(v.get(1)) || v.size() != 2) {
            throw new RuntimeException("Other elements or size changed");
        }
    }

    static void testSetLastValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        String previous = v.set(2, "z");
        if (!"c".equals(previous) || !"z".equals(v.get(2)) || v.size() != 3) {
            throw new RuntimeException("Unexpected result for last index set");
        }
    }
}
