/*
 * @test
 * @summary Verifies that Field.getByte on a static double field throws IllegalArgumentException, and that Field.get still returns the double value.
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static double D = 1.5;
    public static final double F = 2.5;

    public static void main(String[] args) throws Exception {
        checkGetByteRejected("D");
        checkGetByteRejected("F");
        checkGetReturnsDouble("D", 1.5);
        checkGetReturnsDouble("F", 2.5);
    }

    private static void checkGetByteRejected(String name) throws Exception {
        Field f = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getField(name);
        try {
            byte b = f.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException for getByte on " + name + " but got " + b);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void checkGetReturnsDouble(String name, double expected) throws Exception {
        Field f = UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest.class.getField(name);
        Object value = f.get(null);
        if (!(value instanceof Double)) {
            throw new RuntimeException("Expected Double for " + name + " but got " + value);
        }
        if (((Double) value).doubleValue() != expected) {
            throw new RuntimeException("Unexpected value for " + name + ": " + value);
        }
    }
}
