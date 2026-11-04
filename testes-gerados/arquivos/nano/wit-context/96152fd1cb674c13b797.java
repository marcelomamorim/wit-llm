/* @test    @summary Test for Vector constructor with initialCapacity and capacityIncrement    @run main VectorWitupTest */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testValidInitialCapacity();
        testZeroInitialCapacity();
        testNegativeInitialCapacity();
    }

    static void testValidInitialCapacity() {
        Vector<Integer> v = new Vector<>(10, 5);
        assert v.size() == 0 : "Expected size 0";
        assert v.capacity() >= 10 : "Capacity should be at least 10";
    }

    static void testZeroInitialCapacity() {
        Vector<Integer> v = new Vector<>(0, 5);
        assert v.size() == 0 : "Expected size 0";
        assert v.capacity() >= 0 : "Capacity should be at least 0";
    }

    static void testNegativeInitialCapacity() {
        boolean exceptionThrown = false;
        try {
            Vector<Integer> v = new Vector<>(-1, 5);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for negative initialCapacity";
    }
}
