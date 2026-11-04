/*
 * @test
 * @summary Field.getFloat on a double field must throw IllegalArgumentException (UnsafeDoubleFieldAccessorImpl.getFloat)
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {
    public double d = 1.5;

    public static void main(String[] args) throws Exception {
        UnsafeDoubleFieldAccessorImplWitupTest t = new UnsafeDoubleFieldAccessorImplWitupTest();
        Field f = UnsafeDoubleFieldAccessorImplWitupTest.class.getDeclaredField("d");

        if (f.getDouble(t) != 1.5) {
            throw new AssertionError("getDouble returned unexpected value");
        }

        Object boxed = f.get(t);
        if (!(boxed instanceof Double) || ((Double) boxed).doubleValue() != 1.5) {
            throw new AssertionError("get(Object) returned unexpected value");
        }

        boolean thrown = false;
        try {
            f.getFloat(t);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getFloat on a double field must throw IllegalArgumentException");
        }
    }
}
