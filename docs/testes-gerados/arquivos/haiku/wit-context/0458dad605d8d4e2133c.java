/*
 * @test
 * @summary Verify that Field.getByte rejects a static int field with IllegalArgumentException
 * @run main UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest {

    public static final int VALUE = 42;

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticIntegerFieldAccessorImplWitupTest.class.getDeclaredField("VALUE");
        try {
            field.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException from getByte on an int field");
        } catch (IllegalArgumentException expected) {
            // expected behavior
        }
    }
}
