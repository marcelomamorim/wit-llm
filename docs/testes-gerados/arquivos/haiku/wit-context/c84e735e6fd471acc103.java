/*
 * @test
 * @summary Witup-derived tests for Vector.elementAt(int) exception and normal paths
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        Vector<String> empty = new Vector<>();
        expectAioobe(empty, 0);

        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        if (!"a".equals(v.elementAt(0))) {
            throw new RuntimeException("elementAt(0) returned unexpected value");
        }
        if (!"b".equals(v.elementAt(1))) {
            throw new RuntimeException("elementAt(1) returned unexpected value");
        }
        expectAioobe(v, 2);
        expectAioobe(v, 3);
        expectAioobe(v, -1);

        Vector<String> withCapacity = new Vector<>(10, 2);
        withCapacity.add("x");
        if (!"x".equals(withCapacity.elementAt(0))) {
            throw new RuntimeException("elementAt(0) with custom capacity returned unexpected value");
        }
        expectAioobe(withCapacity, 1);
    }

    static void expectAioobe(Vector<String> vector, int index) {
        try {
            vector.elementAt(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for index " + index);
    }
}
