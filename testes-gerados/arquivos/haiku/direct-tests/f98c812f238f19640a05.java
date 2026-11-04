/*
 * @test
 * @summary Verify that Field.getShort on a double field reaches UnsafeDoubleFieldAccessorImpl.getShort and throws IllegalArgumentException
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static double staticValue = 3.5d;
    public double instanceValue = -2.25d;

    public static void main(String[] args) throws Exception {
        testStaticDoubleFieldGetShortThrows();
        testInstanceDoubleFieldGetShortThrows();
        testDoubleAccessStillWorks();
    }

    static void testStaticDoubleFieldGetShortThrows() throws Exception {
        Field f = UnsafeDoubleFieldAccessorImplWitupTest.class.getDeclaredField("staticValue");
        boolean thrown = false;
        try {
            f.getShort(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getShort on static double field");
        }
    }

    static void testInstanceDoubleFieldGetShortThrows() throws Exception {
        Field f = UnsafeDoubleFieldAccessorImplWitupTest.class.getDeclaredField("instanceValue");
        UnsafeDoubleFieldAccessorImplWitupTest obj = new UnsafeDoubleFieldAccessorImplWitupTest();
        boolean thrown = false;
        try {
            f.getShort(obj);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getShort on instance double field");
        }
    }

    static void testDoubleAccessStillWorks() throws Exception {
        Field f = UnsafeDoubleFieldAccessorImplWitupTest.class.getDeclaredField("instanceValue");
        UnsafeDoubleFieldAccessorImplWitupTest obj = new UnsafeDoubleFieldAccessorImplWitupTest();
        double d = f.getDouble(obj);
        if (d != -2.25d) {
            throw new AssertionError("Unexpected getDouble value: " + d);
        }
    }
}
