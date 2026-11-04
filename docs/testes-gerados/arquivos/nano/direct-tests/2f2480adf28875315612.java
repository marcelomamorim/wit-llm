/* @test    @summary Test Vector constructor with initialCapacity parameter    @run main VectorWitupTest */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testVectorConstructorWithInitialCapacity();
    }

    public static void testVectorConstructorWithInitialCapacity() {
        int initialCapacity = 10;
        Vector<Integer> vector = new Vector<>(initialCapacity);
        if (vector.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + vector.size());
        }
        if (vector.capacity() < initialCapacity) {
            throw new AssertionError("Expected capacity at least " + initialCapacity + ", but got " + vector.capacity());
        }
    }
}
