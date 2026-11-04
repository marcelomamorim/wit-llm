/*
 * @test
 * @summary Field.getBoolean on a double field must reject the boolean conversion, while get still returns the Double value
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {
    public double value = 1.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeDoubleFieldAccessorImplWitupTest.class.getField("value");
        UnsafeDoubleFieldAccessorImplWitupTest t = new UnsafeDoubleFieldAccessorImplWitupTest();

        boolean thrown = false;
        try {
            f.getBoolean(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalArgumentException from getBoolean on double field");
        }

        Object v = f.get(t);
        if (!(v instanceof Double) || ((Double) v).doubleValue() != 1.5) {
            throw new AssertionError("unexpected get() result: " + v);
        }
    }
}
