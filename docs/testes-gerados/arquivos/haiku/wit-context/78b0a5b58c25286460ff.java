/*
 * @test
 * @summary Verifies that reading a float field as byte via java.lang.reflect.Field is rejected with IllegalArgumentException
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public float value = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeFloatFieldAccessorImplWitupTest obj = new UnsafeFloatFieldAccessorImplWitupTest();
        Field f = UnsafeFloatFieldAccessorImplWitupTest.class.getField("value");

        if (f.getFloat(obj) != 1.5f) {
            throw new RuntimeException("getFloat control value mismatch");
        }

        boolean thrown = false;
        try {
            f.getByte(obj);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getByte on float field");
        }
    }
}
