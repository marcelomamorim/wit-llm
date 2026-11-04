/*
 * @test
 * @summary Verifies that Field.getByte on a static double field is rejected by the UnsafeStaticDoubleFieldAccessorImpl accessor path
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    static double value = 1.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");

        Object boxed = f.get(null);
        if (!(boxed instanceof Double) || ((Double) boxed).doubleValue() != 1.5) {
            throw new RuntimeException("Unexpected get() result: " + boxed);
        }

        boolean thrown = false;
        try {
            f.getByte(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getByte on static double field did not throw IllegalArgumentException");
        }
    }
}
