/* @test    @summary Test for Vector constructor with initialCapacity parameter    @run main VectorWitupTest */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testVectorConstructorWithValidCapacity();
        testVectorConstructorWithZeroCapacity();
        testVectorConstructorWithNegativeCapacity();
    }

    private static void testVectorConstructorWithValidCapacity() {
        Vector<Object> v = new Vector<>(10);
        if (v.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + v.size());
        }
    }

    private static void testVectorConstructorWithZeroCapacity() {
        Vector<Object> v = new Vector<>(0);
        if (v.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + v.size());
        }
    }

    private static void testVectorConstructorWithNegativeCapacity() {
        boolean exceptionThrown = false;
        try {
            new Vector<>(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative capacity");
        }
    }
}
