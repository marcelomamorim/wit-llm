/*
 * @test
 * @summary Verify that Field.getByte on a float field throws IllegalArgumentException via the float field accessor
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public float value = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeFloatFieldAccessorImplWitupTest t = new UnsafeFloatFieldAccessorImplWitupTest();
        Field f = UnsafeFloatFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            f.getByte(t);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getByte on float field");
        }

        if (f.getFloat(t) != 1.5f) {
            throw new RuntimeException("Float field value changed unexpectedly");
        }
    }
}
