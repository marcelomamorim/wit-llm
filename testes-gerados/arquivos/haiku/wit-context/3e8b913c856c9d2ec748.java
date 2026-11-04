/*
 * @test
 * @summary Field.getShort on a static non-final double field must throw IllegalArgumentException through UnsafeStaticDoubleFieldAccessorImpl.getShort
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    static double value = 1.5;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");
        try {
            f.getShort(null);
            throw new RuntimeException("expected IllegalArgumentException for null obj");
        } catch (IllegalArgumentException expected) {
        }
        try {
            f.getShort(new Object());
            throw new RuntimeException("expected IllegalArgumentException for non-null obj");
        } catch (IllegalArgumentException expected) {
        }
    }
}
