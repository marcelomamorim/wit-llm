/*
 * @test
 * @summary Test Vector constructor with initialCapacity and capacityIncrement
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testVectorConstructorWithValidParameters();
        testVectorConstructorWithZeroInitialCapacity();
        testVectorConstructorWithNegativeInitialCapacity();
    }

    static void testVectorConstructorWithValidParameters() {
        int initialCapacity = 10;
        int capacityIncrement = 5;
        Vector<Object> vector = new Vector<>(initialCapacity, capacityIncrement);
        assert vector.capacity() >= initialCapacity : "Capacity should be at least initialCapacity";
        assert vector.capacity() >= 0 : "Capacity should be non-negative";
    }

    static void testVectorConstructorWithZeroInitialCapacity() {
        int initialCapacity = 0;
        int capacityIncrement = 3;
        Vector<Object> vector = new Vector<>(initialCapacity, capacityIncrement);
        assert vector.capacity() == 0 : "Capacity should be zero when initialized with zero initialCapacity";
    }

    static void testVectorConstructorWithNegativeInitialCapacity() {
        int initialCapacity = -1;
        int capacityIncrement = 2;
        boolean exceptionThrown = false;
        try {
            new Vector<>(initialCapacity, capacityIncrement);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Constructor should throw IllegalArgumentException for negative initialCapacity";
    }
}
