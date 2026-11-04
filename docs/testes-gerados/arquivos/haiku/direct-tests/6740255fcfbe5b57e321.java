/*
 * @test
 * @summary Field.getByte on a double field must throw IllegalArgumentException via UnsafeDoubleFieldAccessorImpl, while getDouble still works
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {
    public double value = 1.5;

    public static void main(String[] args) throws Exception {
        UnsafeDoubleFieldAccessorImplWitupTest t = new UnsafeDoubleFieldAccessorImplWitupTest();
        Field f = UnsafeDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");

        boolean thrown = false;
        try {
            byte b = f.getByte(t);
            throw new RuntimeException("getByte returned " + b + " instead of throwing");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException not thrown by getByte");
        }

        if (f.getDouble(t) != 1.5) {
            throw new RuntimeException("getDouble returned unexpected value");
        }
    }
}
