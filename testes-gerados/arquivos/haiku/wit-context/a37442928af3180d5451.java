/*
 * @test
 * @summary Verify that reading a static double field as byte via the public reflection API throws IllegalArgumentException
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static double doubleValue = 1.5;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("doubleValue");

        double d = field.getDouble(null);
        if (d != 1.5) {
            throw new RuntimeException("Unexpected getDouble result: " + d);
        }

        boolean thrown = false;
        try {
            field.getByte(null);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getByte on double field");
        }
    }
}
